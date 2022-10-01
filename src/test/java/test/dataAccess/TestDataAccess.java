package test.dataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Sport;
import domain.Team;

public class TestDataAccess {
	protected static  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {

		System.out.println("Creating TestDataAccess instance");

		open();

	}


	public void open(){

		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}

	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		if(ev!=null) {
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				db.getTransaction().begin();
				db.remove(e);
				db.getTransaction().commit();
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty, Team lokala, Team kanpokoa) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev=null;
		db.getTransaction().begin();
		try {
			ev=new Event(desc,d, lokala, kanpokoa);
			ev.addQuestion(question, qty);
			lokala.addEvent(ev);
			kanpokoa.addEvent(ev);
			db.persist(ev);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ev;
	}
	public boolean existQuestion(Event ev,Question q) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			return e.DoesQuestionExists(q.getQuestion());
		} else 
			return false;

	}

	public boolean removeTeam(Team t) {
		System.out.println(">> DataAccessTest: removeTeam");
		Team e = db.find(Team.class, t.getIzena());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}

	public Sport kirolaSortu(String desc) {
		System.out.println(">> DataAccessTest: kirolaSortu");
		db.getTransaction().begin();
		Sport s = new Sport(desc);
		db.persist(s);
		db.getTransaction().commit();
		return s;
	}

	public Event gertaeraSortu(String description,Date eventDate, Sport sport) {
		Event e =null;
		if(description==null) {
			return e;
		}else {
			Sport spo =db.find(Sport.class, sport.getIzena());
			if(spo!=null){
				TypedQuery<Event> Equery = db.createQuery("SELECT e FROM Event e WHERE e.getEventDate() =?1 ",Event.class);
				Equery.setParameter(1, eventDate);
				for(Event ev: Equery.getResultList()) {
					if(ev.getDescription().equals(description)) {
						return e;
					}
				}
				db.getTransaction().begin();
				String[] taldeak = description.split("-");
				Team lokala = new Team(taldeak[0]);
				Team kanpokoa = new Team(taldeak[1]);
				e = new Event(description, eventDate, lokala, kanpokoa);
				e.setSport(spo);
				spo.addEvent(e);
				db.persist(e);
				db.getTransaction().commit();
			}else {
				return e;
			}
		}
		return e;
	}

	public boolean kirolaEzabatu(Sport s) {
		System.out.println(">> DataAccessTest: KirolaEzabatu");
		Sport spo = db.find(Sport.class, s.getIzena());
		if (spo!=null) {
			db.getTransaction().begin();
			db.remove(spo);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}
	
	public boolean kirolaEzabatuIzenarekin(String s) {
		System.out.println(">> DataAccessTest: KirolaEzabatu");
		Sport spo = db.find(Sport.class, s);
		if (spo!=null) {
			db.getTransaction().begin();
			db.remove(spo);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}
	
	public boolean kirolarenGertaeraEzabatu(String s, Event e) {
		System.out.println(">> DataAccessTest: KirolaEzabatu");
		Sport spo = db.find(Sport.class, s);
		if (spo!=null) {
			for(Event ev: spo.getEvents()) {
				if(ev.getEventNumber().equals(e.getEventNumber())) {
					db.getTransaction().begin();
					db.remove(ev);
					db.getTransaction().commit();
					return true;
				}
			}	
		} else {
			return false;
		}
		return false;
	}
	
	public boolean kirolarenGertaeraEzabatuArray(String s, Event e) {
		System.out.println(">> DataAccessTest: KirolaEzabatu");
		Sport spo = db.find(Sport.class, s);
		if (spo!=null) {
			Vector<Event> al = spo.getEvents();
			Iterator<Event> itr = al.iterator();
	        while (itr.hasNext()){
	            Event ev = (Event) itr.next();
	            if (ev.getEventNumber().equals(e.getEventNumber())) {
	                itr.remove();
	            	return true;
				}
			}	
		} else {
			return false;
		}
		return false;
	}

	public int findMaxID() {
		String sql = "SELECT MAX(eventNumber) AS event_num FROM Event";
		Query m = db.createQuery(sql);
		int value = (int) m.getSingleResult();
		System.out.println(value);
		return value;
	}
	public Event findEventFromNumber(int i){
		Event e = db.find(Event.class, i);
		return e; 
	}

}


package test.businessLogic;


import java.util.Date;

import configuration.ConfigXML;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Registered;
import domain.Sport;
import domain.Team;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;
 	
    
	   public TestFacadeImplementation()  {
			
			System.out.println("Creating TestFacadeImplementation instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeEvent(Event ev) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeEvent(ev);
			dbManagerTest.close();
			return b;

		}
		
		public Event addEventWithQuestion(String desc, Date d, String q, float qty, Team lokala, Team kanpokoa) {
			dbManagerTest.open();
			Event o=dbManagerTest.addEventWithQuestion(desc,d,q, qty, lokala, kanpokoa);
			dbManagerTest.close();
			return o;

		}
		
		public boolean removeTeam(Team t) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeTeam(t);
			dbManagerTest.close();
			return b;
		}
		
		public Sport kirolaSortu(String desc) {
			dbManagerTest.open();
			Sport s=dbManagerTest.kirolaSortu(desc);
			dbManagerTest.close();
			return s;
		}
		
		public Event gertaeraSortu(String description,Date eventDate, Sport sport) {
			dbManagerTest.open();
			Event e=dbManagerTest.gertaeraSortu(description, eventDate, sport);
			dbManagerTest.close();
			return e;
		}
		
		public boolean kirolaEzabatu(Sport s) {
			dbManagerTest.open();
			Boolean b=dbManagerTest.kirolaEzabatu(s);
			dbManagerTest.close();
			return b;
		}
		
		public Event gertaeraSortu2(String description,Date eventDate, String sport) {
			dbManagerTest.open();
			Event e= dbManagerTest.gertaeraSortu2(description, eventDate, sport);
			dbManagerTest.close();
			return e;
		}
		
		public Registered storeRegistered(String username, String password, Integer bankAccount) {
			dbManagerTest.open();
			Registered r = dbManagerTest.storeRegistered(username, password, bankAccount);
			dbManagerTest.close();
			return r;
		}
		
		public int findMaxIDApustua() {
			dbManagerTest.open();
			Integer i = dbManagerTest.findMaxIDApustua();
			dbManagerTest.close();
			return i;
		}
		
		public Apustua findApustuaFromNumber(int i) {
			dbManagerTest.open();
			Apustua a = dbManagerTest.findApustuaFromNumber(i);
			dbManagerTest.close();
			return a;
		}
		
		public boolean removeEvent2(Event ev) {
			dbManagerTest.open();
			Boolean b= dbManagerTest.removeEvent2(ev);
			dbManagerTest.close();
			return b;
		}
		
		public boolean registerEzabatu(Registered r) {
			dbManagerTest.open();
			Boolean b= dbManagerTest.registerEzabatu(r);
			dbManagerTest.close();
			return b;
		}
		
		public Question createQuestion2(Event event, String question, float betMinimum) throws  QuestionAlreadyExist{
			dbManagerTest.open();
			Question q= dbManagerTest.createQuestion2(event, question, betMinimum);
			dbManagerTest.close();
			return q;
		}

}

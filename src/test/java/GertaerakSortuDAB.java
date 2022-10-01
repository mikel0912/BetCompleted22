import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Question;
import domain.Team;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDAB {

	//sut:system under test
	static DataAccessInterface sut=new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	private Event ev;
	private Team lokala;
	private Team kanpokoa;
	private String description;
	private String sport;
	private Date data;

	@Test
	//sut.gertaerakSortu:  The spo variable is null. There is no sport as same as the parameter in the DB. The test fail
	public void test1() {
		Boolean sortuta = null;
		try {

			//define paramaters
			description = "Eibar-Barca";
			sport = "Pilla-Pilla";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			//invoke System Under Test (sut)  
			sortuta =sut.gertaerakSortu(description, data, sport);
			//verify the results
			assertTrue(!sortuta);

		}catch(IllegalArgumentException e1) {
			assertTrue(false);
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport))){
				boolean b=testDA.removeEvent(ev);
				boolean b1 = testDA.kirolaEzabatuIzenarekin(sport);
				testDA.close();
				if(b && b1) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					assertTrue(b && b1);
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b);
					assertTrue(b && b1);
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertEquals(sortuta, false);
			}

		}
	}
	
	@Test
	//sut.gertaerakSortu:  The are no events in the DB yet. The test success
	public void test2() {
		try {
			//define paramaters
			description = "Ermua-Soraluze";
			sport= "Futbol";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			//invoke System Under Test (sut)  
			Boolean sortuta =sut.gertaerakSortu(description, data, sport);
			//verify the results
			assertTrue(sortuta);

		}catch(NullPointerException e1) {
			assertTrue(false);
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport))){
				boolean b1 = testDA.kirolarenGertaeraEzabatuArray(sport, ev);
				boolean b=testDA.removeEvent(ev);
				testDA.close();
				if(b && b1) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					assertTrue(b && b1);
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b+b1);
					assertTrue(b && b1);
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(false);
			}
			         
		}
	}
	
	@Test
	//sut.gertaerakSortu:  The are events in the DB. No one with the same description. The test success
	public void test3() {
		try {
			//define paramaters
			description = "Eibar-Soraluze";
			sport= "Futbol";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			//invoke System Under Test (sut)  
			Boolean sortuta =sut.gertaerakSortu(description, data, sport);
			//verify the results
			assertTrue(sortuta);

		}catch(NullPointerException e1) {
			assertTrue(false);
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport))){
				boolean b1 = testDA.kirolarenGertaeraEzabatuArray(sport, ev);
				boolean b=testDA.removeEvent(ev);
				testDA.close();
				if(b && b1) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					assertTrue(b && b1);
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b+b1);
					assertTrue(b && b1);
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
			}
			         
		}
	}
}


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Question;
import domain.Sport;
import domain.Team;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDAWTest {

	//sut:system under test
	static DataAccessInterface sut=new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	private Event ev;
	private Team lokala;
	private Team kanpokoa;
	private String description;
	private Sport sport;
	private String sportizen;
	private Date data;

	@Test
	//sut.gertaerakSortu:  The spo variable is null. There is no sport as same as the parameter in the DB. The test fail
	public void test1() {
		Boolean sortuta = null;
		try {

			//define paramaters
			description = "Eibar-Barca";
			sportizen = "Pilla-Pilla";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			//invoke System Under Test (sut)  
			sortuta =sut.gertaerakSortu(description, data, sportizen);
			//verify the results
			assertTrue(!sortuta);

		}catch(IllegalArgumentException e1) {
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sportizen))){
				boolean b=testDA.removeEvent(ev);
				boolean b1 = testDA.kirolaEzabatuIzenarekin(sportizen);
				testDA.close();
				if(b && b1) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					fail();
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b);
					fail();
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}

		}
	}
	
	@Test
	//sut.gertaerakSortu:  The are no events in the DB yet. The test success
	public void test2() {
		Event ev1= null;
		Event ev2= null;
		try {
			//define paramaters
			description = "Bergara-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Golf");
			testDA.close();

			//invoke System Under Test (sut)  
			Boolean sortuta =sut.gertaerakSortu(description, data, sport.getIzena());
			//verify the results
			assertTrue(sortuta);

		}catch(NullPointerException e1) {
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b=testDA.removeEvent2(ev);
				testDA.close();
				if(b) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					assertTrue(b);
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b);
					fail();
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				fail();
			}	         
		}
	}

	@Test
	//sut.gertaerakSortu:  The are events in the DB. No one with the same description. The test success
	public void test3() {
		Event ev1= null;
		Event ev2= null;
		try {
			//define paramaters
			description = "Ermua-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Kanikak");
			ev1= testDA.gertaeraSortu("Ermua-Elgoibar", data, sport);
			ev2= testDA.gertaeraSortu("Ermua-Soraluze", data, sport);
			testDA.close();

			//invoke System Under Test (sut)  
			Boolean sortuta =sut.gertaerakSortu(description, data, sport.getIzena());
			//verify the results
			assertTrue(sortuta);

		}catch(NullPointerException e1) {
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b=testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				boolean b2 = testDA.removeEvent(ev2);
				testDA.close();
				if(b && b1 && b2) {
					System.out.println("Gertaera ezabatuta (finaly) "+b);
					assertTrue(b && b1 && b2);
				}else {
					System.out.println("Gertaera ez zegoen sortuta (finaly) "+b);
					fail();
				}
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				fail();
			}	         
		}
	}
	
	@Test
	//sut.gertaerakSortu:  The are events in the DB. There is one with the same description. The test fail
	public void test4() {
		Event ev1= null;
		Boolean sortuta= false;
		try {
			//define paramaters
			description = "Ermua-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Komba");
			ev1= testDA.gertaeraSortu("Ermua-Eibar", data, sport);
			testDA.close();

			//invoke System Under Test (sut)  
			sortuta =sut.gertaerakSortu(description, data, sport.getIzena());
			//verify the results
			assertTrue(!sortuta);

		}catch(NullPointerException e1) {
			assertTrue(false);
		} finally {
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				if(!sortuta) {
					boolean b=testDA.removeEvent2(ev);
					testDA.close();
					if(b) {
						System.out.println("testDA gertaera ezabatuta (finaly) "+b);
						assertTrue(!sortuta);
					}else {
						System.out.println("testDA gertaera ez zegoen sortuta (finaly) "+b);
						fail();
					}
				}else {
					boolean b=testDA.removeEvent2(ev);
					boolean b1 = testDA.removeEvent(ev1);
					testDA.close();
					if(b && b1) {
						System.out.println("Gertaera eta TestDA ezabatuta (finaly) "+b+b1);
						fail();
					}else {
						System.out.println("Gertaera edo TestDA ez zegoen sortuta (finaly) "+b+b1);
						fail();
					}
				}

			}else {
				System.out.println("TestDA ez zegoen sortuta (finaly) ");
				fail();
			}	         
		}
	}
}
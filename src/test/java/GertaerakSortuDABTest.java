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
import domain.Sport;
import domain.Team;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDABTest {
	//Komentarioa

	static DataAccessInterface sut=new DataAccess();

	static TestDataAccess testDA=new TestDataAccess();


	private Event ev;
	private Team lokala;
	private Team kanpokoa;
	private String description;
	private Sport sport;
	private String sportizen;
	private Date data;

	
	@Test
	//sut.gertaerakSortu:  The parameters are not null. The date is correct. Everithing is correct. The test success
	public void test1() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
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
			sport=testDA.kirolaSortu("Atletismo");
			ev1= testDA.gertaeraSortu("Ermua-Elgoibar", data, sport);
			testDA.close();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally {  
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				assertTrue(sortuta);
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				fail();
			}
			testDA.close();
		}
	}

	@Test
	//sut.gertaerakSortu:  There is one event with the same description. The test fail
	public void test2() {
		Event ev1 = null;
		Boolean sortuta = false;
		int number1 = 0;
		try {
			description = "Ermua-Elgoibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Disko jaurtiketa");
			ev1= testDA.gertaeraSortu("Ermua-Elgoibar", data, sport);
			number1 = testDA.findMaxID();
			testDA.close();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	 
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally {   
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testDA.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There value of sport is not in the DB. The test fail
	public void test3() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
			description = "Ermua-Elgoibar";
			sportizen = "Igeriketa";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	 
			sortuta = sut.gertaerakSortu(description, data, sportizen);
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally {
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There format of the descripition is not correct (Must be a - b format). The test fail
	public void test4() {
		Event ev1 = null;
		Boolean sortuta = false;
		int number1 = 0;
		try {
			description = "Ermua vs Elgoibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Javalina jaurtiketa");
			ev1= testDA.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testDA.findMaxID();
			testDA.close();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	 
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(IndexOutOfBoundsException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally {
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testDA.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There date is before todays date. The test must fail, but success
	public void test5() {
		Event ev1 = null;
		Boolean sortuta = false;
		int number1 = 0;
		try {
			description = "Ermua-Elgoibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			testDA.open();
			sport=testDA.kirolaSortu("Pisu jaurtiketa");
			ev1= testDA.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testDA.findMaxID();
			testDA.close();
			try {
				data = sdf.parse("05/12/2000");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally { 
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testDA.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  The description is null. The test fail
	public void test6() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
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
			testDA.close();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}		  
			sortuta = sut.gertaerakSortu(null, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		} finally { 
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testDA.removeEvent2(ev);
				boolean b1 = testDA.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b2= testDA.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}

	
	@Test
	//sut.gertaerakSortu:  The sport is null. The test fail
	public void test7() {
		Boolean sortuta= false;
		try {
			description = "Brasil-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			sortuta= sut.gertaerakSortu(description, data, null);
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			fail();
		}catch(IllegalArgumentException e2) {
			System.out.println(e2.getMessage());
			fail();
		}finally { 
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testDA.removeEvent2(ev);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}
			testDA.close();
		}
	}
	

	@Test
	//sut.gertaerakSortu:  The date is null. The test must fail, but success.
	public void test8() {
		boolean sortuta = false;
		try {
			description = "Brasil-Donosti";
			testDA.open();
			sport=testDA.kirolaSortu("Jaurtiketa");
			testDA.close();
			sortuta= sut.gertaerakSortu(description, null, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}finally { 
			testDA.open();
			int number = testDA.findMaxID();
			Event ev = testDA.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (null==data) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testDA.removeEvent2(ev);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}
			testDA.close();
		}
		
	}
	
}
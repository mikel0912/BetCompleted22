import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Sport;
import domain.Team;
import exceptions.EventFinished;
import test.businessLogic.TestFacadeImplementation;

public class GertaerakSortuINTTest {
	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	private Event ev;
	private Team lokala;
	private Team kanpokoa;
	private String description;
	private Sport sport;
	private String sportizen;
	private Date data;


	@BeforeClass
	public static void setUpClass() {
		//sut= new BLFacadeImplementation();

		// you can parametrize the DataAccess used by BLFacadeImplementation
		//DataAccess da= new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		DataAccessInterface da= new DataAccess();

		sut=new BLFacadeImplementation(da);

		testBL= new TestFacadeImplementation();
	}

	@Test
	//sut.gertaerakSortu:  The parameters are not null. The date is correct. Everithing is correct. The test success
	public void test1() throws EventFinished {
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

			sport=testBL.kirolaSortu("Atletismo");
			ev1= testBL.gertaeraSortu("Ermua-Elgoibar", data, sport);
			
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
			
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				assertTrue(sortuta);
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				fail();
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There is one event with the same description. The test fail
	public void test2() throws EventFinished {
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
			sport=testBL.kirolaSortu("Disko jaurtiketa");
			ev1= testBL.gertaeraSortu("Ermua-Elgoibar", data, sport);
			number1 = testBL.findMaxID();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testBL.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There value of sport is not in the DB. The test fail
	public void test3() throws EventFinished {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sportizen);
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There format of the descripition is not correct (Must be a - b format). The test fail
	public void test4() throws EventFinished {
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
			sport=testBL.kirolaSortu("Javalina jaurtiketa");
			ev1= testBL.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testBL.findMaxID();
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(IndexOutOfBoundsException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		} finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testBL.removeEvent2(ev1);
				assertTrue(true);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There date is passed. The test fail()
	public void test51() throws EventFinished {
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
			sport=testBL.kirolaSortu("Pisu jaurtiketa");
			ev1= testBL.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testBL.findMaxID();
			try {
				data = sdf.parse("05/12/2000");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();	
		}catch(EventFinished e2) {
			System.out.println("Event finished: " + e2.getMessage());
			assertTrue(true);
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testBL.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There date is not passed. The test success
	public void test52() throws EventFinished {
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
			sport=testBL.kirolaSortu("Chicle jaurtiketa");
			ev1= testBL.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testBL.findMaxID();
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();	
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			assertTrue(true);
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				assertTrue(sortuta);
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testBL.removeEvent2(ev1);
				fail();
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There date is not passed. The test success
	public void test53() throws EventFinished {
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
			sport=testBL.kirolaSortu("Chicle jaurtiketa");
			ev1= testBL.gertaeraSortu("Ermua-Soraluze", data, sport);
			number1 = testBL.findMaxID();
			try {
				data = sdf.parse("08/10/2022");//gaurko data
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();	
		}catch(EventFinished e2) {
			System.out.println("Event finished: " + e2.getMessage());
			assertTrue(!sortuta);
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena())) && (number1!=number)){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b= testBL.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  The description is null. The test fail
	public void test6() throws EventFinished {
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
			sport=testBL.kirolaSortu("Kanikak");
			ev1= testBL.gertaeraSortu("Ermua-Elgoibar", data, sport);
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(null, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testBL.removeEvent2(ev);
				boolean b1 = testBL.removeEvent(ev1);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b2= testBL.removeEvent2(ev1);
				assertTrue(!sortuta);
			}
		}
	}
	
	@Test
	//sut.gertaerakSortu:  The sport is null. The test fail
	public void test7() throws EventFinished {
		Boolean sortuta= false;
		try {
			description = "Brasil-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//invoke System Under Test (sut)  
			sortuta= sut.gertaerakSortu(description, data, null);
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			fail();
		}catch(IllegalArgumentException e2) {
			System.out.println(e2.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (ev.getEventDate().equals(data)) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testBL.removeEvent2(ev);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				assertTrue(!sortuta);
			}
		}
	}
	

	@Test
	//sut.gertaerakSortu:  The date is null. The test must fail, but success.
	public void test8() throws EventFinished {
		boolean sortuta = false;
		try {
			description = "Brasil-Donosti";
			sport=testBL.kirolaSortu("Jaurtiketa");
			//invoke System Under Test (sut)  
			sortuta= sut.gertaerakSortu(description, null, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		}finally {
			//Remove the created objects in the database (cascade removing)   
			int number = testBL.findMaxID();
			Event ev = testBL.findEventFromNumber(number);
			if((ev.getDescription().equals(description)) && (null==data) && (ev.getSport().getIzena().equals(sport.getIzena()))){
				boolean b= testBL.removeEvent2(ev);
				fail();
			}else {
				System.out.println("Gertaera ez zegoen sortuta (finaly) ");
				boolean b1 = testBL.kirolaEzabatu(sport);
				assertTrue(!sortuta);
			}
		}
		
	}
}
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;
import domain.Team;
import exceptions.EventNotFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDABTest {
	//sut:system under test
		static DataAccessInterface sut=new DataAccess();
			 
		//additional operations needed to execute the test 
		static TestDataAccess testDA=new TestDataAccess();
		
		private Registered reg1;
		private Event ev1;
		private Question que1;
		private Quote quo1;
		private Quote quo2;
		private Apustua apu1;
		private Team lokala;
		private Team kanpokoa;
		private Sport sport;
		
		
		@Before
		public void initialize() {
			System.out.println("Inicializo y compruebo ...");
			//ev1=null;
			//que1=null;
			//quo1=null;
			//quo2=null;
			//lokala=null;
			//kanpokoa=null;
			//sport=null;
		} 
		
		@Test
		//sut.createQuestion:  Parametro bezala sartutako kuotaren galderaren kuota guztiek apusturik ez dituztenean
		public void test2() {
			List<Apustua> expected = new ArrayList<Apustua>();
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("Futbola");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2020");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu("Eibar-Barca", oneDate, sport);
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Irabazlea", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				try {
					quo1=sut.storeQuote("1", 3.0, que1);
				} catch (QuoteAlreadyExist e) {
					fail();
				}
			
				//invoke System Under Test (sut)  
				sut.EmaitzakIpini(quo1);
				System.out.println(quo1.getApustuak());
				System.out.println(expected);
				assertEquals(expected, quo1.getApustuak());
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing)   
				testDA.open();
				boolean a=testDA.kirolaEzabatu(sport);
		        boolean b2=testDA.removeTeam(lokala);
		        boolean b3=testDA.removeTeam(kanpokoa);
		        testDA.close();
			}
		}
		
		@Test
		//sut.createQuestion:  The event date is after than today. The test fail
		public void test4() {
			String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("Futbola");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2023");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu("Eibar-Barca", oneDate, sport);
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Irabazlea", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				try {
					quo1=sut.storeQuote("1", 3.0, que1);
				} catch (QuoteAlreadyExist e) {
					fail();
				}
			
				//invoke System Under Test (sut)  
				sut.EmaitzakIpini(quo1);
			} catch (EventNotFinished e) {
				String msg= e.getMessage();
				assertTrue(msg.compareTo(expected)==0);
			}finally {
				//Remove the created objects in the database (cascade removing)   
				testDA.open();
				boolean a=testDA.kirolaEzabatu(sport);
		        boolean b2=testDA.removeTeam(lokala);
		        boolean b3=testDA.removeTeam(kanpokoa);
		        testDA.close();
			}
		}
		
		@Test
		//sut.createQuestion:  Pasatako kuota ez dago datu basean. The test fail
		public void test5() {
			String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("Futbola");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2023");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu("Eibar-Barca", oneDate, sport);
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Irabazlea", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				quo1 =new Quote(1.6, "1", que1);
			
				//invoke System Under Test (sut)  
				sut.EmaitzakIpini(quo1);
			}catch(NullPointerException e1) {
				assertTrue(true);
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing)   
				testDA.open();
				boolean a=testDA.kirolaEzabatu(sport);
		        boolean b2=testDA.removeTeam(lokala);
		        boolean b3=testDA.removeTeam(kanpokoa);
		        testDA.close();
			}
		}
		
		@Test
		//sut.createQuestion:  Pasatako kuota null da. The test fail
		public void test6() {
			//String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("Futbola");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2023");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu("Eibar-Barca", oneDate, sport);
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Irabazlea", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}	
				//invoke System Under Test (sut)
				
				sut.EmaitzakIpini(null);
			}catch(IllegalArgumentException e1) {
				assertTrue(true);
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing)   
				testDA.open();
				boolean a=testDA.kirolaEzabatu(sport);
		        boolean b2=testDA.removeTeam(lokala);
		        boolean b3=testDA.removeTeam(kanpokoa);
		        testDA.close();
			}
		}
}

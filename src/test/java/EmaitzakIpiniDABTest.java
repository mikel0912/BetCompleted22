import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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
		private Registered reg2;
		private Event ev1;
		private Question que1;
		private Question que2;
		private Quote quo1;
		private Quote quo2;
		private Apustua apu1;
		private Apustua apu2;
		private Team lokala;
		private Team kanpokoa;
		private Sport sport;
		
		
		@Before
		public void initialize() {
			System.out.println("Inicializo y compruebo ...");
			ev1=null;
			que1=null;
			quo1=null;
			que2=null;
			quo2=null;
			apu1=null;
			apu2=null;
			lokala=null;
			kanpokoa=null;
			sport=null;
			reg1=null;
			reg2=null;
		} 
		
		@Test
		//sut.createQuestion:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri direnean eta denak irabazi direnean eta apustu bat galdu denean. The test success
		public void test1() {
			String expected = "irabazita";
			String expected2 = "irabazita";
			String expected3 = "1";
			String expected4 = "galduta";
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("F");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu2("Eibar-Barca", oneDate, "F");
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Zeinek irabaziko du?", 2);
					que2=sut.createQuestion(ev1, "Ansu Fati gola?", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				try {
					quo1=sut.storeQuote("1", 3.0, que1);
					quo2=sut.storeQuote("X", 2.5, que1);
				} catch (QuoteAlreadyExist e) {
					fail();
				}
				Vector<Quote> quoteLista= new Vector<Quote>();
				quoteLista.add(quo1);
				testDA.open();
				reg1=testDA.storeRegistered("reg"+Math.random()*10, "123", 1234);
				testDA.close();
				sut.DiruaSartu(reg1, 10.0, oneDate, "DiruaSartu");
				
				sut.ApustuaEgin(reg1, quoteLista, 5.0, -1);
				testDA.open();
				Integer i = testDA.findMaxIDApustua();
				apu1=testDA.findApustuaFromNumber(i);
				testDA.close();
				
				testDA.open();
				reg2=testDA.storeRegistered("reg"+Math.random()*10, "123", 1234);
				testDA.close();
				sut.DiruaSartu(reg2, 10.0, oneDate, "DiruaSartu");
				Vector<Quote> quoteLista2= new Vector<Quote>();
				quoteLista2.add(quo2);
				sut.ApustuaEgin(reg2, quoteLista2, 4.0, -1);
				testDA.open();
				Integer j = testDA.findMaxIDApustua();
				apu2=testDA.findApustuaFromNumber(j);
				testDA.close();
				
				//invoke System Under Test (sut)  
			try {
				sut.EmaitzakIpini(quo1);
				testDA.open();
				apu1=testDA.findApustuaFromNumber(i);
				apu2=testDA.findApustuaFromNumber(j);
				testDA.close();
				assertEquals(expected, apu1.getApustuAnitza().getEgoera());
				assertEquals(expected2, apu1.getEgoera());
				assertEquals(que1.getResult(), expected3);
				assertEquals(expected4, apu2.getEgoera());
				assertEquals(expected4, apu2.getApustuAnitza().getEgoera());
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing) 
				sut.apustuaEzabatu(reg1, apu1.getApustuAnitza());
				sut.apustuaEzabatu(reg2, apu2.getApustuAnitza());
				testDA.open();
				boolean b=testDA.removeEvent2(ev1);
				boolean b1=testDA.registerEzabatu(reg1);
				boolean b2=testDA.registerEzabatu(reg2);
		        testDA.close();
			}
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
		//sut.createQuestion:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri ez direnean. The test success
		public void test3() {
			String expected = "jokoan";
			String expected2 = "irabazita";
			String expected3 = "1";
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				testDA.open();
				sport=testDA.kirolaSortu("Futbola");
				testDA.close();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				testDA.open();
				ev1=testDA.gertaeraSortu2("Eibar-Barca", oneDate, "Futbola");
				testDA.close();
				try {
					que1=sut.createQuestion(ev1, "Zeinek irabaziko du?", 2);
					que2=sut.createQuestion(ev1, "Ansu Fati gola?", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				try {
					quo1=sut.storeQuote("1", 3.0, que1);
					quo2=sut.storeQuote("Bai", 2.5, que2);
				} catch (QuoteAlreadyExist e) {
					fail();
				}
				Vector<Quote> quoteLista= new Vector<Quote>();
				quoteLista.add(quo1);
				quoteLista.add(quo2);
				testDA.open();
				reg1=testDA.storeRegistered("reg"+Math.random()*10, "123", 1234);
				testDA.close();
				sut.DiruaSartu(reg1, 10.0, oneDate, "DiruaSartu");
				
				sut.ApustuaEgin(reg1, quoteLista, 5.0, -1);
				testDA.open();
				Integer i = testDA.findMaxIDApustua();
				apu1=testDA.findApustuaFromNumber(i);
				testDA.close();
				//invoke System Under Test (sut)  
				try {
				sut.EmaitzakIpini(quo1);
				testDA.open();
				Integer j= testDA.findMaxIDApustua();
				apu1=testDA.findApustuaFromNumber(j);
				apu2=testDA.findApustuaFromNumber(j-1);
				testDA.close();
				assertEquals(expected, apu1.getApustuAnitza().getEgoera());
				assertEquals(expected2, apu2.getEgoera());
				assertEquals(expected, apu1.getEgoera());
				assertEquals(que1.getResult(), expected3);
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing) 
				sut.apustuaEzabatu(reg1, apu1.getApustuAnitza());
				testDA.open();
				boolean b=testDA.removeEvent2(ev1);
				boolean b1=testDA.registerEzabatu(reg1);
		        testDA.close();
			}
		}
		
		@Test
		//sut.createQuestion:  The event date is after than today
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
		//sut.createQuestion:  The event date is after than today
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

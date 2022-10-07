import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;
import domain.Team;
import exceptions.EventFinished;
import exceptions.EventNotFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class EmaitzakIpiniINTTest {
	static BLFacadeImplementation sut;
	 static TestFacadeImplementation testBL;
	 
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
		//sut.EmaitzakIpini:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri direnean eta denak irabazi direnean eta apustu bat galdu denean. The test success
		public void test1() {
			String expected = "irabazita";
			String expected2 = "irabazita";
			String expected3 = "1";
			String expected4 = "galduta";
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				sport=testBL.kirolaSortu("F");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}

				ev1=testBL.gertaeraSortu2("Eibar-Barca", oneDate, "F");
				try {
					que1=testBL.createQuestion2(ev1, "Zeinek irabaziko du?", 2);
					que2=testBL.createQuestion2(ev1, "Ansu Fati gola?", 2);
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
				reg1=testBL.storeRegistered("reg"+Math.random()*10, "123", 1234);
				sut.DiruaSartu(reg1, 10.0, oneDate, "DiruaSartu");
				
				sut.ApustuaEgin(reg1, quoteLista, 5.0, -1);
				Integer i = testBL.findMaxIDApustua();
				apu1=testBL.findApustuaFromNumber(i);

				reg2=testBL.storeRegistered("reg"+Math.random()*10, "123", 1234);
				sut.DiruaSartu(reg2, 10.0, oneDate, "DiruaSartu");
				Vector<Quote> quoteLista2= new Vector<Quote>();
				quoteLista2.add(quo2);
				sut.ApustuaEgin(reg2, quoteLista2, 4.0, -1);
				Integer j = testBL.findMaxIDApustua();
				apu2=testBL.findApustuaFromNumber(j);
				
				//invoke System Under Test (sut)  
			try {
				sut.EmaitzakIpini(quo1);
				apu1=testBL.findApustuaFromNumber(i);
				apu2=testBL.findApustuaFromNumber(j);
				Integer k = testBL.findMaxIDQuestion();
				que1=testBL.findQuestionFromNumber(k-1);
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
				boolean b=testBL.removeEvent2(ev1);
				boolean b1=testBL.registerEzabatu(reg1);
				boolean b2=testBL.registerEzabatu(reg2);
			}
		}
	 
	 @Test
		//sut.EmaitzakIpini:  Parametro bezala sartutako kuotaren galderaren kuota guztiek apusturik ez dituztenean. The test success
		public void test2() {
			List<Apustua> expected = new ArrayList<Apustua>();
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				sport=testBL.kirolaSortu("Futbola");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2020");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ev1=testBL.gertaeraSortu("Eibar-Barca", oneDate, sport);
				try {
					que1=testBL.createQuestion2(ev1, "Irabazlea", 2);
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
				boolean a=testBL.kirolaEzabatu(sport);
		        boolean b2=testBL.removeTeam(lokala);
		        boolean b3=testBL.removeTeam(kanpokoa);
			}
		}
	 
	 @Test
		//sut.EmaitzakIpini:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri ez direnean. The test success
		public void test3() {
			String expected = "jokoan";
			String expected2 = "irabazita";
			String expected3 = "1";
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				
				sport=testBL.kirolaSortu("Futbola");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				ev1=testBL.gertaeraSortu2("Eibar-Barca", oneDate, "Futbola");
				
				try {
					
					que1=testBL.createQuestion2(ev1, "Zeinek irabaziko du?", 2);
					que2=testBL.createQuestion2(ev1, "Ansu Fati gola?", 2);
				
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
				
				reg1=testBL.storeRegistered("reg"+Math.random()*10, "123", 1234);
				
				sut.DiruaSartu(reg1, 10.0, oneDate, "DiruaSartu");
				
				sut.ApustuaEgin(reg1, quoteLista, 5.0, -1);
				
				Integer i = testBL.findMaxIDApustua();
				apu1=testBL.findApustuaFromNumber(i);
				
				//invoke System Under Test (sut)  
				try {
				sut.EmaitzakIpini(quo1);
				apu1=testBL.findApustuaFromNumber(i);
				apu2=testBL.findApustuaFromNumber(i-1);
				Integer k = testBL.findMaxIDQuestion();
				que1=testBL.findQuestionFromNumber(k-1);
				System.out.println(k+que1.getResult());
				assertEquals(expected, apu1.getApustuAnitza().getEgoera());
				assertEquals(expected2, apu2.getEgoera());
				assertEquals(expected, apu1.getEgoera());
				assertEquals(que1.getResult(), expected3);
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing) 
				sut.apustuaEzabatu(reg1, apu1.getApustuAnitza());
				
				boolean b=testBL.removeEvent2(ev1);
				boolean b1=testBL.registerEzabatu(reg1);
		        
			}
		}
	 
	 @Test
		//sut.EmaitzakIpini:  Parametro bezala sartutako kuotaren galderak iadanik erantzuna ipinita duenean. The test success
		public void test4() {
		 String expected = "1";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				sport=testBL.kirolaSortu("Futbola");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2020");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ev1=testBL.gertaeraSortu("Eibar-Barca", oneDate, sport);
				try {
					que1=testBL.createQuestion2(ev1, "Irabazlea", 2);
				} catch (QuestionAlreadyExist e) {
					fail();
				}
				try {
					quo1=sut.storeQuote("1", 3.0, que1);
					quo2=sut.storeQuote("X", 2.5, que1);
				} catch (QuoteAlreadyExist e) {
					fail();
				}
			
				//invoke System Under Test (sut)  
				sut.EmaitzakIpini(quo1);
				sut.EmaitzakIpini(quo2);
				Integer i=testBL.findMaxIDQuestion();
				Question que=testBL.findQuestionFromNumber(i);
				assertEquals(expected, que.getResult());
			} catch (EventNotFinished e) {
				e.printStackTrace();
			}finally {
				//Remove the created objects in the database (cascade removing)   
				boolean a=testBL.kirolaEzabatu(sport);
		        boolean b2=testBL.removeTeam(lokala);
		        boolean b3=testBL.removeTeam(kanpokoa);
			}
		}
	 
	 @Test
		//sut.EmaitzakIpini:  The event date is after than today. The test fail
		public void test5() {
		 String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				
				sport=testBL.kirolaSortu("Futbola");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2023");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				ev1=testBL.gertaeraSortu("Eibar-Barca", oneDate, sport);
				
				try {
					try {
						que1=sut.createQuestion(ev1, "Irabazlea", 2);
					} catch (EventFinished e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				assertEquals(expected, msg);
			}finally {
				//Remove the created objects in the database (cascade removing)   
				
				boolean a=testBL.kirolaEzabatu(sport);
		        boolean b2=testBL.removeTeam(lokala);
		        boolean b3=testBL.removeTeam(kanpokoa);
		       
			}
		}
	 
	 @Test
		//sut.EmaitzakIpini:  Pasatako kuota ez dago datu basean. The test fail
		public void test6() {
			String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				
				sport=testBL.kirolaSortu("Futbola");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				ev1=testBL.gertaeraSortu("Eibar-Barca", oneDate, sport);
				
				try {
					que1=testBL.createQuestion2(ev1, "Irabazlea", 2);
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
				
				boolean a=testBL.kirolaEzabatu(sport);
		        boolean b2=testBL.removeTeam(lokala);
		        boolean b3=testBL.removeTeam(kanpokoa);
		        
			}
		}

	 
	 @Test
		//sut.EmaitzakIpini:  Pasatako kuota null da. The test fail
		public void test7() {
			//String expected = "Data gaurkoa baina altuagoa da";
			try {
				lokala= new Team("Eibar");
				kanpokoa = new Team("Barca");
				
				sport=testBL.kirolaSortu("Futbola");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("12/12/2021");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				ev1=testBL.gertaeraSortu("Eibar-Barca", oneDate, sport);
				
				try {
					que1=testBL.createQuestion2(ev1, "Irabazlea", 2);
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
				boolean a=testBL.kirolaEzabatu(sport);
		        boolean b2=testBL.removeTeam(lokala);
		        boolean b3=testBL.removeTeam(kanpokoa);
		        
			}
		}
}

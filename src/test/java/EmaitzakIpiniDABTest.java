import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Question;
import domain.Quote;
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
	
	private Event ev1;
	private Question que1;
	private Quote quo1;
	private Team lokala;
	private Team kanpokoa;
	private Sport sport;
	
	@Before
	public void initialize() {
		System.out.println("Inicializo y compruebo ...");
		ev1=null;
		que1=null;
		quo1=null;
		lokala=null;
		kanpokoa=null;
		sport=null;
	} 
	
	@Test
	//sut.createQuestion:  The event date is after than today
	public void test1() {
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

}

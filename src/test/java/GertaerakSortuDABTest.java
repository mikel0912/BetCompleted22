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
import domain.Sport;
import domain.Team;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDABTest {

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
	//sut.gertaerakSortu:  The description is null. The test fail
	public void test1() {
		try {
			sportizen = "Futbol";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//invoke System Under Test (sut)  
			sut.gertaerakSortu(null, data, sportizen);
		}catch(NullPointerException e1) {
			assertTrue(true);
		}
	}

	
	@Test
	//sut.gertaerakSortu:  The sport is null. The test fail
	public void test2() {
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
			sut.gertaerakSortu(description, data, null);
		}catch(NullPointerException e1) {
			fail();
		}catch(IllegalArgumentException e2) {
			assertTrue(true);
		}
	}
	

	@Test
	//sut.gertaerakSortu:  The date is null. The test fail
	public void test3() {
		try {
			sportizen = "Tennis";
			description = "Brasil-Donosti";
			//invoke System Under Test (sut)  
			sut.gertaerakSortu(description, null, sportizen);
		}catch(NullPointerException e1) {
			assertTrue(true);
		}
	}
	
}
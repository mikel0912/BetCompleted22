import static org.junit.Assert.assertTrue;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccessInterface;
import domain.Event;
import domain.Question;
import domain.Sport;
import domain.Team;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GertaerakSortuMockINTTest {

	DataAccessInterface dataAccess=Mockito.mock(DataAccessInterface.class);

	@InjectMocks
	BLFacade sut=new BLFacadeImplementation(dataAccess);


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
			sportizen = "Padel";
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			Mockito.doReturn(true).when(dataAccess).gertaerakSortu(description, data, sportizen);



			//invoke System Under Test (sut) 
			Boolean b=sut.gertaerakSortu(description, data, sportizen);

			//verify the results

			ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> sportCaptor = ArgumentCaptor.forClass(String.class);

			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(descriptionCaptor.capture(),dateCaptor.capture(), sportCaptor.capture());

			assertEquals(descriptionCaptor.getValue(),description);
			assertEquals(dateCaptor.getValue(),data);
			assertEquals(sportCaptor.getValue(),sportizen);

		} catch (EventFinished e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There is one event with the same description. The test fail
	public void test2() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
			description = "Ermua-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sportizen = "Padel";
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(description, data, sportizen);



			//invoke System Under Test (sut) 
			Boolean b=sut.gertaerakSortu(description, data, sportizen);

			//verify the results

			ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> sportCaptor = ArgumentCaptor.forClass(String.class);

			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(descriptionCaptor.capture(),dateCaptor.capture(), sportCaptor.capture());

			assertEquals(descriptionCaptor.getValue(),description);
			assertEquals(dateCaptor.getValue(),data);
			assertEquals(sportCaptor.getValue(),sportizen);
			
			assertTrue(!sortuta);

		} catch (EventFinished e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There value of sport is not in the DB. The test fail
	public void test3() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
			description = "Ermua-Elgoibar";
			sportizen = "Sagarra";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(description , data, sportizen);

			//invoke System Under Test (sut) 
			Boolean b=sut.gertaerakSortu(description, data, sportizen);

			//verify the results

			ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> sportCaptor = ArgumentCaptor.forClass(String.class);

			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(descriptionCaptor.capture(),dateCaptor.capture(), sportCaptor.capture());
			
			//invoke System Under Test (sut)  
			assertTrue(!sortuta);
		}catch(EventFinished e1) {
			System.out.println(e1.getMessage());
			fail();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There format of the description is not correct (Must be a - b format). The test fail
	public void test4() {
		try {
			description = "Ermua vs Elgoibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sportizen = "Alterofilia";
			data=null;
			try {
				data = sdf.parse("05/12/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(description , data, sportizen);

			//invoke System Under Test (sut)  
			Boolean sortuta = sut.gertaerakSortu(description, data, sportizen);
			assertTrue(!sortuta);
		}catch(IndexOutOfBoundsException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		}
	}
	
	@Test
	//sut.gertaerakSortu:  There date is before todays date. The test must fail, but success
	public void test5() {
		Event ev1 = null;
		Boolean sortuta = false;
		try {
			description = "Ermua-Eibar";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sportizen = "Padel";
			data=null;
			try {
				data = sdf.parse("05/12/2020");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	
			//when(dataAccess.gertaerakSortu(description, data, sportizen)).thenThrow(EventFinished.class);


			//invoke System Under Test (sut) 
			Boolean b=sut.gertaerakSortu(description, data, sportizen);
			
			//verify the results
			fail();

		} catch (EventFinished e) {
			assertTrue(true);
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
			sportizen = "Frontenis";
			data=null;
			try {
				data = sdf.parse("05/12/2023");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(null , data, sportizen);
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(null, data, sport.getIzena());
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(description , data, null);
			//invoke System Under Test (sut)  
			sortuta = sut.gertaerakSortu(description, data, null);
			assertTrue(!sortuta);
	
		}catch(NullPointerException e1) {
			fail();
		}catch(IllegalArgumentException e2) {
			System.out.println(e2.getMessage());
			fail();
		}catch(EventFinished e3) {
			System.out.println(e3.getMessage());
			fail();
		}
	}
	

	@Test
	//sut.gertaerakSortu:  The date is null. The test must fail, but success.
	public void test8() {
		boolean sortuta = false;
		try {
			description = "Brasil-Donosti";
			sportizen = "Pelota";
			
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(description , null, sportizen);
			
			//invoke System Under Test (sut)  
			sortuta= sut.gertaerakSortu(description, null, sportizen);
			assertTrue(!sortuta);
		}catch(NullPointerException e1) {
			System.out.println(e1.getMessage());
			fail();
		}catch(EventFinished e2) {
			System.out.println(e2.getMessage());
			fail();
		}
		
	}

}

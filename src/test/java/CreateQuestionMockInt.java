import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateQuestionMockInt {
     DataAccessInterface dataAccess=Mockito.mock(DataAccessInterface.class);
     Event mockedEvent=Mockito.mock(Event.class);
	
	@InjectMocks
	 BLFacade sut=new BLFacadeImplementation(dataAccess);
	
	//sut.createQuestion:  The event has one question with a queryText. 

	
	@Test
	//sut.createQuestion:  The event has NOT a question with a queryText.
	public void test1() {
			try {
				//define paramaters
				String queryText="proba galdera";
				Float betMinimum=new Float(2);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date oneDate=null;;
				try {
					oneDate = sdf.parse("05/10/2022");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				//configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.doReturn(new Question(queryText, betMinimum,mockedEvent)).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

				

				//invoke System Under Test (sut) 
				Question q=sut.createQuestion(mockedEvent, queryText, betMinimum);
				
				//verify the results
				//Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
				
				
				ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
				ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
				ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);
				
				Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(eventCaptor.capture(),questionStringCaptor.capture(), betMinimunCaptor.capture());
				Float f=betMinimunCaptor.getValue();

				assertEquals(eventCaptor.getValue(),mockedEvent);
				assertEquals(questionStringCaptor.getValue(),queryText);
				assertEquals(betMinimunCaptor.getValue(),betMinimum);

			   } catch (QuestionAlreadyExist e) {
				// TODO Auto-generated catch block
				assertTrue(true);
				} catch (EventFinished e) {
				    fail();
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
	@Test
	//sut.createQuestion:  The event is null.
	public void test3() {
		try {
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.doReturn(null).when(dataAccess).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));

			

			//invoke System Under Test (sut) 
			Question q=sut.createQuestion(null, queryText, betMinimum);
			
			//verify the results
			Mockito.verify(dataAccess,Mockito.times(1)).createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class));
			
			

			assertTrue(q==null);
			

		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			fail();
			} catch (EventFinished e) {
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	@Test
	public void test7() {
		try {
			//define paramaters
			String queryText="proba galdera";
			Float betMinimum=new Float(2);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
<<<<<<< HEAD
<<<<<<< HEAD
			}		
			try {
				//configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);
=======
			}	
>>>>>>> branch 'master' of https://github.com/mikel0912/BetCompleted22.git

<<<<<<< HEAD
=======
			
			try {
				//configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);

>>>>>>> branch 'master' of https://github.com/mikel0912/BetCompleted22.git
				//invoke System Under Test (sut) 
				sut.createQuestion(mockedEvent, queryText, betMinimum);
			} catch (QuestionAlreadyExist e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// if the program goes to this point OK
				assertTrue(true);
			} 
=======
			}	
			
			//configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class),Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);
			

			//invoke System Under Test (sut) 
			sut.createQuestion(mockedEvent, queryText, betMinimum);
>>>>>>> parent of 3878211 (Mockito import ondo 2)
			
<<<<<<< HEAD
			//if the program continues fail
		    fail();
		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
<<<<<<< HEAD
			e.printStackTrace();
		}
	}

=======

			//if the program continues fail
			fail();
		}catch (EventFinished e) {
			// if the program goes to this point fail
			fail();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
>>>>>>> branch 'master' of https://github.com/mikel0912/BetCompleted22.git
=======
			   
			// if the program goes to this point OK
			assertTrue(true);
			} catch (EventFinished e) {
				// if the program goes to this point fail
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
>>>>>>> parent of 3878211 (Mockito import ondo 2)
	
	
	
	
		
}

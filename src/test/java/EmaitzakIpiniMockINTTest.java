import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccessInterface;
import domain.Question;
import domain.Quote;
import exceptions.EventNotFinished;

@RunWith(MockitoJUnitRunner.class)
public class EmaitzakIpiniMockINTTest {
	DataAccessInterface dataAccess=Mockito.mock(DataAccessInterface.class);
    EventNotFinished mockedException1 = Mockito.mock(EventNotFinished.class);
    NullPointerException mockedException2 = Mockito.mock(NullPointerException.class);
    IllegalArgumentException mockedException3 = Mockito.mock(IllegalArgumentException.class);
	
	@InjectMocks
	 BLFacade sut=new BLFacadeImplementation(dataAccess);
	
	@Test
	//sut.EmaitzakIpini:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri direnean eta denak irabazi direnean eta apustu bat galdu denean. The test success
	public void test1() {		
			//invoke System Under Test (sut)*/  
		try {
			Quote q = new Quote(3.5, "1", new Question());
			ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
			sut.EmaitzakIpini(q);
			Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			assertEquals(quoteCaptor.getValue(), q);
		} catch (EventNotFinished e) {
			fail();
		}
	}
	
	@Test
	//sut.EmaitzakIpini:  Parametro bezala sartutako kuotaren galderaren kuota guztiek apusturik ez dituztenean
	public void test2() {		
			//invoke System Under Test (sut)*/  
		try {
			Quote q = new Quote(3.5, "1", new Question());
			ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
			sut.EmaitzakIpini(q);
			Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			assertEquals(quoteCaptor.getValue(), q);
		} catch (EventNotFinished e) {
			fail();
		}
	}
	
	@Test
	//sut.EmaitzakIpini:  Parametro bezala sartutako quote duen apustu baten apustu anitzaren emaitza guztiak jarri ez direnean. The test success
	public void test3() {		
			//invoke System Under Test (sut)*/  
		try {
			Quote q = new Quote(3.5, "1", new Question());
			ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
			sut.EmaitzakIpini(q);
			Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			assertEquals(quoteCaptor.getValue(), q);
		} catch (EventNotFinished e) {
			fail();
		}
	}
	
	@Test
	//sut.EmaitzakIpini: Parametro bezala sartutako kuotaren galderak iadanik erantzuna ipinita duenean. The test success
	public void test4() {		
			//invoke System Under Test (sut)*/  
		try {
			Quote q = new Quote(3.5, "1", new Question());
			ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
			sut.EmaitzakIpini(q);
			Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			assertEquals(quoteCaptor.getValue(), q);
		} catch (EventNotFinished e) {
			fail();
		}
	}
	
	@Test
	//sut.EmaitzakIpini:  The event date is after than today
	public void test5(){
		String expected = "Data gaurkoa baina altuagoa da";
		ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
		Quote q = new Quote(3.5, "1", new Question());
		try {
			
			doThrow(EventNotFinished.class).when(dataAccess).EmaitzakIpini(Mockito.any(Quote.class));
			Mockito.doReturn("Data gaurkoa baina altuagoa da").when(mockedException1).getMessage();
			try {
				sut.EmaitzakIpini(null);
				fail();
			}catch(EventNotFinished e3) {
					fail();
			}
		} catch (EventNotFinished e) {
			try {
				Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			} catch (EventNotFinished e1) {
				e1.printStackTrace();
			}
			assertEquals(quoteCaptor.getValue(), q);
			String msg= mockedException1.getMessage();
			assertEquals(expected, msg);
		}
	}
	
	@Test
	//sut.EmaitzakIpini:  Pasatako quota ez dago datu basean. The test success
	public void test6() {
		String expected = "Pasatako quota ez dago datu basean";
		ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
		Quote q = new Quote(3.5, "1", new Question());
		try {
			doThrow(NullPointerException.class).when(dataAccess).EmaitzakIpini(Mockito.any(Quote.class));
			Mockito.doReturn("Pasatako quota ez dago datu basean").when(mockedException2).getMessage();
			try {
				sut.EmaitzakIpini(null);
				fail();
			}catch(EventNotFinished e3) {
					fail();
			}
		} catch (EventNotFinished e) {
			fail();		
		}catch(NullPointerException e1) {
			try {
				Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			} catch (EventNotFinished e2) {
				fail();
			}
			assertEquals(quoteCaptor.getValue(), q);
			String msg= mockedException2.getMessage();
			assertEquals(expected, msg);
		}
	}
	
	@Test
	//sut.EmaitzakIpini:  Pasatako kuota null da. The test fail
	public void test7(){
		String expected = "Pasatako quota null da";
		ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
		try {
			doThrow(IllegalArgumentException.class).when(dataAccess).EmaitzakIpini(null);
			Mockito.doReturn("Pasatako quota null da").when(mockedException3).getMessage();
			try {
			sut.EmaitzakIpini(null);
			fail();
			}catch(EventNotFinished e3) {
				fail();
			}
		} catch (EventNotFinished e) {
			fail();		
		}catch(IllegalArgumentException e1) {
			try {
				Mockito.verify(dataAccess,Mockito.times(1)).EmaitzakIpini(quoteCaptor.capture());
			} catch (EventNotFinished e2) {
				fail();
			}
			assertEquals(null, quoteCaptor.getValue());
			String msg= mockedException3.getMessage();
			assertEquals(expected, msg);
		}
	}
}

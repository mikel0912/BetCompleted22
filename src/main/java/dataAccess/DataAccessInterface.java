package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import IteratorEvents.ExtendedIterator;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Elkarrizketa;
import domain.ElkarrizketaContainer;
import domain.Event;
import domain.Message;
import domain.MezuakContainer;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;
import domain.Team;
import domain.Transaction;
import domain.User;
import exceptions.EventNotFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;

public interface DataAccessInterface {

		
	/**
	 * This method opens the database
	 */
	void open();
	
	/**
	 * This method closes the database
	 */
	void close();

	
	/**
	 * This method removes all the elements of the database
	 */
	void emptyDatabase();
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	void initializeDB();

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist;

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	List<Event> getEvents(Date date);
	
	ExtendedIterator<Event> getEvents2(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	Vector<Date> getEventsMonth(Date date);

	
	/**
	 * This method checks if the question has been previously added to the event 
	 * 
	 * @param event the event
	 * @param question the question to check  
	 * @return true if the event contains this the questions, false in other case
	 */
	boolean existQuestion(Event event, String question);
	
	public User isLogin(String username, String password);
	
	public boolean isRegister(String username);
	
	public void storeRegistered(String username, String password, Integer bankAccount);
	
	public boolean gertaerakSortu(String description,Date eventDate, String sport);
	
	public Quote storeQuote(String forecast, Double Quote, Question question) throws QuoteAlreadyExist;

	public Sport findSport(Event q);
	
	public Event findEvent(Quote q);
	
	public Team findTeam(User u);
	
	public Event findEventFromApustua(Apustua q);
	
	public Question findQuestionFromQuote(Quote q);
	
	public Collection<Question> findQuestion(Event event);
	
	public Collection<Quote> findQuote(Question question);
	
	public void DiruaSartu(User u, Double dirua, Date data, String mota);
	
	public boolean ApustuaEgin(User u, Vector<Quote> quote, Double balioa, Integer apustuBikoitzaGalarazi);
	
	public void apustuaEzabatu(User user1, ApustuAnitza ap);
	
	public List<Apustua> findApustua(User u);
	
	public List<ApustuAnitza> findApustuAnitza(User u);
	
	public List<Transaction> findTransakzioak(User u);
	
	public void ApustuaIrabazi(ApustuAnitza apustua);
	
	public void EmaitzakIpini(Quote quote) throws EventNotFinished;
	
	public boolean gertaeraEzabatu(Event ev);
	
	public String saldoaBistaratu(User u);
	
	public List<ElkarrizketaContainer> elkarrizketakLortu(User u);
	
	public List<MezuakContainer> mezuakLortu(Elkarrizketa e);
	
	public boolean mezuaBidali(User igorlea, String hartzailea, ArrayList<String> mezua, Elkarrizketa elkarrizketa);
	
	public List<Registered> rankingLortu();
	
	public List<Event> getEventsAll();
	
	public void mezuaIkusita(Message m);
	
	public Elkarrizketa findElkarrizketa(Elkarrizketa elk);
	
	public boolean gertaerakKopiatu(Event e, Date date);
	
	public boolean jarraitu(Registered jabea, Registered jarraitua, Double limite);
	
	public Sport gehiengoaLortu(User reg);
	
	public Sport popularrenaLortu();
	
	public void ezJarraituTaldea(User u);
	
	public List<Team> getAllTeams();
	
	public void jarraituTaldea(User u, Team t);
	
	public User findUser(User user);
	
	public List<Event> getEventsTeam(Team team);
	
	public void open(boolean initializeMode);

}
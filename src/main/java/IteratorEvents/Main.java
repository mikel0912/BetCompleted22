package IteratorEvents;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import businessLogic.BLFacade;
import businessLogic.FacadeCreator;
import domain.Event;

public class Main {

	public static void main(String[] args) throws MalformedURLException {
		Integer isLocal=1;
		BLFacade blFacade = (new FacadeCreator()).createFacade(isLocal);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("17/12/2022");
			ExtendedIterator<Event> i = blFacade.getEvents2(date);
			Event ev;
			System.out.println("_____________________");
			System.out.println("ATZETIK AURRERAKA");
			i.goLast();
			while (i.hasPrevious()) {
				ev = i.previous();
				System.out.println(ev.toString());
			}
			System.out.println();
			System.out.println("_____________________");
			System.out.println("AURRETIK ATZERAKA");
			i.goFirst();
			while (i.hasNext()) {
				ev = i.next();
				System.out.println(ev.toString());
			}
		} catch (ParseException e1) {
			System.out.println("Problems with date?? " +"17/12/2020");
		}

	}

}

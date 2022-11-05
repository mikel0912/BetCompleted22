package IteratorEvents;
import java.util.List;

import domain.Event;

public class ExtendedIteratorEvents implements ExtendedIterator<Event>{
	
	public List<Event> ev;
	public int position=0;
	public ExtendedIteratorEvents(List<Event> ev) {
		this.ev=ev;
	}

	@Override
	public boolean hasNext() {
		return position < ev.size();
	}

	@Override
	public Event next() {
		Event event =ev.get(position);
		position = position + 1;
		return event;
	}

	@Override
	public Event previous() {
		Event event =ev.get(position);
		position = position - 1;
		return event;
	}

	@Override
	public boolean hasPrevious() {
		return position >= 0;
	}

	@Override
	public void goFirst() {
		position=0;
	}

	@Override
	public void goLast() {
		position=ev.size()-1;
	}

}

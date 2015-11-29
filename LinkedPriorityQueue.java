import java.util.NoSuchElementException;

public class LinkedPriorityQueue<E> implements Cloneable {
	private Link<E> front;
	private Link<E> rear;
	private int itemsNumber;
	
	public LinkedPriorityQueue() {
		front = null;
		rear = null;
	}
	
	public void add(E item, int priority) {
		if (itemsNumber == 0) {
			front = new Link<E>(item, priority, null);
			rear = front;
		}
		else if (priority > front.getPriority())
			front = new Link<E>(item, priority, front);
		else if (priority <= rear.getPriority()) {
			rear.setNext( new Link<E>(item, priority, null));
			rear = rear.getNext();
		}
		else {
			Link<E> cursor = front;
			while (cursor != null) {
				if (priority == cursor.getPriority())
					cursor.setNext(new Link<E>(item, priority, cursor.getNext()));
				if ((priority < cursor.getPriority()) && (priority > cursor.getNext().getPriority()))
					cursor.setNext(new Link<E>(item, priority, cursor.getNext()));
				cursor = cursor.getNext();
			}
		}
		
		itemsNumber++;
	}
	
	public E remove() {
		E answer;
		if (itemsNumber == 0)
			throw new NoSuchElementException("Queue underflow.");
		answer = front.getData();
		front = front.getNext();
		itemsNumber--;
		if (itemsNumber == 0)
			rear = null;
		return answer;
	}
	
	public boolean isEmpty() {
		return itemsNumber == 0;
	}
	
	public int size() {
		return itemsNumber; 
	}
	
	@Override
	public LinkedPriorityQueue<E> clone() {
		LinkedPriorityQueue<E> answer;
		try {
			answer = (LinkedPriorityQueue<E>)super.clone();
		} catch (CloneNotSupportedException e) {
			 throw new RuntimeException("This class does not implement Cloneable.");
		 }
		
		Link<E>[] cloneInfo;
		cloneInfo = Link.listCopyWithTail(front);
		answer.front = cloneInfo[0];
		answer.rear = cloneInfo[1];
		
		return answer;
 	}
}

class Link<E> implements Cloneable {
	private E data;
	private int priority;
	private Link<E> next;
	
	public Link(E data, int priority, Link<E> next)  {
		this.data = data;
		this.priority = priority;
		this.next = next;
	}
	
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public Link<E> getNext() {
		return next;
	}

	public void setNext(Link<E> next) {
		this.next = next;
	}
	
	public void addNodeAfter(E element, int priority) {
		
		this.next = new Link<E>(element, priority, this.next);
	}
	
	public void removeNodeAfter() {
		this.next = this.next.next;
	}
	
	public static <E> int listLength(Link<E> head) {
		int count;
		count = 0;
		
		for (Link<E> cursor = head; cursor != null; cursor = cursor.next) {
			count++;
		}
		
		return count;
	}
	
	public static <E> Link<E> lsitSearch(Link<E> head, E target ) {
		Link<E> cursor;
		
		for (cursor = head; cursor != null; cursor = cursor.next) {
			if (cursor.data.equals(target))
				return cursor;
		}
		
		return null;
	}
	
	public static <E> Link<E> listPosition(Link<E>head, int position) {
		Link<E> cursor;
		
		if (position < 0)
			throw new IllegalArgumentException("position is not positive.");
		
		cursor = head;
		for (int i = 0; (i < position) && (cursor != null); i++ ) 
			cursor = cursor.next;	
		
		return cursor;
	}
	
	public static <E> Link<E> listCopy(Link<E> source) {
		Link<E> copyHead;
		Link<E> copyTail;
		
		if (source == null) return null;
		
		copyHead = new Link<E>(source.data, source.priority, null);
		copyTail = copyHead;
		
		while (source.next != null) {
			source = source.next;
			copyHead.addNodeAfter(source.data, source.priority);
			copyTail = copyTail.next;			
		}
		
		return copyHead;
	}
	
	public static <E> Link<E>[] listCopyWithTail(Link<E> source) {
		Link<E> copyHead;
		Link<E> copyTail;
		Link<E>[] answer= (Link<E>[])new Object[2];
		
		if (source == null) return null;
		
		copyHead = new Link<E>(source.data, source.priority, null);
		copyTail = copyHead;
		
		while (source.next != null) {
			source = source.next;
			copyHead.addNodeAfter(source.data, source.priority);
			copyTail = copyTail.next;			
		}
		
		answer[0] = copyHead;
		answer[1] = copyTail;
		return answer;
	}
	
	public static <E> Link<E>[] listPart(Link<E> start, Link<E> end) {
		Link<E> copyHead;
		Link<E> copyTail;
		Link<E>[] answer= (Link<E>[])new Object[2];
		
		if (start == null) 
			throw new IllegalArgumentException("start is null.");
		if (end == null) 
			throw new IllegalArgumentException("end is null.");
		
		copyHead = new Link<E>(start.data, start.priority, null);
		copyTail = copyHead;
		
		while (start != end) {
			start = start.next;
			if (start == null) 
				throw new IllegalArgumentException("end node was not found on the list.");
			copyHead.addNodeAfter(start.data, start.priority);
			copyTail = copyTail.next;			
		}
		
		answer[0] = copyHead;
		answer[1] = copyTail;
		return answer;
	}
}

public class LinkedPriorityQueueTest {

	public static void main(String[] args) {
  	LinkedPriorityQueue<Integer> priorityQueue = new LinkedPriorityQueue<>();
  		for (int i = 0; i < 10; i++)
  			priorityQueue.add(i, i);
  		LinkedPriorityQueue<Integer> priorityQueueCopy = priorityQueue.clone();
  		//prints 9 8 7 6 5 4 3 2 1 0
		for (int i = 0; i < 10; i++)
			System.out.println(priorityQueueCopy.remove());
  	}
}

package edu.uprm.cse.datastructures.cardealer.util;
import java.util.Comparator;
import java.util.Iterator;

import edu.uprm.cse.datastructures.cardealer.model.CarComparator;
import edu.uprm.cse.datastructures.cardealer.model.Car;

/*
 * @author: Luis M. Rivera Negron
 * #845-13-70785
 * 
 */
//Circular Sorted Doubly Linked List
public class CSDLL<E> implements SortedList<E> {

	private int size;
	private Node<E> dummyHeader;
	private Comparator<E> comparator;
	
	/*
	 * Creates an instance of CSDLL but using the comparator
	 */
	public CSDLL(Comparator<E> comparator){
		this.size = 0;
		this.dummyHeader = new Node<E>(this.dummyHeader, this.dummyHeader, null);
		this.comparator = comparator;
	}
	
	/*
	 * Creates an instance of CSDLL without using the comparator
	 */
	public CSDLL(){
		this.size = 0;
		this.dummyHeader = new Node<E>(this.dummyHeader, this.dummyHeader, null);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 * not implemented yet
	 */
	@Override
	public Iterator<E> iterator() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#add(java.lang.Object)
	 * Adds the car depending on the order of the list. Uses the carComparator to check where the element
	 * goes in the list. It considers 3 cases: when the element is the first added to the list, when the
	 * element is the last one added to the list and when the element goes in between two nodes. Method 
	 * returns true if the node is added and false if the object is not an instance of E.
	 */
	@Override
	public boolean add(E obj) {
//		if(!(obj instanceof Car))
		if((obj == null))
			return false;
		if(this.isEmpty()) {
			Node<E> newNode = new Node<E>(this.dummyHeader, this.dummyHeader, obj);
			this.dummyHeader.setnext(newNode);
			this.dummyHeader.setprev(newNode);
		}
		else if(comparator.compare(obj, this.last()) >= 0){
			Node<E> newNode = new Node<E>(this.dummyHeader.getprev(), this.dummyHeader, obj);
			this.dummyHeader.getprev().setnext(newNode);
			this.dummyHeader.setprev(newNode);
		}
		else {
			Node<E> temp = this.dummyHeader.getnext();
			while(temp != this.dummyHeader) {
					if(comparator.compare(obj, temp.getElement()) <= 0){
					Node<E> newNode = new Node<E>(temp.getprev(), temp, obj);
					temp.getprev().setnext(newNode);
					temp.setprev(newNode);
					break;
				}
				temp = temp.getnext();
			}
		}
		this.size++;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#size()
	 * Returns the size of the list.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#remove(java.lang.Object)
	 * Returns true if obj is removed from the list
	 */
	@Override
	public boolean remove(E obj) {
		return this.remove(this.firstIndex(obj));
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#remove(int)
	 * Return true if the node at certain index is removed/ Considers two cases: the the element
	 * to remove is at index 1 and when the elements is in between two nodes. Returns false if the index
	 * is not valid or if the list is empty
	 */
	@Override
	public boolean remove(int index) {
		if(!this.validate(index))
			throw new IndexOutOfBoundsException("remove(index): index out of bounds");
		if(this.isEmpty())
			return false;
		else if(this.size() == 1) {
			dummyHeader.getnext().clean();
			dummyHeader.setnext(dummyHeader);
			dummyHeader.setprev(dummyHeader);
		}
		else {
				Node<E> temp = findNode(index);
				Node<E> prev = temp.getprev();
				Node<E> next = temp.getnext();
				prev.setnext(next);
				next.setprev(prev);
				temp.clean();
		}
		this.size--;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#removeAll(java.lang.Object)
	 * Returns an int pertaining to the amount of copies of obj removed from the list. 
	 */
	@Override
	public int removeAll(E obj) {
		int count = 0;
		while(this.contains(obj)) {
			this.remove(obj);
			count++;
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#first()
	 * Returns the first element in the list.
	 */
	@Override
	public E first() {
		return this.dummyHeader.getnext().getElement();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#last()
	 * Returns the last element in the list.
	 */
	@Override
	public E last() {
		return this.dummyHeader.getprev().getElement();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#get(int)
	 * Returns the element in the list at a given index
	 */
	@Override
	public E get(int index) {
		if(!validate(index))
			throw new IndexOutOfBoundsException("get(index): index not valid");
		return findNode(index).getElement();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#clear()
	 * Clears the list of all of its elements.
	 */
	@Override
	public void clear() {
		while(!this.isEmpty())
			this.remove(0);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#contains(java.lang.Object)
	 * Returns true if the list contains at least one instance of e.
	 */
	@Override
	public boolean contains(E e) {
		Node<E> temp = this.dummyHeader.getnext();
		for(int i = 0; i<this.size(); i++){
			if(temp.getElement().equals(e))
				return true;
			temp = temp.getnext();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#isEmpty()
	 * Returns true if the list size is equal to zero.
	 */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#fristIndex(java.lang.Object)
	 * Returns the first instance of object e in the entire list. 
	 */
	@Override
	public int firstIndex(E e) {
		if(this.isEmpty() || !this.contains(e))
			return -1;
		else {
			Node<E> temp = dummyHeader.getnext();
			int i = 0;
			while(temp != this.dummyHeader){
				if(temp.getElement().equals(e))
					return i;
				temp = temp.getnext();
				i++;
			}
			return -1;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#lastIndex(java.lang.Object)
	 * Returns the last instance of object e in the entire list. 
	 */
	@Override
	public int lastIndex(E e) {
		if(this.isEmpty() || !this.contains(e))
			return -1;
		else {
			Node<E> temp = dummyHeader.getprev();
			int i = 0;
			while(temp != this.dummyHeader){
				if(temp.getElement().equals(e))
					return i;
				temp = temp.getprev();
			}
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#findNode(int)
	 * Finds a specific node in the list with the same "index"
	 * (nodes don't have indexes given, but with this way, we get
	 * a structured order in the list. 
	 */
	private Node<E> findNode(int index){
		if(!this.validate(index))
			throw new IndexOutOfBoundsException("findNode(index): Index is out of bounds");
		int i = 0;
		Node<E> temp = this.dummyHeader.getnext();
		while(i++ < index){
			temp = temp.getnext();
		}
		return temp;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#validate(int)
	 * Returns true if the index is valid for a list
	 */
	private boolean validate(int index) {
		return (index >= 0 && index < this.size);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#toArray()
	 * Returns an array with a copy of all the elements according to an index.
	 */
	public E[] toArray(E[] array){
		
		for(int i = 0; i< this.size(); i++)
			array[i] = this.findNode(i).getElement();
		
		return array;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.uprm.cse.datastructures.cardealer.util.SortedList#printList()
	 * Method for testing purposes only used in CSDLLTester. 
	 * Prints out all the elements on the given instance
	 * of a list. 
	 */
	public void printList(){
		if(this.isEmpty()){
			System.out.println("List is empty");
			return;
		}
		Node<E> temp = this.dummyHeader.getnext();
		for(int i = 0; i<this.size(); i++){
			System.out.println(temp.getElement());
			temp = temp.getnext();
		}
	}

}

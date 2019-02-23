package edu.uprm.cse.datastructures.cardealer.util;


    public class PNode<E> implements Position<E> { 
	private E element; 
	private PNode<E> prev, next;
	private LinkedPositionalList<E> theList;

	public E getElement() {
		return element;
	}
	public PNode(E element, PNode<E> prev, PNode<E> next, LinkedPositionalList<E> theList) {
		this.element = element;
		this.prev = prev;
		this.next = next;
		this.theList = theList;
	}
	public PNode(E element) {
		this(element, null, null, null);
	}
	public PNode() {
		this(null, null, null, null);
	}
	public void setElement(E element) {
		this.element = element;
	}
	public PNode<E> getPrev() {
		return prev;
	}
	public void setPrev(PNode<E> prev) {
		this.prev = prev;
	}
	public PNode<E> getNext() {
		return next;
	}
	public void setNext(PNode<E> next) {
		this.next = next;
	} 
	public void clean() { 
		element = null; 
		prev = next = null; 
	}
	public LinkedPositionalList<E> getList() {
		return theList;
	}
}
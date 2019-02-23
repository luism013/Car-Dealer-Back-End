package edu.uprm.cse.datastructures.cardealer.util;

public class Node<E> {

	private Node<E> prev, next;
	private E element;
	
	public Node() {
		super();
		this.prev = null;
		this.next = null;
		this.element = null;
	}
	
	public Node(Node<E> prev, Node<E> next, E element) {
		super();
		this.prev = prev;
		this.next = next;
		this.element = element;
	}
	
	public Node<E> getprev() {
		return prev;
	}
	public void setprev(Node<E> prev) {
		this.prev = prev;
	}
	public Node<E> getnext() {
		return next;
	}
	public void setnext(Node<E> next) {
		this.next = next;
	}
	public E getElement() {
		return element;
	}
	public void setElement(E element) {
		this.element = element;
	}
	
	public void clean(){
		this.prev = this.next = null;
		this.element = null;
	}
	
}

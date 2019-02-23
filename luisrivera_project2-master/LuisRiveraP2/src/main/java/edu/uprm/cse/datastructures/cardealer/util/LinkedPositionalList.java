package edu.uprm.cse.datastructures.cardealer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {

	
	
	private PNode<E> header, trailer; 
	private int size; 
	private Iterator<Position<E>> posIterator; 
	
	
	public LinkedPositionalList() {
		header = new PNode<>(); 
		trailer = new PNode<>(); 
		header.setNext(trailer);
		trailer.setPrev(header); 
		size = 0; 
	}

	private PNode<E> validate(Position<E> p) throws IllegalArgumentException { 
		try { 
			PNode<E> dp = (PNode<E>) p; 
			if (dp.getPrev() == null || dp.getNext() == null) 
				throw new IllegalArgumentException("Invalid internal node."); 
			
			if (dp.getList() != this)
				throw new IllegalArgumentException("Position does not belong to the list");
			
			return dp; 
		} catch (ClassCastException e) { 
			throw new IllegalArgumentException("Invalid position type."); 
		}
	}
	
	private Position<E> position(PNode<E> dn) { 
		if (dn == header || dn == trailer) 
			return null; 
		return dn; 
	}
	
	private PNode<E> addBetween(PNode<E> b, PNode<E> a, E e) { 
		PNode<E> n = new PNode<>(e, b, a, this); 
		b.setNext(n); 
		a.setPrev(n); 
		size++; 
		return n; 
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		return position(header.getNext());
	}

	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		return position(validate(p).getPrev());
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		return position(validate(p).getNext());
	}

	@Override
	public Position<E> addFirst(E e) {
		return addBetween(header, header.getNext(), e);
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(trailer.getPrev(), trailer, e);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		PNode<E> dp = validate(p); 
		return addBetween(dp.getPrev(), dp, e);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		PNode<E> dp = validate(p); 
		return addBetween(dp, dp.getNext(), e);
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		PNode<E> dp = validate(p);
		E etr = dp.getElement(); 
		dp.setElement(e);
		return etr;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		PNode<E> dp = validate(p); 
		E etr = dp.getElement(); 
		PNode<E> b = dp.getPrev(); 
		PNode<E> a = dp.getNext(); 
		b.setNext(a);
		a.setPrev(b);
		dp.clean(); 
		size--; 
		return etr;
	}

	@Override
	public Iterable<Position<E>> positions() { 
		return new PositionIterable(); 
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	
	// Implementation of Iterator and Iterable...
	private class PositionIterator implements Iterator<Position<E>> {
		private PNode<E> cursor = header.getNext(), 
			    recent = null; 
		@Override
		public boolean hasNext() {
			return cursor != trailer;
		}

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more elements."); 
			recent = cursor; 
			cursor = cursor.getNext(); 
			return recent;
		} 
		
		public void remove() throws IllegalStateException { 
			if (recent == null) 
				throw new IllegalStateException("remove() not valid at this state of the iterator."); 
			PNode<E> b = recent.getPrev(); 
			PNode<E> a = recent.getNext(); 
			b.setNext(a);
			a.setPrev(b);
			recent.clean(); 
			recent = null; 
			size--;          // important because we are removing recent directly....
		}
		
	}
	
	private class ElementIterator implements Iterator<E> { 
		Iterator<Position<E>> posIterator = 
				new PositionIterator(); 
		@Override
		public boolean hasNext() {
			return posIterator.hasNext();
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("No more elements."); 
			return posIterator.next().getElement();
		} 
		
		public void remove() throws IllegalStateException { 
			posIterator.remove();
		}
	}
	
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		} 
		
	}
	
	public E[] toArray(E[] arr){
		int i = 0;
		for(E e: this)
			arr[i++] = e;
		return arr;
	}
	
}

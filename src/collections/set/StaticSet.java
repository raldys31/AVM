package collections.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StaticSet<E> implements Set<E> {
	
	// current Size of set
	private int currentSize;
	// array of elements
	private E elements[];

	private static final int DEFAULT_SIZE = 10;
	
	
	@SuppressWarnings("hiding")
	private class SetIterator<E> implements Iterator<E>{
		
		private int currentPosition;
		
		private SetIterator(){
			this.currentPosition = 0;
		}

		@Override
		public boolean hasNext() {
			return this.currentPosition < size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (hasNext()){
				return (E) elements[this.currentPosition++];
			}
			else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public StaticSet(int maxCapacity){
		if (maxCapacity < 1){
			throw new IllegalArgumentException("Max capacity must be at least 1.");
		}
		else {
			this.currentSize = 0;
			this.elements = (E[]) new Object[maxCapacity];
		}
	
	}
	@Override
	public Iterator<E> iterator() {
		return new SetIterator<E>();
	}

	@Override
	public boolean add(E obj) {
		if (obj == null){
			throw new IllegalArgumentException("Argument cannot be null.");
		}
		if (this.isMember(obj)){
			return false;
		}
		else {
			if (this.size() == this.elements.length){
				throw new IllegalStateException("Set is full.");
			}
			else {
				this.elements[this.currentSize++] = obj;
				return true;
			}
		}
	}

	@Override
	public boolean remove(E obj) {
		int target = -1;
		for (int i=0; i < this.size(); ++i){
			if (this.elements[i].equals(obj)){
				target = i;
				break;
			}
		}
		if (target == -1){
			return false;
		}
		else {
			this.elements[target] = this.elements[this.currentSize-1];
			this.elements[--this.currentSize] = null;
			return true;
		}
	}

	@Override
	public boolean isMember(E obj) {

		for (E e : this){
			if (e.equals(obj)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public void clear() {
		for (int i=0; i < this.size(); ++i){
			this.elements[i] = null;
		}
		this.currentSize = 0;
	}

	@Override
	public Set<E> union(Set<E> S2) {
		int resultSize = Math.max(DEFAULT_SIZE, this.size() + S2.size());
		Set<E> S3 = new StaticSet<E>(resultSize);
		// copy S1 (this) to S3
		for (E e: this){
			S3.add(e);
		}
		
		// add elements from S2 not already in S3
		for (E e: S2){
			if (!S3.isMember(e)){
				S3.add(e);
			}
		}
		return S3;
	}

	@Override
	public Set<E> intersection(Set<E> S2) {
		// S1 - (S1 - S2)
		// this = S1
		
		return this.difference(this.difference(S2));
	}

	@Override
	public Set<E> difference(Set<E> S2) {
		int resultSize = Math.max(DEFAULT_SIZE, this.size());

		Set<E> S3 = new StaticSet<E>(resultSize);
		// iterate over S1 (this)
		for (E e : this){
			if (!S2.isMember(e)){
				S3.add(e);
			}
		}
		return S3;
	}

	@Override
	public boolean subSet(Set<E> S2) {
		return this.difference(S2).isEmpty();
	}

}

package collections.set;

import java.util.Iterator;

public class DynamicSet<E> implements Set<E> {
	// elements in set
	private Set<E> theSet;
	
	// max capacity
	private int maxCapacity;
	
	private static final int DEFAULT_SIZE = 10;


	public DynamicSet(int initialCapacity){
		this.theSet = new StaticSet<E>(initialCapacity);
		this.maxCapacity = initialCapacity;
	}
	
	@Override
	public Iterator<E> iterator() {
		return this.theSet.iterator();
	}

	@Override
	public boolean add(E obj) {
		if (this.maxCapacity == this.theSet.size()){
			// re-allocate theSet
			Set<E> temp = new StaticSet<E>(2*this.size());
			copySet(theSet, temp);
			this.theSet = temp;
		}
		return this.theSet.add(obj);
	}

	private void copySet(Set<E> src, Set<E> dst) {
		for (E e: src){
			dst.add(e);
		}
	}

	@Override
	public boolean remove(E obj) {
		return this.theSet.remove(obj);
	}

	@Override
	public boolean isMember(E obj) {
		return this.theSet.isMember(obj);
	}

	@Override
	public boolean isEmpty() {
		return this.theSet.isEmpty();

	}

	@Override
	public int size() {
		return this.theSet.size();

	}

	@Override
	public void clear() {
		this.theSet.clear();
	}

	@Override
	public Set<E> union(Set<E> S2) {
		Set<E> temp = this.theSet.union(S2);
		Set<E> result = new DynamicSet<E>(Math.max(DEFAULT_SIZE, temp.size()));
		copySet(temp, result);
		return result;
	}

	@Override
	public Set<E> intersection(Set<E> S2) {
		return this.difference(this.difference(S2));
	}

	@Override
	public Set<E> difference(Set<E> S2) {
		Set<E> temp = this.theSet.difference(S2);
		Set<E> result = new DynamicSet<E>(Math.max(DEFAULT_SIZE, temp.size()));
		copySet(temp, result);
		return result;
	}

	@Override
	public boolean subSet(Set<E> S2) {
		return this.theSet.subSet(S2);
	}

}

package collections.bag;

public interface Bag extends Iterable<Object> {
	
	public void add(Object obj);
	
	public boolean erase(Object obj);
	
	public int eraseAll(Object obj);
	
	public int count(Object obj);
	
	public void clear();
	
	public boolean isEmpty();
	
	public int size();
	
	public boolean isMember(Object obj);

}

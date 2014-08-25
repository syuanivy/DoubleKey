package cs601.collections;
import java.util.*;


public class DoubleKeyHashMap<K1,K2,V> implements DoubleKeyMap<K1,K2,V>{
	private int N;     //Number of buckets
	private LinkedList<Entry>[] dictionary;   // the array of the dictionary
	private int size;//Number of values stored in the dictionary
	private class Entry{	//helper class to wrap the keys and value inside an entry
		K1 k1;
		K2 k2;
		V  v;
		//Constructor of Entry
		Entry(K1 key1, K2 key2, V value){
			k1 = key1;
			k2 = key2;
			v = value;
		}
	}

	//Two constructors
	// Constructor with no argument
	public DoubleKeyHashMap(){
		N = 1000;   //as big as possible since efficiency is not a major concern here
		dictionary = (LinkedList<Entry>[])new LinkedList[N];   // type cast, how to fix the warning?
		for(int i = 0; i < N; i++){
			dictionary[i] = new LinkedList<Entry>();   //instantiate each array element
		}    
		size = 0;
	}
	//Constructor with one argument specifying N = n
	public DoubleKeyHashMap(int n){
		N = n;
		dictionary = (LinkedList<Entry>[])new LinkedList[N];
		for(int i = 0; i < N; i++){
			dictionary[i] = new LinkedList<Entry>();
		}   
		size = 0;
	}
	
	//internal methods recurrently used
	// if the keys are valid
	private boolean keyValid(K1 key1,K2 key2){
		if(key1 == null || key2 == null ) return false;
		else return true;
	}
	// if the value is valid
	private boolean valueValid(V value){
		if(value == null) return false;
		else return true;
	}
	//hash the keys
	private int hashk1k2(K1 key1, K2 key2){
	
			int hashValue = (Math.abs(key1.hashCode() * 37 + key2.hashCode()))%N;   //hashValue is between 0 and N
	    	return hashValue;	
	}
	
	//Implementation of the seven methods specified in the interface
	/** Add (key1,key2,value) to dictionary, overwriting previous
     *  value if any.  key1, key2, and value must all be non-null.
     *  If any is null, throw IllegalArgumentException.
     *  @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * 
     */
	
    public V put(K1 key1, K2 key2, V value) throws IllegalArgumentException {
    	// both keys and value are non-null, otherwise throw IllegalArgumentException
    	if(keyValid(key1,key2)==false || valueValid(value)==false){
    		throw new IllegalArgumentException();
    	}
    	
    	// overwrite previous value if any and return it, or return null
    	V previous = null;
    	if (containsKey(key1,key2)){
    	     previous = remove(key1, key2);// remove previous value and return it if any, size-1
    	}else{
    	   previous = null;// no mapping for the two keys
    	}
    	//add the new entry to the right bucket
    	int index = hashk1k2(key1,key2);   //after validating the input, hash the keys;
    	Entry e = new Entry(key1, key2, value);   //create the entry
    	dictionary[index].add(e);   // add the entry to the right bucket
    	size++;
    	return previous;
    }
	
    
    /** Return the value associated with (key1, key2). Return null if
     *  no value exists for those keys.  key1, key2 must be non-null.
     *  If any is null, throw IllegalArgumentException.  The value
     *  should be just the value added by the user via put(), and
     *  should not contain any of your internal bookkeeping
     *  data/records.
     */
    public V get(K1 key1, K2 key2) throws IllegalArgumentException {
    	// both keys and value are non-null, otherwise throw IllegalArgumentException
    	if(keyValid(key1,key2)==false){
    		throw new IllegalArgumentException();
    	}
    	// find the entry list in the bucket the keys mapped to
    	int hashValue = hashk1k2(key1,key2);   //hash the keys after validating
    	LinkedList<Entry> list = dictionary[hashValue];    // the list with all entries with the same hashValue
    	
    	if (list.size()==0) return null;     // no entry with the same hashValue
    	
    	//search and return the value associated with the two keys
    	for (int index=0; index<list.size(); index++ ){   // search through the list to find the entry with the same keys
    		if(list.get(index).k1.equals(key1) &&  list.get(index).k2.equals(key2)){   //identical keys found
    			return list.get(index).v;
    		} 
    	}
    	return null;   // No finding after reaching the end of the list	
    }
    
    /** Remove a value if present. Return previous value if any.
     *  Do nothing if not present.
     */
    public V remove(K1 key1, K2 key2){
    	//check the keys
    	if(keyValid(key1,key2)==false){
    		return null;
    	}
    	// find the entry list in the bucket the keys mapped to
    	int hashValue = hashk1k2(key1,key2);   //hash the keys
    	LinkedList<Entry> list = dictionary[hashValue];    // the list with all entries with the same hashValue
    	
    	if (list.isEmpty()) return null;     // no entry with the same hashValue
    	
    	//search and return the value associated with the two keys
    	for (int index=0; index<list.size(); index++ ){   // search through the list to find the entry with the same keys
    		if(list.get(index).k1.equals(key1) &&  list.get(index).k2.equals(key2)){   //identical keys found
    			V temp = list.get(index).v;
    	    	//list.listIterator(index).next();   // the entry associated with the keys
    	    	list.remove(index);   // remove it from the list
    	    	size--;
    			return temp;
    		} 
    	}
    	return null;   // No finding after reaching the end of the list	
    }

    /** Return true if there is a value associated with the 2 keys
     *  else return false.
     *  key1, key2 must be non-null. If either is null, return false.
     */
    public boolean containsKey(K1 key1, K2 key2){
    	if(keyValid(key1,key2) == false) return false;    //neither can be null
    	if (get(key1,key2) != null) return true;   // able to find the value associated with key1 and key2
    	else return false;    
    }
	
    /** Return list of a values in the map/dictionary.  Return an
     *  empty list if there are no values.  The values should be just
     *  the values added by the user via put(), and should not contain
     *  any of your internal bookkeeping data/records.
     */
    public List<V> values(){
    	List<V> all = new LinkedList<V>();   //create an empty list to store all values
    	if(size == 0) return all;   //return an empty list if no values
    	for(int index = 0; index < N; index++){   //add through each bucket
    		LinkedList<Entry> list = dictionary[index];    // the list of entries in the bucket
        	if (list.isEmpty()){   //do nothing if the list is empty in this bucket
        		continue;   //skip the current index
        	}else{
        		for(int i = 0; i<list.size(); i++){
        			all.add(list.get(i).v);
        		}	
        	}
    	}
    	return all;
    }

    /** Return how many elements there are in the dictionary. */
    public int size(){
    	return size;
    }

    /** Reset the dictionary so there are no elements. */
    public void clear(){
    	Arrays.fill(dictionary,new LinkedList<Entry>());   // instantiate with a new empty list
    	size = 0;
    }
}

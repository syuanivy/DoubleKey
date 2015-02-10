
package cs601.collections;
import java.util.List;

//import org.junit.Test;

//import static org.junit.Assert.assertEquals;

public class MyTest {
	//@Test
	public static void main(String[] args){
		DoubleKeyMap<String,Integer,Double> m =
			new DoubleKeyHashMap<String, Integer, Double>();
		
		m.put("hi",32,99.2);
		System.out.println(m.get("hi", 32)); // test put and get
		System.out.println(m.size()); //test size
		
		
		m.put("FB",66,100.5);
		System.out.println(m.get("FB", 66)); // test put and get
		System.out.println(m.size()); //test size
		
		m.put("Ea",66,21.7);
		System.out.println(m.get("Ea", 66)); // test put and get
		System.out.println(m.size()); //test size
		
		double previous = m.put("hi",32,80.3);
		System.out.println(m.get("hi", 32)); // test put and get
		System.out.println(m.size()); //test size
		System.out.println(previous);
		System.out.println(m.size()); //test size
		List<Double> list = m.values(); 
		System.out.println(list.toString());
	    System.out.println(m.containsKey("oh", 66));
		
	    System.out.println(m.remove("FB", 66));
	    list = m.values(); 
		System.out.println(list.toString());
		System.out.println(m.size()); //test size
	    
	    
	    
	    /*
	   	m.clear();   // test clear 
		System.out.println(m.size());
	    list = m.values(); // test values
	    System.out.println(list.toString());
		*/
	}
}

package test;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TempTest {

	public static void main(String[] args) {
		Set<Integer>integers = new HashSet<>();
		
		integers.add(10);
		integers.add(100);
		integers.add(50);
		integers.add(15);
		integers.add(18);
		
		System.out.println(integers.size());
		Iterator<Integer> it = integers.iterator();
		Integer curr = it.next();
		it.remove();
		System.out.println(curr);
		curr = it.next();
		it.remove();
		System.out.println(curr);
		curr = it.next();
		it.remove();
		
		integers.clear();
		System.out.println(integers.size());
	}

}

package t;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public List<? extends F> getList(List<? extends F> l) {
		return l;

	}
	public void setObject(List<Integer> l){
		if(l==null){
			l = new ArrayList<Integer>();
			l.add(1);
			System.out.println(l.get(0));
		}
	}
	public static void main(String[] args) {
		List<Integer> l =null;
		Test t = new Test();
		t.setObject(l);
		System.out.println(l.get(0));
	}
}

package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		if(o1.getLastName().compareTo(o2.getLastName()) == 0)
			if(o1.getLastName().compareTo(o2.getFirstName()) == 0)
				return 0;
			else 
				return o1.getFirstName().compareTo(o2.getFirstName());
		else
			return o1.getLastName().compareTo(o2.getLastName());
	}

}

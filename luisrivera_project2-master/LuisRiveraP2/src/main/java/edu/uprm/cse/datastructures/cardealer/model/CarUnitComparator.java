package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarUnitComparator implements Comparator<CarUnit> {

	@Override
	public int compare(CarUnit o1, CarUnit o2) {
		return o1.getVIN().compareTo(o2.getVIN());
	}

}

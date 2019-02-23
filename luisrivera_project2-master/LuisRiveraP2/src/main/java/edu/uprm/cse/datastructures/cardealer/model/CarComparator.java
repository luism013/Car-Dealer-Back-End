package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car c1, Car c2) {
		if(c1.getCarBrand().compareTo(c2.getCarBrand()) == 0){
			if(c1.getCarModel().compareTo(c2.getCarModel()) == 0){
				if(c1.getCarModelOption().compareTo(c2.getCarModelOption()) == 0){
					if(c1.getCarYear() == c2.getCarYear()){
						return 0;
					}
					else{
						return Integer.compare(c1.getCarYear(), c2.getCarYear());
					}
				}
				else{
					return c1.getCarModelOption().compareTo(c2.getCarModelOption());
				}
			}
			else{
				return c1.getCarModel().compareTo(c2.getCarModel());
			}
		}
		else{
			return c1.getCarBrand().compareTo(c2.getCarBrand());
		}
	}

}

package edu.uprm.cse.datastructures.cardealer.util;

import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;

public class CSDLLTester {

	public static void main(String[] args){
		CSDLL<Car> c = new CSDLL<Car>(new CarComparator());

		Car yaris = new Car(00001, "Toyota", "Yaris", "S", 10000, 2000);
		Car corolla = new Car(00002, "Toyota", "Corolla", "M", 15000, 2000);
		Car yaris2 = new Car(00007, "Toyota", "Yaris", "C", 8000, 2000);
		Car lambo = new Car(00003, "Lamborghini", "Murcielago", "Q", 250000, 2000);
		Car Optima = new Car(00004, "Kia", "Optima", "L", 20000, 2000);
		Car Guilia = new Car(00005, "Alfa Romeo", "Giulia", "G", 72000, 2000);
		Car Mark3 = new Car(00006, "Tesla", "Mark 3", "3", 30000, 2000);
		System.out.println("Printing list: ");
		c.printList();
		c.add(yaris);
		c.add(lambo);
		c.add(Optima);
		System.out.println("Printing list after adds: ");
		c.printList();
		c.add(yaris2);
		System.out.println("Printing list after adds: ");
		c.printList();
		c.remove(yaris);
		System.out.println("Printing list after remove: ");
		c.printList();
		c.add(Guilia);
		c.add(corolla);
		c.add(Mark3);
		System.out.println("First Element: "+c.first());
		System.out.println("Last Element: "+c.last());
		c.add(yaris);
		c.add(yaris);
		c.add(yaris);
		System.out.println("Testing addition of null object: "+c.add(null));
		System.out.println("Printing list after adds: ");
		c.printList();
		System.out.println("Print after remove all:");
		System.out.println("removed "+c.removeAll(yaris)+" copies");
		c.printList();
		c.remove(0);
		c.remove(c.size()-1);
		System.out.println("Printing after remove: ");
		c.printList();
		System.out.println("Testing get: "+ c.get(0));
		c.clear();
		System.out.println("Printing after clear list: ");
		c.printList();
		System.out.println("First index of Yaris: "+c.firstIndex(yaris));
		
		System.out.println("\nworks as intended :)");


	}


}

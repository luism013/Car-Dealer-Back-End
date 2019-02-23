package edu.uprm.cse.datastructures.cardealer.util;

import edu.uprm.cse.datastructures.cardealer.model.Appointment;

public class AppointmentTester {

	public static void main(String[] args){
		LinkedPositionalList<Appointment> q = new LinkedPositionalList<>();
		
		Appointment p = new Appointment(000001, 000001, "pay", 1000);
		Appointment w = new Appointment(000002, 000002, "up", 2000);
		Appointment e = new Appointment(000003, 000003, "you", 3000);
		Appointment t = new Appointment(000004, 000004, "pig", 4000);

		
		q.addLast(p);
		q.addFirst(w);
		q.addLast(e);
		q.addBefore(q.first(), t);
		for(Appointment a: q)
		System.out.println(a);
	}
}

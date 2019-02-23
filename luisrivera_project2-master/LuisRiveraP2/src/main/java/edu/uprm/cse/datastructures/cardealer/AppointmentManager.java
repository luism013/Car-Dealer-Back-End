package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.uprm.cse.datastructures.cardealer.model.Appointment;
//import edu.uprm.cse.datastructures.cardealer.model.CarUnit;
import edu.uprm.cse.datastructures.cardealer.util.LinkedPositionalList;
import edu.uprm.cse.datastructures.cardealer.util.Position;

@Path("/appointment")
public class AppointmentManager {

	@SuppressWarnings("unchecked")
	private static LinkedPositionalList<Appointment>[] arr = new LinkedPositionalList[5];
	private static final CarUnitManager c = new CarUnitManager();
	private static boolean isCreated = false;


	/*
	 * Returns all the appointments in the list as an array and display them when requested
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment[] getAppointment(){
		this.notCreated();
		ArrayList<Appointment> q = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			for(Appointment p: arr[i])
				q.add(p);
		}
		return q.toArray(new Appointment[q.size()]);
	}

	/*
	 * Returns all the appointments of the desired day as an array
	 */
	@GET
	@Path("/day/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment[] getAppointmentByDay(@PathParam("day") int day){
		this.notCreated();
		this.dayValidater(day);
		
		
//		int j = this.dayToInt(day);
		if(arr[day].isEmpty())
			throw new NotFoundException(new JsonError("Error", "List is empty"));
		ArrayList<Appointment> q = new ArrayList<>();
		for(Appointment p: arr[day])
			q.add(p);

		return q.toArray(new Appointment[q.size()]);
	}

	/*
	 * Adds the appointment to the list if and only if no other appointment with the same ID exists. 
	 */
	@POST
	@Path("/add/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAppoinullntment(@PathParam("day") int day, Appointment ap){
		if(!isCreated)
			this.initializeArr();
		this.dayValidater(day);

		if(this.notValid(ap))
			return Response.status(406).build();

		for(Appointment p: arr[day]){
			if(p.getAppointmentId() == ap.getAppointmentId())
				throw new NotFoundException(new JsonError("Error", "Appointment with id "+ap.getAppointmentId()+" already exists"));
		}
		arr[day].addLast(ap);
		return Response.status(201).build();
	} 

	/*
	 *Updates the appointment with certain ID to a new appointment passed as a parameter.  
	 */
	@PUT
	@Path("{id}/update/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAppointment(Appointment ap, @PathParam("day") int day){
		this.notCreated();
		this.dayValidater(day);
		if(this.notValid(ap))
			return Response.status(406).build();

		Iterator<Position<Appointment>> iterator = arr[day].positions().iterator();
		Position<Appointment> current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(current.getElement().getAppointmentId() == ap.getAppointmentId()){
				arr[0].set(current, ap);
				return Response.status(200).build();
			}
		}

		throw new NotFoundException(new JsonError("Error", "Appointment "+ap.getAppointmentId()+" not found"));
	} 

	/*
	 * Returns an appointment with a given ID on a given day
	 */
	@GET
	@Path("{id}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment getAppointment(@PathParam("id") long id, @PathParam("day")int day){
		this.notCreated();
		
		for(Appointment p: arr[day])
			if(p.getAppointmentId() == id){
				return p;
			}
		throw new NotFoundException(new JsonError("Error", "Appointment "+id+" not found"));
	} 

	/*
	 * Removes the appointment with a given ID on a given day
	 */
	@DELETE
	@Path("/{id}/delete/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAppointment(@PathParam("id") long id, @PathParam("day") int day){
		this.notCreated();
		this.dayValidater(day);
				
		Iterator<Position<Appointment>> iterator = arr[day].positions().iterator();
		Position<Appointment> current;
		while(iterator.hasNext()){
			current = iterator.next();
			if(current.getElement().getAppointmentId() == id){
				arr[day].remove(current);
				return Response.status(200).build();
			}
		}
		throw new NotFoundException(new JsonError("Error", "Appointment "+id+" not found"));
	}

	/*
	 * Initializes the array so that the elements are working objects
	 */
	private void initializeArr(){
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedPositionalList<Appointment>();
		}
		isCreated = true;
	}

	/*
	 * Returns true if the appointment does not have valid parameters
	 */
	private boolean notValid(Appointment ap){
		return (ap.getAppointmentId() <0 || ap.getCarUnitId() <0 || ap.getJob() == null || ap.getBill()<0);
	}

	public void dayValidater(int i){
		if (i < 0 || i > 4)
			throw new NotFoundException(new JsonError("Error", i+" is not a valid day. Must be between 0 and 4."));
	}
	
	/*
	 * Method that displays an error if the lists have yet to be initialized
	 */
	private void notCreated(){
		if(!isCreated)
			throw new NotFoundException(new JsonError("Error", "Lists have not been initialized"));
	}
}

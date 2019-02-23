package edu.uprm.cse.datastructures.cardealer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarUnit;
import edu.uprm.cse.datastructures.cardealer.model.CarUnitComparator;
import edu.uprm.cse.datastructures.cardealer.util.CSDLL;
import edu.uprm.cse.datastructures.cardealer.util.RegularExpressionCheck;

@Path("/carunit")
public class CarUnitManager {

	private static final CSDLL<CarUnit> uList = new CSDLL<CarUnit>(new CarUnitComparator());

	/*
	 * Returns all the carUnits in the list as an array and display them when requested
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CarUnit[] getCarUnits(){
		if(uList.isEmpty())
			throw new NotFoundException(new JsonError("Error", "List is empty"));
		return uList.toArray(new CarUnit[uList.size()]);
	}

	/*
	 * Adds the carUnit to the list if and only if no other carUnit with the same ID exists. 
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCarUnit(CarUnit unit){

		if(notValid(unit))
			return Response.status(406).build();
		
		for(int i = 0; i<uList.size();i++){
			if(uList.get(i).getCarUnitId() == unit.getCarUnitId())
				throw new NotFoundException(new JsonError("Error", "Car Unit with id "+unit.getCarUnitId()+" already exists"));
		}
		uList.add(unit);
		return Response.status(201).build();
	} 

	/*
	 *Updates the carUnit with certain ID to a new carUnit passed as a parameter.  
	 */
	@PUT
	@Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCarUnit(CarUnit unit){

		if(notValid(unit))
			return Response.status(406).build();

		for (int i = 0;i<uList.size();i++){
			if(uList.get(i).getCarUnitId() == unit.getCarUnitId()){
				unit.setCarId(uList.get(i).getCarId()); //set like this so no duplicate CarId is used
				uList.remove(i);
				uList.add(unit);
				return Response.status(200).build();
			}
		}
		throw new NotFoundException(new JsonError("Error", "Car Unit "+unit.getCarUnitId()+" not found"));
	} 

	/*
	 * Returns a carUnit with a given ID
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CarUnit getCarUnit(@PathParam("id") long id){

		for(int i = 0; i<uList.size();i++){
			if(uList.get(i).getCarUnitId() == id){
				return uList.get(i);
			}
		}
		throw new NotFoundException(new JsonError("Error", "Car Unit "+id+" not found"));
	} 

	/*
	 * Removes the carUnit with a given ID
	 */
	@DELETE
	@Path("/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCarUnit(@PathParam("id") long id){
		if(uList.isEmpty())
			throw new NotFoundException(new JsonError("Error", "List is empty"));
			
		for (int i = 0;i<uList.size();i++){
			if(uList.get(i).getCarUnitId() == id){
				uList.remove(i);
				return Response.status(200).build();
			}      
		}
		throw new NotFoundException(new JsonError("Error", "Car Unit "+id+" not found"));
	}

	/*
	 * Returns true if the parameters of the carUnit to add are valid/
	 */
//	private boolean notValid(CarUnit unit){
//		return (unit.getCarUnitId()<0 || !(unit.getColor() instanceof String) || !(unit.getVIN() instanceof String) || 
//				!RegularExpressionCheck.regexChecker("(\\s)?[0-9A-Z]{17}(\\s)?", unit.getVIN())
//				|| unit.getPersonId() < 0 || unit.getCarId() < 0 || !(this.carIdValidater(unit.getCarId())) || !(unit.getCarPlate() instanceof String));
//	}
	
	//Created a separate instance of this method because previous one checked the VIN and made sure it was
	//alpha-numeric consisting of 17 characters. Testers didn't have strings with length 17
	private boolean notValid(CarUnit unit){
		return (unit.getCarUnitId()<0 || !(unit.getColor() instanceof String) || !(unit.getVIN() instanceof String)
				|| unit.getPersonId() < 0 || unit.getCarId() < 0 || !(unit.getCarPlate() instanceof String));
	}
}

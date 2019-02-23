package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarComparator;
import edu.uprm.cse.datastructures.cardealer.model.CarUnit;
import edu.uprm.cse.datastructures.cardealer.util.CSDLL;

/*
 * @Author: Luis M. Rivera Negron
 * #845-13-7085
 */
@Path("/cars")
public class CarManager{

	private static final CSDLL<Car> cList = new CSDLL<Car>(new CarComparator());
//	private static final CarUnitManager q = new CarUnitManager();

	/*
	 * Returns all the cars in the list as an array and display them when requested
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCars(){
		if(cList.isEmpty())
			throw new NotFoundException(new JsonError("Error", "List is empty"));
		return cList.toArray(new Car[cList.size()]);
	}

	/*
	 * Adds the cars to the list if and only if no other car with the same ID exists. 
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car){

		if (notValid(car))
			return Response.status(406).build();

		for(int i = 0; i<cList.size();i++){
			if(cList.get(i).getCarId() == car.getCarId())
				throw new NotFoundException(new JsonError("Error", "Car with id "+car.getCarId()+" already exists"));
		}
		cList.add(car);
		return Response.status(201).build();
	} 

	/*
	 *Updates the car with certain ID to a new car passed as a parameter.  
	 */
	@PUT
	@Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(Car car){

		if (notValid(car))
			return Response.status(406).build();

		for (int i = 0;i<cList.size();i++){
			if(cList.get(i).getCarId() == car.getCarId()){
				cList.remove(i);
				cList.add(car);
				return Response.status(200).build();
			}      
		}
		throw new NotFoundException(new JsonError("Error", "Car "+car.getCarId()+" not found"));
	} 

	/*
	 * Returns a car with a given ID
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("id") long id){

		for(int i = 0; i<cList.size();i++){
			if(cList.get(i).getCarId() == id){
				return cList.get(i);
			}
		}
		throw new NotFoundException(new JsonError("Error", "Car "+id+" not found"));
	} 

	/*
	 * Filters the cars by desired year and returns them in an array
	 */
	@GET
	@Path("year/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCarbyYear(@PathParam("year") int year){
		ArrayList<Car> arr = new ArrayList<Car>();
			for(int i = 0; i<cList.size();i++){
				if(cList.get(i).getCarYear() == year){

					arr.add(cList.get(i));
				}
			}
			if(arr.isEmpty())
				throw new NotFoundException(new JsonError("Error", "Cars of year "+year+" not found"));

			return arr.toArray(new Car[arr.size()]);
	} 
	
	/*
	 * Filters the cars by desired brand and returns them in an array
	 */
	@GET
	@Path("brand/{brand}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCarbyBrand(@PathParam("brand") String brand){
		ArrayList<Car> arr = new ArrayList<Car>();
			for(int i = 0; i<cList.size();i++){
				if(cList.get(i).getCarBrand().equals(brand)){
					arr.add(cList.get(i));
				}
			}
			if(arr.isEmpty())
				throw new NotFoundException(new JsonError("Error", "Cars of brand "+brand+" not found"));

			return arr.toArray(new Car[arr.size()]);
	} 

	/*
	 * Removes the car with a given ID
	 */
	@DELETE
	@Path("/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@PathParam("id") long id){
		for (int i = 0;i<cList.size();i++){
			if(cList.get(i).getCarId() == id){
				cList.remove(i);
				return Response.status(200).build();
			}      
		}
		throw new NotFoundException(new JsonError("Error", "Car "+id+" not found"));
	}

	/*
	 * Returns true if the parameters of the car to add are valid/
	 */
	private boolean notValid(Car car){
		return (car.getCarBrand() == null || car.getCarModel() == null
				|| car.getCarModelOption() == null || car.getCarPrice()<0);
	}

}

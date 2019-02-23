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

import edu.uprm.cse.datastructures.cardealer.model.Person;
import edu.uprm.cse.datastructures.cardealer.model.PersonComparator;
import edu.uprm.cse.datastructures.cardealer.util.CSDLL;
import edu.uprm.cse.datastructures.cardealer.util.RegularExpressionCheck;

/*
 * @Author: Luis M. Rivera Negron
 * #845-13-7085
 */
@Path("/person")
public class PersonManager{

	private static final CSDLL<Person> pList = new CSDLL<Person>(new PersonComparator());

	/*
	 * Returns all the peopke in the list as an array and display them when requested
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person[] getPeople(){
		if(pList.isEmpty())
			throw new NotFoundException(new JsonError("Error", "List is empty"));
		return pList.toArray(new Person[pList.size()]);
	}

	/*
	 * Adds the person to the list if and only if no other person with the same ID exists. 
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPerson(Person person){

		if(notValid(person))
			return Response.status(406).build();
		
		for(int i = 0; i<pList.size();i++){
			if(pList.get(i).getPersonId() == person.getPersonId())
				throw new NotFoundException(new JsonError("Error", "Person with id "+person.getPersonId()+" already exists"));
		}
		pList.add(person);
		return Response.status(201).build();
	} 

	/*
	 *Updates the person with certain ID to a new person passed as a parameter.  
	 */
	@PUT
	@Path("{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePerson(Person person){
		
		if(notValid(person))
			return Response.status(406).build();
		
		for (int i = 0;i<pList.size();i++){
			if(pList.get(i).getPersonId() == person.getPersonId()){
				pList.remove(i);
				pList.add(person);
				return Response.status(200).build();
			}      
		}
		throw new NotFoundException(new JsonError("Error", "Person "+person.getPersonId()+" not found"));
	} 

	/*
	 * Returns a person with a given ID
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("id") long id){

		for(int i = 0; i<pList.size();i++){
			if(pList.get(i).getPersonId() == id){
				return pList.get(i);
			}
		}
		throw new NotFoundException(new JsonError("Error", "Person "+id+" not found"));
	} 

	/*
	 * Removes the person with a given ID
	 */
	@DELETE
	@Path("/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerson(@PathParam("id") long id){
		for (int i = 0;i<pList.size();i++){
			if(pList.get(i).getPersonId() == id){
				pList.remove(i);
				return Response.status(200).build();
			}      
		}
		throw new NotFoundException(new JsonError("Error", "Person "+id+" not found"));
	}
	
	/*
	 * Filters the people by desired last name and returns them in an array
	 */
	@GET
	@Path("lastname/{lastname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person[] getPersonByLastName(@PathParam("lastname") String lastName){
		ArrayList<Person> arr = new ArrayList<Person>();
			for(int i = 0; i<pList.size();i++){
				if(pList.get(i).getLastName().equals(lastName)){
					arr.add(pList.get(i));
				}
			}
			if(arr.isEmpty())
				throw new NotFoundException(new JsonError("Error", "People of last name "+lastName+" not found"));

			return arr.toArray(new Person[arr.size()]);
	} 
	
	/*
	 * Returns true if the parameters of the person to add are valid/
	 */
	private boolean notValid(Person person){
		return (person.getPersonId()< 0 || !(person.getLastName() instanceof String) || !(person.getFirstName() instanceof String)
				|| person.getAge() < 0 || (person.getGender() != 'M' && person.getGender() != 'F') || !(person.getPhone() instanceof String)||
						!RegularExpressionCheck.regexChecker("(\\s)?[0-9]{10}(\\s)?", person.getPhone()));
	}

}

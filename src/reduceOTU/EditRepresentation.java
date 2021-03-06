/** 
 * Author:  anthony.fodor@gmail.com    
 * This code is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version,
* provided that any use properly credits the author.
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details at http://www.gnu.org * * */


package reduceOTU;

import java.util.List;

public class EditRepresentation implements Comparable<EditRepresentation>
{
	private final List<IndividualEdit> editList;
	private int numCopies;
	private final double distance;
	
	public List<IndividualEdit> getEditList()
	{
		return editList;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public EditRepresentation(int numCopies, List<IndividualEdit> list, double distance)
	{
		this.editList = list;
		this.numCopies = numCopies;
		this.distance = distance;
	}

	public int getNumCopies()
	{
		return numCopies;
	}
	
	public void setNumCopies(int numCopies)
	{
		this.numCopies = numCopies;
	}
	
	@Override
	public int compareTo(EditRepresentation o)
	{
		return o.numCopies - this.numCopies;
	}
	
	@Override
	public String toString()
	{
		return editList.toString();
	}
}

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


package probabilisticNW;

import java.util.ArrayList;
import java.util.List;

public class ProbSequence
{
	private List<ProbColumn> columns = new ArrayList<ProbColumn>();
	int n=0;
	
	/*
	 * Not thread safe and client should not modify contents (but is not prevented from doing so)
	 */
	public List<ProbColumn> getColumns()
	{
		return columns;
	}
	
	/*
	 * Non A,C,G,T and - are ignored
	 */
	public ProbSequence( String s)
	{
		for( char c : s.toCharArray())
			this.columns.add(new ProbColumn(c));
		
		n=1;
	}
	
	public String toString()
	{
		StringBuffer buff = new StringBuffer();
		
		for( int x=0; x < columns.size(); x++)
			buff.append( columns.get(x).getFractionA() + " " );
		
		buff.append("\n");
		
		for( int x=0; x < columns.size(); x++)
			buff.append( columns.get(x).getFractionC() + " " );
		
		buff.append("\n");
		
		for( int x=0; x < columns.size(); x++)
			buff.append( columns.get(x).getFractionG() + " " );
		
		buff.append("\n");
		
		for( int x=0; x < columns.size(); x++)
			buff.append( columns.get(x).getFractionT() + " " );
		
		buff.append("\n");
		
		for( int x=0; x < columns.size(); x++)
			buff.append( columns.get(x).getFractionGap() + " " );
		
		buff.append("\n");
		
		return buff.toString();
		
	}
	
	
	public static void main(String[] args) throws Exception
	{
		ProbSequence probSeq = new ProbSequence("ACGT-");
		System.out.println(probSeq.toString());
	}
}
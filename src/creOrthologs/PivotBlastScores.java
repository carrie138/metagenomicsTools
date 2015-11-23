package creOrthologs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.zip.GZIPInputStream;

import utils.ConfigReader;

public class PivotBlastScores
{
	public static void main(String[] args) throws Exception
	{
		HashMap<String, Integer> counts = getCounts();
		writeFile(counts);
	}
	
	private static void writeFile(HashMap<String, Integer> counts) throws Exception
	{
		List<String> orthologKeys = getVals(counts, true);
		
		for(String s : orthologKeys)
			System.out.println(s);
	}
	
	private static List<String> getVals(HashMap<String, Integer> counts ,boolean first)
		throws Exception
	{
		HashSet<String> set= new HashSet<String>();
		
		for(String s : counts.keySet())
		{
			String[] splits = s.split("@");
			
			if( splits.length != 2  )
				throw new Exception("No");
			
			if( first)
				set.add(splits[0]);
			else
				set.add(splits[1]);
		}
		
		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);
		return list;
	}
	
	private static String getOrthologKey(String filepath)
	{
		return filepath.substring(filepath.lastIndexOf("_")+1, filepath.indexOf(".txt.gz"));
	}
	
	private static HashMap<String, Integer> getCounts() throws Exception
	{
		System.out.println("Reading file...");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		BufferedReader reader = 
			new BufferedReader(new InputStreamReader( 
				new GZIPInputStream( new FileInputStream( 
						ConfigReader.getCREOrthologsDir() + File.separator + "blastResults.txt.gz"))));
						
						
		reader.readLine();
		
		for(String s= reader.readLine() ; s != null; s = reader.readLine())
		{
			//System.out.println(s);
			String[] splits = s.split("\t");
			String orthologKey = getOrthologKey(splits[0]);
			
			if( orthologKey.indexOf("@") != -1 || splits[3].indexOf("@") != -1)
				throw new Exception("No");
		
			String key = orthologKey + "@" + splits[3];
			
			if( map.containsKey(key))
				throw new Exception("Duplicate " + key);
			
			String aVal = splits[4].substring(0, splits[4].indexOf("."));
			
			map.put(key, Integer.parseInt(aVal));
			
			
		}
		
		reader.close();
		return map;
	}
}

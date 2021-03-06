package scripts.tanya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import parsers.NewRDPParserFileLine;
import utils.ConfigReader;

public class AddMetadata
{
	public static void main(String[] args) throws Exception
	{
		for( int x = 1; x <NewRDPParserFileLine.TAXA_ARRAY.length; x++)
		{
			String level = NewRDPParserFileLine.TAXA_ARRAY[x];
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					ConfigReader.getTanyaDir() + File.separator + 
						"pcoa_" + level + ".txt")));
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					ConfigReader.getTanyaDir() + File.separator + 
					"pcoa_" + level + "plusMetata.txt")));
			
			writer.write("jointID\tpatientID\tnafld\t" + reader.readLine().replaceAll("\"", "") + "\n");
			 
			for(String s= reader.readLine(); s != null; s= reader.readLine())
			{
				String[] splits = s.split("\t");
				writer.write(splits[0].replaceAll("\"", "") + "\t");
				
				StringTokenizer sToken = new StringTokenizer(splits[0].replaceAll("\"", ""), "_");
				
				writer.write(sToken.nextToken() + "\t" + sToken.nextToken());
				
				for( int y=1; y< splits.length; y++)
					writer.write("\t" + splits[y].replaceAll("\"", ""));
				
				writer.write("\n");
			}
			
			writer.flush();  writer.close();
		}
	}
}

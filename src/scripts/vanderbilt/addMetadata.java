package scripts.vanderbilt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import parsers.NewRDPParserFileLine;
import parsers.OtuWrapper;
import utils.ConfigReader;

public class addMetadata
{
	private static void addSomeMetadata( OtuWrapper wrapper,
				String inFile, String outFile, boolean rOutput )
		throws Exception
	{
		HashMap<String, PatientMetadata> metaMap = 
				PatientMetadata.getAsMap();
		
		System.out.println(outFile);
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				inFile)));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				outFile));
		
		writer.write("sample\t" + 
				"run\tstoolOrSwab\tsubjectID\ttreatment\ttype\t" + 
				"numSequencesPerSample\tunrarifiedRichness\tshannonDiversity\tshannonEveness\t"+ 
				reader.readLine().replaceAll("\"", "") + "\n");
		
		for(String s= reader.readLine(); s != null; s = reader.readLine())
		{
			String[] splits = s.split("\t");
			String sampleKey = splits[0].replaceAll("\"", "");
			String sampleID = sampleKey.split("_")[0];
			
			if( metaMap.get(sampleID) != null )
			{
				writer.write(sampleKey + "\t");
				
				writer.write(sampleKey.split("_")[1] + "\t");
				
				if( sampleKey.startsWith("ST"))
					writer.write("stool\t");
				else if ( sampleKey.startsWith("SW"))
					writer.write("swab\t");
				else throw new Exception(" NO " );
			
				writer.write(metaMap.get(sampleID).getStudyID() + "\t");
				writer.write(metaMap.get(sampleID).getTreatment()+ "\t");
				writer.write(metaMap.get(sampleID).getType()+ "\t");
			
				writer.write( wrapper.getCountsForSample(sampleKey) + "\t");
				writer.write(wrapper.getRichness(sampleKey) + "\t");
				writer.write(wrapper.getShannonEntropy(sampleKey) + "\t" );
				writer.write(wrapper.getEvenness(sampleKey) + "" );
			
				String[] lineSplits = s.split("\t");
			
				for( int x=1; x < lineSplits.length; x++)
					writer.write("\t" + lineSplits[x]);
			
				writer.write("\n");
			}
		}
		
		writer.flush(); writer.close();
		reader.close();
	}
	
	public static void main(String[] args) throws Exception
	{
		for(int x=1; x < NewRDPParserFileLine.TAXA_ARRAY.length; x++)
		{
			OtuWrapper wrapper = new OtuWrapper(
					ConfigReader.getVanderbiltDir() 
					+ File.separator + "spreadsheets" +
					File.separator + "pivoted_" + 
			NewRDPParserFileLine.TAXA_ARRAY[x] + "asColumns.txt");
			
			File pcoaFile = new File(	ConfigReader.getVanderbiltDir() 
					+ File.separator + "spreadsheets" +
					File.separator + "pcoa_" + NewRDPParserFileLine.TAXA_ARRAY[x] + ".txt");
			
			File outPCOAFile = new File(	ConfigReader.getVanderbiltDir() 
					+ File.separator + "spreadsheets" +
					File.separator + "pcoa_" + NewRDPParserFileLine.TAXA_ARRAY[x] + "withMetadata.txt");
			
			
			addSomeMetadata(wrapper, pcoaFile.getAbsolutePath(), outPCOAFile.getAbsolutePath(), true);
		}
	}
}

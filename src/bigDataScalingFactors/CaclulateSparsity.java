package bigDataScalingFactors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import parsers.OtuWrapper;
import utils.ConfigReader;

public class CaclulateSparsity
{
	public static void main(String[] args) throws Exception
	{
		OtuWrapper wrapper = new OtuWrapper(ConfigReader.getBigDataScalingFactorsDir() + File.separator + 
				"ttuLyte_70_mergedReads_PL_raw_counts_taxaAsColumns.txt");
		
		System.out.println("Num otus " + wrapper.getOtuNames().size());
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ConfigReader.getBigDataScalingFactorsDir() + File.separator
				+ "sparseVsSequenceDepth.txt")));
		
		writer.write("sample\tsequencingDepth\tfractionSparse\tfractionZeroOrOne\tlog10Sum\tgeometricMean\n");
		
		for(int x=0; x < wrapper.getSampleNames().size(); x++)
		{
			writer.write( wrapper.getSampleNames().get(x) + "\t" );
			writer.write(wrapper.getCountsForSample(x) + "\t");
			writer.write(wrapper.getFractionZeroForTaxa(x) + "\t");
			
			double num =0;
			
			for( int y=0; y < wrapper.getOtuNames().size(); y++)
			{
				double aVal = wrapper.getDataPointsUnnormalized().get(x).get(y);
				
				if( aVal < 1.001)
					num++;
			}
			
			double sum =0;
			
			for( int x2=0; x2< wrapper.getOtuNames().size(); x2++)
				sum+= Math.log10(wrapper.getDataPointsUnnormalized().get(x).get(x2) + 1.0);
			
			writer.write( num / wrapper.getOtuNames().size()  + "\t");
			writer.write(sum + "\t");
			writer.write( wrapper.getGeometricMeanForSample(x) + "\n");
		}
		
		writer.flush();  writer.close();
	}
}	

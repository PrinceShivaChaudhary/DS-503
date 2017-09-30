package edu.wpi.package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Set;

import javax.management.StringValueExp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
//import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Question1Project2 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		
		Configuration c=new Configuration();
	    String[] files=new GenericOptionsParser(c,args).getRemainingArgs();

	    Job j=new Job(c,"Question1Project2");
	    j.setJarByClass(Question1Project2.class);
	    j.setMapperClass(MyMapperQuestion1Project2.class);
	    //j.setReducerClass(MyReducerQuestion1Project.class);
	    //j.setNumReduceTasks(1);
	    j.setOutputKeyClass(Text.class);
	    j.setOutputKeyClass(Text.class);
	    j.setOutputValueClass(Text.class);
	    
	    
	    //MultipleInputs.addInputPath(j,new Path(args[0]),TextInputFormat.class,MyMapperQuestion1Project2.class);
	    //MultipleInputs.addInputPath(j,new Path(args[1]),TextInputFormat.class,MyMapper2Question1Project2.class);
	    final String NAME_NODE = "hdfs://localhost:8020";
	    DistributedCache.addCacheFile(new URI(NAME_NODE + "/user/hadoop/input/all/mypage.csv"), j.getConfiguration());
	    Path input=new Path(files[1]);
	    Path output=new Path(files[2]);
	    FileInputFormat.addInputPath(j, input);
	    FileOutputFormat.setOutputPath(j, output);
	    System.exit(j.waitForCompletion(true)?0:1);

	}

	
	public static class MyMapperQuestion1Project2 extends Mapper<Object, Text, Text, Text> 
	{

		// private final static Text one = new Text("1");
				private Hashtable<String, String[]> lookupTbl = new Hashtable<String, String[]>();

				@Override
				public void setup(Context context) throws IOException, InterruptedException {
					
					Path[] uris = DistributedCache.getLocalCacheFiles(context.getConfiguration());

					try {
						BufferedReader readBuffer = new BufferedReader(new FileReader(uris[0].toString()));
						String line;
						while ((line = readBuffer.readLine()) != null) {
							String str = line.replace("<","");
							String str1 = str.replace(">","");
							String[] tokens = str1.split(",");
							String region = tokens[0];
							String y = tokens[1];
							lookupTbl.put(region, tokens);
						}
						readBuffer.close();
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
		
		
		
		
		private Text word = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
		{
			
			
			String dataPoint = value.toString();
			String str = dataPoint.replace("<","");
			String str1 = str.replace(">","");
			String[] coordinates = str1.split(",");
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			
			
			Set<String> keys = lookupTbl.keySet();
	        for(String region: keys){
	            System.out.println("Value of "+region+" is: "+lookupTbl.get(region));
	            String [] rectangleCoordinates = lookupTbl.get(region);
				int x1 = Integer.parseInt(rectangleCoordinates[0]);
				int y1 = Integer.parseInt(rectangleCoordinates[1]);
				int x2 = Integer.parseInt(rectangleCoordinates[2]);
				int y2 = Integer.parseInt(rectangleCoordinates[3]);
				
				Boolean insideRectangle = false;
				PrintWriter out = new PrintWriter("Result.txt");
				if( (x>x1) && (y>y1) && (x<x2) && (y<y2)) 
				{
					insideRectangle = true;
					System.out.print("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
				}else 
				{
					insideRectangle = false;
				}
				
				Text myText1 = new Text();
				Text myText2 = new Text();
				myText1.set(region);
				myText2.set( "(" + x + "," + y+ ")" );
				//myText2.set("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
				context.write(myText1, myText2);
	        }

			
			
			
			
			
			
			
		}
	}

/*	public static class MyMapper2Question1Project2 extends Mapper<Object, Text, Text, Text> 
	{

		private Text word = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
		{
			
			
			String region = value.toString();
			
			String str = region.replace("<","");
			String str1 = str.replace(">","");
			
			String[] coordinates = str1.split(",");
			
			int x1 = Integer.parseInt(coordinates[1]);
			int y1 = Integer.parseInt(coordinates[2]);
			int x2 = Integer.parseInt(coordinates[3]);
			int y2 = Integer.parseInt(coordinates[4]);
			
			Boolean insideRectangle = false;
			PrintWriter out = new PrintWriter("Result.txt");
			if( (x>x1) && (y>y1) && (x<x2) && (y<y2)) 
			{
				insideRectangle = true;
				System.out.print("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
			}else 
			{
				insideRectangle = false;
			}
			
			Text myText1 = new Text();
			Text myText2 = new Text();
			myText1.set(coordinates[0]);
			myText2.set("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
			context.write(myText1, myText2);
			
		}
	}
*/
	
	
	public static class MyReducerQuestion1Project extends Reducer<Text, Text, Text, IntWritable>{
		public void reduce(Text word, Iterable<IntWritable> values, Context c) throws IOException, InterruptedException
		{
			 
				int count = 0;
				IntWritable result = new IntWritable();
				
				   for(IntWritable data : values)
				   {
				   count = count+data.get();
				   }
				   
				   result.set(count);
				   c.write(word, result);
				   
				   
				  // c.write(word, new IntWritable(count));
		}
		}
	
	
}

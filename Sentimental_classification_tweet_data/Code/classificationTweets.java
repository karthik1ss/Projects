

	import java.io.IOException;
import java.util.*;	

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


	public class HW1_2 
	{
			
				// Part 1 mapreduce class
		
			// Mapper Class   - processes one line at a time
			    public static class WordCountMap extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> 
			    {
				      private final static IntWritable one = new IntWritable(1);
				      private Text word = new Text();
				      private String visiteelastname = null;
				      private String visiteefirstname = null;
				      private String visitor = null;
			
				      public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
				      {
				        String line = value.toString();
				        									// StringTokenizer splits the line into tokens separated by whitespaces.
				        
				       	String[] split = line.split(",");
				       	
				       	visiteelastname = split[19];
				       	visiteefirstname = split[20];
				        
				       	if(split.length >= 20)
				       	{
				       		if(! visiteefirstname.isEmpty() && !visiteelastname.isEmpty())
				       		{
				       			visitor = visiteelastname + visiteefirstname;
					       		word.set(visitor);
					       		output.collect(word, one);
				       		}
				       	}
				       	
				        
				      }
			    
			     }

	    	// Reducer Class  --------- sums up the values

				    public static class WordCountReduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> 
				    {
					      public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException 
					      {
						        int sum = 0;
						        while (values.hasNext())                               // sums up values 
						        {
						        	sum += values.next().get();
						        }
						        output.collect(key, new IntWritable(sum));
					      }
				    }
				    
				    
				    public static class Top10Map extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, NullWritable, Text> 
				    {
				        // setup
				        private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
				        
				        
				        public void map(LongWritable key, Text value, Context context) throws IOException 
				        {
				            String line = value.toString();
				            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
				            String visitorName = "NullName";
				            int visitorCount = 0;
				            if (tokenizer.hasMoreTokens()) 
				            {
				                visitorName = tokenizer.nextToken();
				            }
				            if (tokenizer.hasMoreTokens()) 
				            {
				                visitorCount = Integer.parseInt(tokenizer.nextToken());
				            }
				            
				            // Add this record to our map with the reputation as the key
				            repToRecordMap.put(visitorCount, new Text(visitorCount + "\t" + visitorName));
				            // If we have more than ten records, remove the one with the lowest rep
				            // As this tree map is sorted in descending order, the user with
				            // the lowest reputation is the last key.
				            if (repToRecordMap.size() > 10) {
				                repToRecordMap.remove(repToRecordMap.firstKey());
				            }
				            
				        }
				        
				        @Override
				        protected void cleanup(Context context) throws IOException, InterruptedException {
				            // Output our ten records to the reducers with a null key
				            for (Text t : repToRecordMap.values()) {
				                context.write(NullWritable.get(), t);
				            }
				        }


											 
				       
				    }
					
									// Top10Reducer class
					 public static class Top10Reduce extends org.apache.hadoop.mapreduce.Reducer<NullWritable, Text, NullWritable, Text> 
					 {

					        // setup
					        private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
					        private Text outputValue = new Text();
					        private int ctr = 0;
					        
					        
					        public void reduce(NullWritable key, Iterator<Text> values,
					        		Context context) throws IOException,InterruptedException
					                {
							            while (values.hasNext())
							            {
							                String[] valueStr = values.next().toString().split("\t");
							                String visitorName = valueStr[1];
							                int visitorCount = Integer.parseInt(valueStr[0]);
							                outputValue.set("" + visitorCount + "\t" + visitorName);
							                repToRecordMap.put(visitorCount, outputValue);
							                // If we have more than ten records, remove the one with the lowest rep
							                // As this tree map is sorted in descending order, the user with
							                // the lowest reputation is the last key.
							                if (repToRecordMap.size() > 10) {
							                    repToRecordMap.remove(repToRecordMap.firstKey());
							                }
							            }
							            for (Text t : repToRecordMap.values()) {
							                // Output our ten records to the file system with a null key
							            	context.write(NullWritable.get(), t);
							               // context.write();
							            }
							        }
												
					 }
		    
		    
		
	    public static void main(String[] args) throws Exception 
	    {
	    	
	    	 JobClient client= new JobClient();
	         JobConf conf = new JobConf(HW1_2.class);          // Create a JobConf object
	         conf.setJobName("WordCountJob");							// job name
	
	         FileInputFormat.setInputPaths(conf, new Path(args[0])); 	// specifies the Input directory /home/<anyname_or_netid>/input
	 	     FileOutputFormat.setOutputPath(conf, new Path(args[1])); 	// specifies the Output directory /home/<anyname_or_netid>/output
	         
	         conf.setOutputKeyClass(Text.class);				// see output.collect in map --> this and the following line are reduce input pair
	      													//  so reduce should have Text-IntWritable pair as input

	         conf.setOutputValueClass(IntWritable.class);		// see output.collect
	
	         conf.setMapperClass(HW1_2.WordCountMap.class);					// specifies the mapper class
	 	     conf.setCombinerClass(HW1_2.WordCountReduce.class); 			// specifies the combiner
	 	     conf.setReducerClass(HW1_2.WordCountReduce.class); 			// specifies the reducer
	 	
	 	     conf.setInputFormat(TextInputFormat.class); 	// input type is text, key is line no., value is the line words
	 	     conf.setOutputFormat(TextOutputFormat.class); 	// output type is text
	 	
	 	    client.setConf(conf);
	 	
	 	    try {
	 	    	JobClient.runJob(conf); 
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
	 	     						// calling the JobClient.runJob to submit the job and monitor progress

	 	    Configuration config = new Configuration();
	 	     
	 	     //JobConf conf1= new JobConf(config);
	 	    
	 	    Job job = new Job(config, "Top10");
	 	     
	 	    job.setJarByClass(HW1_2.class);
	 	    	
	        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(args[1]));
	        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(args[2]));
	        
	        job.setOutputKeyClass(NullWritable.class);				// see output.collect in map --> this and the following line are reduce input pair
				//  so reduce should have Text-NullWritable pair as input

	        job.setOutputValueClass(Text.class);		// see output.collect
			
	        job.setMapperClass(HW1_2.Top10Map.class);					// specifies the mapper class
	        job.setCombinerClass(HW1_2.Top10Reduce.class); 			// specifies the combiner
	        job.setReducerClass(HW1_2.Top10Reduce.class); 			// specifies the reducer
			
	               
	        job.setNumReduceTasks(1);
			
			
		 	
	 	    try {
	 	    	job.waitForCompletion(true);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
	 	     
	 	     
	    }
	}
	
	
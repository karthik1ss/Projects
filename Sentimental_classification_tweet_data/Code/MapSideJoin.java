
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package mapsidejoin;
/**
 *
 * @author mvchinta
 */
import java.io.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


3.38.	/**
3.39.	 * @author Karthik
3.40.	 *
3.41.	 */


public class HadoopHDFSStore {

    public static class Map extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

        public HashMap<String, String> hmap;
        public static final String HDFS_List = "/HOST_COUNTRY.txt";

        @Override
        protected void setup(Context context) throws IOException, InterruptedException, FileNotFoundException {
            hmap = new HashMap<String, String>();
            BufferedReader br;// = null;
            String FileCacheName = new Path(HDFS_List).getName();
//				super.setup(context);
            Path[] localCacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
 
            if (localCacheFiles != null) 
            {
                for (Path cachePath : localCacheFiles) 
                {
                    if (cachePath.getName().equals(FileCacheName)) 
                    {
                        //Path localCacheFile = localCacheFiles[0];
                        br = new BufferedReader(new FileReader(cachePath.toString()));
                        String line = "";
                        while ((line = br.readLine()) != null) 
                        {
                            String[] arr = line.split("\t", -1);
                            if (arr.length >= 1) {
                                hmap.put(arr[0], arr[1]);
                            }
                        }
                        break;
                    }
                }

            }
        }

        //private String Ip,country;

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String splitarray[] = line.split(" |\t", -1);
            String Ip, country;
            // context.write(new Text(hmap.toString()), new Text("val"));
            if (splitarray.length >= 4) {
                Ip = splitarray[0].trim();
//                        if(hmap == null) context.write(new Text("map null"),new Text(""));
//                        if(hmap.isEmpty()) context.write(new Text("map empty"),new Text(""));


                Iterator entries = hmap.entrySet().iterator();
                while (entries.hasNext()) {
                    Entry entry = (Entry) entries.next();
                    String cacheip = entry.getKey().toString();

                    if (Ip.equals(cacheip))
                        
                      {
                        try {
                            context.write(new Text(cacheip), new Text(entry.getValue().toString() + " " + splitarray[1].trim() + " " + splitarray[2].trim() + " " + splitarray[3].trim() + " " + splitarray[4].trim()));

                            //output.collect(new Text(cacheip), new Text(entry.getValue().toString()+ " " +splitarray[1].trim() + " " + splitarray[2].trim() + " " + splitarray[3].trim() + " " + splitarray[4].trim()));
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MapSideJoin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }

        }
    }

  
    public static void main(String[] args) throws IOException, URISyntaxException {

        Configuration conf = new Configuration();

        org.apache.hadoop.mapreduce.Job job = new Job(conf, "Map side");

        job.setJarByClass(MapSideJoin.class);


        DistributedCache.addCacheFile(new Path(args[0] + "/" + "HOST_COUNTRY.txt").toUri(), job.getConfiguration());

        DistributedCache.addCacheFile(new java.net.URI("/HW2/input/HOST_COUNTRY.TXT"),conf);

        DistributedCache.createSymlink(conf);

        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.setInputPaths(job, new Path(args[0]));

        job.setMapperClass(Map.class);
        job.setNumReduceTasks(0);


        job.setReducerClass(Reduce.class);

        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        try {
            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}

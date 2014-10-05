
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapreduce.Job;z

public class ReduceSideJoin {

    public static class Map extends Mapper<LongWritable, Text, TextPair, TextPair> {

        //map method
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] LineData = line.split("\\s", -1);
            String tag = "0";
            if (LineData.length > 2) {
                tag = "1";
            }
            String keyvalue = LineData[0];
            String vals = new String();
            for (int i = 1; i < LineData.length; i++) {
                vals += LineData[i] + " ";
            }
            //if(LineData.length >3) vals += LineData[1]+" ";
            context.write((new TextPair(keyvalue, tag)), new TextPair(vals, tag));
        }
    }

    public static class JoinPartitioner extends Partitioner<TextPair, TextPair> {

        @Override
        public int getPartition(TextPair key, TextPair value, int numPartitions) {
            // TODO define custom partitioner to partition based on only the key part of TextPair
            return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
        }

        public void configure(JobConf conf) {
        }
    }

    public static class GroupComparator extends TextPair.FirstComparator {

        public int compare(TextPair one, TextPair two) {
            return one.getFirst().compareTo(two.getFirst());

        }
    }

    public static class SortComparator extends TextPair.FirstComparator {

        public int compare(TextPair one, TextPair two) {
            int cmp = one.getFirst().compareTo(two.getFirst());
            if (cmp != 0) {
                return cmp;
            }
            return one.getSecond().compareTo(two.getSecond());
        }
    }

    public static class Reduce extends Reducer<TextPair, TextPair, Text, Text> {

        public void reduce(TextPair key, Iterable<TextPair> values,
                Context context)
                throws IOException, InterruptedException {
            ArrayList<String> T1 = new ArrayList<String>();
            Text tag = key.getSecond();
            String tag1 = tag.toString();
//            TextPair value = null;
            for (TextPair value : values) {
//                value = values.next();
                if (value.getSecond().toString().equals(tag1)) {
                    T1.add(value.getFirst().toString());
                } else {
                    for (String val : T1) {
                        context.write(key.getFirst(),
                                new Text(val.toString() + "\t"
                                + value.getFirst().toString()));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //JobConf conf = new JobConf(ReduceSideJoin.class); // create jobconf object
        //conf. setJobName("ReduceSideJoin");
        Configuration config = new Configuration();
        Job job = new Job(config);

        job.setJarByClass(ReduceSideJoin.class);



        job.setMapOutputKeyClass(TextPair.class);
        job.setMapOutputValueClass(TextPair.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // job.set

        job.setMapperClass(Map.class);
        job.setPartitionerClass(JoinPartitioner.class);
        job.setGroupingComparatorClass(TextPair.FirstComparator.class);
        job.setReducerClass(Reduce.class);

        //conf.setMapperClass(Map.class);




        //conf.setPartitionerClass( JoinPartitioner.class);
        //job.setOutputKeyComparatorClass(GroupComparator.class);
        //conf.setOutputValueGroupingComparator(SortComparator.class);
        job.setReducerClass(Reduce.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        ////job.setInputFormat(TextInputFormat.class);
        //conf.setOutputFormat(TextOutputFormat.class);

        //FileInputFormat.setInputPaths(job, new Path(args[0]));
        //FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //JobClient.runJob(conf);
        job.waitForCompletion(true);




    }
}

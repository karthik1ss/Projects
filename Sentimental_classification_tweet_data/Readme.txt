This file has commands of how to compile and run the assignment problems


Reduce Side Join
Compile:

javac -classpath /usr/local/hadoop-1.0.4/hadoop-core-1.0.4.jar -d Hw2 TextPair.java ReduceSideJoin.java
jar -cvf ReduceSideJoin.jar -C Hw2 /.

Run:
hadoop jar ReduceSideJoin.jar ReduceSideJoin  /HW2/input/ /home/vxc121530/output

output:
hadoop fs -cat /home/vxc121530/output/part-m-00000

Map Side join

Compile
javac -classpath /usr/local/hadoop-1.0.4/hadoop-core-1.0.4.jar -d HW1 MapSideJoin.java
jar -cvf MapSideJoin.jar -C HW1 /.

Run:
hadoop jar MapSideJoin.jar MapSideJoin  /HW2/input/ /home/vxc121530/output

output:
hadoop fs -cat /home/vxc121530/output/part-m-00000



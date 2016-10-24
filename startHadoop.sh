while read slave
do
	echo installing hadoop on "$slave"
	ssh jp360641@$slave 'bash -s' < installHadoop.sh

done < slaves
export _JAVA_OPTIONS=-Xmx6000m
/tmp/hadoop/sbin/stop-dfs.sh
/tmp/hadoop/bin/hdfs namenode -format
/tmp/hadoop/sbin/start-dfs.sh
/tmp/hadoop/bin/hdfs dfs -mkdir /user
/tmp/hadoop/bin/hdfs dfs -mkdir /user/jp360641
/tmp/hadoop/bin/hdfs dfs -put masters
/tmp/hadoop/bin/hdfs dfs -ls

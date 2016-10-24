for f in ` find . -name '*.in'`
do
  n=$(basename "$f")
  echo $n
  /tmp/hadoop/bin/hdfs dfs -rm $n
  /tmp/hadoop/bin/hdfs dfs -put $n
  /tmp/hadoop/bin/hadoop jar main.jar -cl $n 4 -1 $n.shingles
  /tmp/hadoop/bin/hadoop jar main.jar -b $n.shingles  $n 4 -1 $n.matrix
  /tmp/hadoop/bin/hadoop jar main.jar -m $n.matrix $n.minhash
  /tmp/hadoop/bin/hadoop jar main.jar -lsh $n.minhash $n.candid
  rm $n.candid
  /tmp/hadoop/bin/hdfs dfs -get $n.candid
done

for f in ` find . -name '*.in'`
do
  n=$(basename "$f")
  echo $n
  /tmp/hadoop/bin/hdfs dfs -rm $n
  /tmp/hadoop/bin/hdfs dfs -put $n
  /tmp/hadoop/bin/hadoop jar main.jar -cl $n 10 4 $n.shingles
  /tmp/hadoop/bin/hadoop jar main.jar -b $n.shingles  $n 10 4 $n.matrix
  /tmp/hadoop/bin/hadoop jar main.jar -m $n.matrix $n.minhash
  /tmp/hadoop/bin/hadoop jar main.jar -lsh $n.minhash $n.hashed.candid
  rm $n.hashed.candid
  /tmp/hadoop/bin/hdfs dfs -get $n.hashed.candid
done

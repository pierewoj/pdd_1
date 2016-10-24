RUN="java -cp \"/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1\" Cw1 "
echo $RUN
for f in ` find . -name '*.in'`
do
  n=$(basename "$f")
  echo $n " GENERATING SHINGLES LIST"
  /usr/bin/time -v java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -cl $n 4 -1 tmp/$n.shingles

  echo $n " GENERATING MATRIX"
  /usr/bin/time -v java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1  -b tmp/$n.shingles $n 4 -1 tmp/$n.matrix

  echo $n "GENERATING SIGNATURES"
  /usr/bin/time -v java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1  -m tmp/$n.matrix tmp/$n.minhash

  echo $n "GENERATING CANDIDATES"
  /usr/bin/time -v java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1  -lsh tmp/$n.minhash tmp/$n.candid
done

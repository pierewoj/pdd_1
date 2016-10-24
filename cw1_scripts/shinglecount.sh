lens=(2 3 4 5 6 7 8 9 10)
hashlens=(-1 2 3 4)

for file in `find ../pdd2_dane/books -name '*.csv' -or -name '*.txt' -or -name '*.filtered_lower'`
do
  echo 'generating input for' $file 
   java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -p $file $file.in
done

for file in `find ../pdd2_dane/news -name '*.csv' -or -name '*.txt' -or -name '*.filtered_lower'`
do
  echo 'generating input for' $file 
   java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -p $file $file.in
done

for file in `find ../pdd2_dane -name '*.csv.in' -or -name '*.txt.in' -or -name '*.filtered_lower.in'`
do
  echo $file
  for len in "${lens[@]}"
  do
    for hashlen in "${hashlens[@]}"
    do
      echo $len $hashlen `java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -cl $file $len $hashlen null`
    done 
  done
done

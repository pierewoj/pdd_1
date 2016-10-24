java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -pl ../pdd2_dane/tweets/tweets.csv.filtered_lower tweets.in
java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -p ../pdd2_dane/news/*.filtered_lower news.in
java -cp "/tmp/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar:../cw1" Cw1 -p ../pdd2_dane/books/*.filtered_lower books.in


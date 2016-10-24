rm -rf /tmp/hadoop*
mkdir /tmp/hadoop
tar -xf ~/Download/hadoop-2.7.2.tar.gz -C /tmp/hadoop
mv /tmp/hadoop/hadoop-2.7.2/* /tmp/hadoop
rmdir /tmp/hadoop/hadoop-2.7.2
export HADOOP_PREFIX=/tmp/hadoop/
sed -i -e "s|^export JAVA_HOME=\${JAVA_HOME}|export JAVA_HOME=/usr/lib64/jvm/java|g" ${HADOOP_PREFIX}/etc/hadoop/hadoop-env.sh
cd $HADOOP_PREFIX

cp ~/Dokumenty/pdd/masters $HADOOP_PREFIX/etc/hadoop/masters
cp ~/Dokumenty/pdd/slaves $HADOOP_PREFIX/etc/hadoop/slaves

cat <<EOF > ${HADOOP_PREFIX}/etc/hadoop/core-site.xml
<configuration>
<property>
<name>fs.defaultFS</name>
<value>hdfs://`cat ~/Dokumenty/pdd/masters`:9000</value>
</property>
</configuration>
EOF

cat <<EOF > ${HADOOP_PREFIX}/etc/hadoop/hdfs-site.xml
<configuration>
<property>
<name>dfs.replication</name>
<value>1</value>
</property>
</configuration>
EOF


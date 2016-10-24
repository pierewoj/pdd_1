for f in ` find ../pdd2_dane -name '*.csv' -or -name '*.txt' `
do
	sed 's/[^[:alpha:]\r\t]//g' < $f > $f.filtered
	grep -v -e '^[[:space:]]*$' $f.filtered > $f.filtered_tmp
	mv $f.filtered_tmp $f.filtered
	tr '[:upper:]' '[:lower:]' < $f.filtered > $f.filtered_lower
done

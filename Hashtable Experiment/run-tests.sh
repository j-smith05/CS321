#!/bin/bash

echo
echo "Compiling the source code"
echo
javac *.java

if [ ! -f HashtableExperiment.class ]; then
	echo
	echo "HashtableExperiment.class not found! Not able to test!!"
	echo
	exit 1
fi

echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Running test for word-list for varying load factors"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo

debugLevel=1
loads="0.5 0.6 0.7 0.8 0.9 0.95 0.99"

for load in $loads
do
	echo "Running java HashtableExperiment dataSource = 3 loadFactor = $load"

	rm -f linear-dump.txt double-dump.txt

	java HashtableExperiment 3 $load $debugLevel > /dev/null

	echo

	if [ ! -f linear-dump.txt ]; then
		echo "==> Test FAILED for linear probing load = $load!!"
		echo "       linear-dump.txt not found"
	else
		diff -w -B linear-dump.txt test-cases/word-list-$load-linear-dump.txt > diff-linear-$load.out
		if [ $? -eq 0 ]; then
			echo "Test PASSED for linear probing and load = $load"
			rm -f diff-linear-$load.out
		else
			echo "==> Test FAILED for linear probing load = $load!!"
			echo "       Check the file diff-linear-$load.out for differences"
		fi
	fi

	if [ ! -f double-dump.txt ]; then
		echo "==> Test FAILED for double probing load = $load!!"
		echo "       double-dump.txt not found"
	else
		diff -w -B double-dump.txt test-cases/word-list-$load-double-dump.txt > diff-double-$load.out
		if [ $? -eq 0 ]; then
			echo "Test PASSED for double probing and load = $load"
			rm -f diff-double-$load.out
		else
			echo "==> Test FAILED for double probing load = $load!!"
			echo "       Check the file diff-double-$load.out for differences"
		fi
	fi

	echo
done
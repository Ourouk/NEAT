#!/usr/bin/env bash

BASE_DIR="Images"

if [ ! -d $BASE_DIR ]; then
	echo "No $BASE_DIR directory"
	exit 1
fi

find $BASE_DIR -type f -name *.dot | while read -r file; do
	dot -Tsvg $file -o "${file%.dot}.svg"
done

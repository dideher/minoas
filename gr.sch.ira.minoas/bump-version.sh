#!/bin/sh
REPLACE_STRING="s/<version>development-SNAPSHOT<\/version>/<version>${1}<\/version>/g"
echo ${REPLACE_STRING}
find . -name "pom.xml" -exec sed -i ${REPLACE_STRING} '{}' \;


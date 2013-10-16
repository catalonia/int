#!/bin/sh


####################################
# Main Program 

echo "Main program - current directory `pwd`"

numArg=$#
echo "numArg=$# arguments=$*"
case "$numArg" in
   2)
	FILENAMEWITHDIRPATH=$1
    SCRIPT_ROOT=$2

   ;;
   *)
      echo "ERROR: Wrong number of parameters $numArg."
      exit 1
   ;;
esac

echo "SCRIPT_ROOT=${SCRIPT_ROOT}"

#include the common environment property files
if [ "$SCRIPT_ROOT" != "" ]; then
lastChar=`echo ${SCRIPT_ROOT} | sed -e "s/^.*\(.\)$/\1/"`
	if [ "$lastChar" != "/" ]; then
	SCRIPT_ROOT=${SCRIPT_ROOT}/
	fi
fi


. ${SCRIPT_ROOT}./scripts/commonEnvProps.properties


#mysql -h "server-name" -u "root" -p "XXXXXXXX" "database-name" < "filename.sql"
mysql -h "localhost" "--user=root" "--password=" "Delta3_3May2013" < $FILENAMEWITHDIRPATH

echo "finish - SUCCESS"
exit 0

#!/bin/sh

SCRIPTNAME="Algo2UserRestaurantOffline"

####################################
# Main Program 

echo "Main program - current directory `pwd`"

ENV_TYPE=dev
P6SPY_HOME=$SCRIPT_ROOT/$ENV_TYPE/p6spy_config

numArg=$#
echo "numArg=$# arguments=$*"

echo "SCRIPT_ROOT=${SCRIPT_ROOT}"

#include the common environment property files
if [ "$SCRIPT_ROOT" != "" ]; then
lastChar=`echo ${SCRIPT_ROOT} | sed -e "s/^.*\(.\)$/\1/"`
	if [ "$lastChar" != "/" ]; then
	SCRIPT_ROOT=${SCRIPT_ROOT}/
	fi
fi

#. ${SCRIPT_ROOT}./scripts/commonEnvProps.properties


for i in ./lib/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

for i in ./libExtras/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

for i in $TOMCAT_HOME/lib/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

for i in $P6SPY_HOME/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done

CLASSPATH=`echo $CLASSPATH | cut -c2-`
CLASSPATH=$SCRIPT_ROOT/$ENV_TYPE/config/:$CLASSPATH

echo $CLASSPATH

#call java class
MAINCLASS=com.tastesync.algo.main.UserRestaurantOfflineAlgo2CalcMain
INPUTS_MAINCLASS=

#TODO if already running, kill. stop/start

java -DSCRIPT_ROOT="$SCRIPT_ROOT" -DENV_TYPE="$ENV_TYPE" -DCONFIG_ROOT="$SCRIPT_ROOT/config/dev" -Dlog4j.debug -Dp6.home=$P6SPY_HOME -cp $CLASSPATH $MAINCLASS $INPUTS_MAINCLASS >& $SCRIPTNAME.start.log &
#java -DSCRIPT_ROOT="$SCRIPT_ROOT" -DENV_TYPE="$ENV_TYPE" -DCONFIG_ROOT="$SCRIPT_ROOT/$ENV_TYPE" -Dlog4j.debug -Dp6.home=$P6SPY_HOME -cp $CLASSPATH $MAINCLASS $INPUTS_MAINCLASS

echo "${SCRIPTNAME} date FINISH numArg=$# arguments=$*"


echo "finish - SUCCESS"
exit 0

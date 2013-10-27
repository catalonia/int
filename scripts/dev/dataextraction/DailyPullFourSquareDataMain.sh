#!/bin/sh
SCRIPTNAME=DailyPullFourSquareDataMain

####################################
# Main Program 
SCRIPTNAME=DataExtractionExecMain.sh
echo "Main program - current directory `pwd`"

ENV_TYPE=dev
P6SPY_HOME=$SCRIPT_ROOT/$ENV_TYPE/p6spy_config1

SCRIPT_ROOT=$SCRIPT_ROOT
echo "SCRIPT_ROOT $SCRIPT_ROOT"
numArg=$#
echo "numArg=$# arguments=$*"


#include the common environment property files
if [ "$SCRIPT_ROOT" != "" ]; then
lastChar=`echo ${SCRIPT_ROOT} | sed -e "s/^.*\(.\)$/\1/"`
	if [ "$lastChar" != "/" ]; then
	SCRIPT_ROOT=${SCRIPT_ROOT}/
	fi
fi

echo "SCRIPT_ROOT $SCRIPT_ROOT" 

#. ${SCRIPT_ROOT}./commonEnvProps.properties

#set classpath
CLASSPATH=""
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
CLASSPATH=$SCRIPT_ROOT/dev/config/:$CLASSPATH

echo $CLASSPATH

#call java class
MAINCLASS=com.tastesync.dataextraction.main.DailyPullFourSquareDataMain
INPUTS_MAINCLASS=

#TODO if already running, kill. stop/start

#java -DSCRIPT_ROOT="$SCRIPT_ROOT" -DENV_TYPE="$ENV_TYPE" -DCONFIG_ROOT="$SCRIPT_ROOT/config/dev" -Dlog4j.debug -Dp6.home=$P6SPY_HOME -cp $CLASSPATH $MAINCLASS $INPUTS_MAINCLASS >& $SCRIPTNAME.startlog.log &
java -DSCRIPT_ROOT="$SCRIPT_ROOT" -DENV_TYPE="$ENV_TYPE" -DCONFIG_ROOT="$SCRIPT_ROOT/$ENV_TYPE" -Dlog4j.debug -Dp6.home=$P6SPY_HOME -cp $CLASSPATH $MAINCLASS $INPUTS_MAINCLASS

echo "${SCRIPTNAME} date FINISH numArg=$# arguments=$*"


echo "finish - SUCCESS"
exit 0

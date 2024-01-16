#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	clear
	echo "*** Make sure Eclipse is NOT running ***"
	cd $PDDA_HOME
	echo "Sonar Logs are in $SONAR_LOGS"
	rm -rf $SONAR_LOGS/*.log
	echo "Starting Sonar..."
	sonar start
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

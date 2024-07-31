#!/bin/zsh
 
if [[ -n $TOMEE_HOME ]]; then
	clear
	cd $TOMEE_HOME/bin
	echo "Starting TomEE..."
	catalina.sh start
else
	echo "TOMEE_HOME is not set, please run 'source pdda_env.sh' first"
fi

#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	if [[ -n $TOMEE_HOME ]]; then
		cp $PDDA_HOME/target/PDDA-1.0.war $TOMEE_HOME/libexec/webapps/.
		cd $TOMEE_HOME/libexec/webapps
		pwd
		ls -al *.war
	else
		echo "TOMEE_HOME is not set, please run 'source pdda_env.sh' first"
	fi	
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

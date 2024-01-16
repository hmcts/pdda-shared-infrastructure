#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	echo "Stopping Sonar..."
	sonar stop
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

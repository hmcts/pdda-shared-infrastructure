#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	echo "Building Maven project - Bring down all latest versions"
	cd $PDDA_HOME
	rm -rf ~/.m2
	mvn clean validate
	mvn clean install
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

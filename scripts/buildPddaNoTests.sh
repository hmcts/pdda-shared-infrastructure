#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	echo "Building Maven project *** SKIPPING ALL TESTS ***"
	cd $PDDA_HOME
	mvn clean install -Dmaven.test.skip=true
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	cd $PDDA_HOME
	clear
	Echo Starting Sonar Analysis...
	mvn clean verify sonar:sonar \
	  -Dsonar.projectKey=pddav2 \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.login=sqp_db78686ddc0ca3079070836729f93c904124cb7b
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

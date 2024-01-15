#!/bin/zsh

if [[ -n $PDDA_HOME ]]; then
	cd $PDDA_HOME/database/test-data
	clear
	echo \\q - quit
	echo \\! pwd - Show current directory
	echo \\cd {dir} - Change Directory
	echo \\i {filename} - Run file
	psql -d pdda -U postgres
else
	echo "PDDA_HOME is not set, please run 'source pdda_env.sh' first"
fi

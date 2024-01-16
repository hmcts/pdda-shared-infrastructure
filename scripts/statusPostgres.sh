#!/bin/zsh

if [[ -n $PGDATA ]]; then
	pg_ctl status
else
	echo "PGDATA is not set, please run 'source pdda_env.sh' first"
fi

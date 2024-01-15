#!/bin/zsh

if [[ -n $PGDATA ]]; then
	pg_ctl -D $PGDATA start
else
	echo "PGDATA is not set, please run 'source pdda_env.sh' first"
fi

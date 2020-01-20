#!/bin/bash

set -e

HOST=`hostname -s`
	
export CATALINA_OPTS="${CATALINA_OPTS} -DMY_POD_NAME=$HOST"

exec "$@"
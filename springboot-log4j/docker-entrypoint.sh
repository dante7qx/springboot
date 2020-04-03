#!/bin/bash

set -e

## export CATALINA_OPTS="${CATALINA_OPTS} -DMY_POD_NAME=$MY_POD_NAME"
export JAVA_OPTS="${JAVA_OPTS} -DMY_POD_NAME=$MY_POD_NAME"

exec "$@"
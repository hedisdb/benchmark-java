#!/bin/bash

THREADS=$1
CONNECTIONS=$2

./wrk/wrk -s scripts/redis_invalidation.lua -t $THREADS -c $CONNECTIONS http://localhost:8090/redis

#!/bin/bash

THREADS=$1
CONNECTIONS=$2

./wrk/wrk -s scripts/hedis_no_invalidation.lua -t $THREADS -c $CONNECTIONS http://localhost:8090/hedis

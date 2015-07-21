#!/bin/bash

THREADS=$1
CONNECTIONS=$2
DURATION=$3

./wrk/wrk -s scripts/hedis_hbase_invalidation.lua -t $THREADS -c $CONNECTIONS -d $DURATION http://localhost:8090/hedis

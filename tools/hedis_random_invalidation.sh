#!/bin/bash

for ((i=1; i<$1; i=i+1))
do
  rand=$(($RANDOM % 2))

  if (( $rand == 0 ))
    curl -s -X POST -d "mysqltest://!select * from user" http://localhost:8090/hedis > /dev/null
  then
    curl -s -X POST -d "mysqltest://select * from user" http://localhost:8090/hedis > /dev/null
  fi
done

#!/bin/bash

for ((i=1; i<500; i=i+1))
do
  curl -s -X POST -d "mysqltest://select * from user" http://localhost:8090/hedis > /dev/null
done

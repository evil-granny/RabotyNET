#!/bin/sh

status_code=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/healthCheck)

if [[ "$status_code" -ne 200 ]] ; then
    exit -1
else
    exit -1
fi

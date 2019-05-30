#!/bin/sh

status_code=$(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/RabotyNET/healthCheck)

if [ "$status_code" -ne 200 ] ; then
    exit 1
else
    exit 0
fi

#!/bin/sh

status_code=$(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/healthCheck1)

if [ "$status_code" -ne 200 ] ; then
    exit 1
else
    exit 0
fi

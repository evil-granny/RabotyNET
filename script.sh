#!/bin/sh

#status_code=$(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/RabotyNE)
#echo "$status_code"
if [[ $(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/RabotyNET/companies) -ne 200 ]] ; then
echo "not 200"
    exit 1
else
echo "200"
    exit 0
fi

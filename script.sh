#!/bin/sh

echo "Wait 30 seconds for Jenkins to boot"
sleep 30

STATUS_CODE=$(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/RabotyNET/healthCheck)

echo "Curling against the Jenkins server"
echo "Should expect a 200 within 15 seconds"

if [[ ${STATUS_CODE} -ne 200 ]] ; then
    echo "Jenkins has come up and ready to use"
    echo "Returned: $STATUS_CODE"
    exit 1
fi

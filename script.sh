#!/bin/sh

set -e
set -x

echo "Wait 60 seconds for Jenkins to boot"
sleep 60

STATUS_CODE=$(curl --write-out '%{http_code}' --silent --output /dev/null localhost:8080/RabotyNET/healthCheck1)

echo "Curling against the Jenkins server"
echo "Should expect a 200 within 15 seconds"

if [[ "$STATUS_CODE" == "200" ]] ; then
    echo "Jenkins has come up and ready to use"
    echo "Returned: $STATUS_CODE"
    exit 0
else
    echo "Returned: $STATUS_CODE"
    echo "Jenkins did not return a correct status code."
    exit 1
fi

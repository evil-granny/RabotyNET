#!/bin/sh

echo "Wait 30 seconds for Jenkins to boot"
sleep 30

STATUS_CODE=$(curl -s -o /dev/null -I -w "%{http_code}" localhost:8080/RabotyNET/healthCheck)

echo "Curling against the Jenkins server"
echo "Should expect a 200 within 15 seconds"

# shellcheck disable=SC2039
if [[ ${STATUS_CODE} -ne 200 ]] ; then
    echo "Jenkins has come up and ready to use"
    echo "Returned: $STATUS_CODE"
    exit 1
fi

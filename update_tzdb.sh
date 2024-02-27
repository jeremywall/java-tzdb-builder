#!/bin/bash

if [ $# -eq 0 ]; then
  echo "TZDB version argument missing"
  exit
fi
if [ -z "$1" ]; then
  echo "TZDB version argument empty"
  exit
fi

JAVA_EXE_PATH="`readlink -f /usr/bin/java`"
JAVA_JRE_BIN_DIR_PATH="`dirname ${JAVA_EXE_PATH}`"
JAVA_JRE_LIB_DIR_PATH="`dirname ${JAVA_JRE_BIN_DIR_PATH}`/lib"
JAVA_TZDB_PATH="${JAVA_JRE_LIB_DIR_PATH}/tzdb.dat"

if [ ! -f "$JAVA_TZDB_PATH" ]; then
  echo "Unable to locate existing TZDB ${JAVA_TZDB_PATH}"
  exit
fi

TZDB_OLD_VERSION=`head -c 16 ${JAVA_TZDB_PATH} | tail -c 5`

echo "Located existing TZDB ${TZDB_OLD_VERSION} ${JAVA_TZDB_PATH}"

TZDB_NEW_VERSION="${1}"
TZDB_DOWNLOAD_URL="https://raw.githubusercontent.com/jeremywall/java-tzdb-builder/main/releases/${TZDB_NEW_VERSION}/tzdb${TZDB_NEW_VERSION}.dat"

HTTP_RESPONSE_CODE=`curl -O -f -w "%{http_code}" "${TZDB_DOWNLOAD_URL}"`
#HTTP_RESPONSE_CODE=`curl -O -f -w "%{http_code}" "${TZDB_DOWNLOAD_URL}" | head -n 1 | cut -d $' ' -f2`

echo "Download TZDB ${TZDB_NEW_VERSION} HTTP Response ${HTTP_RESPONSE_CODE}"

if [ "200" != "${HTTP_RESPONSE_CODE}" ]; then
  echo "Failed to download TZDB ${TZDB_NEW_VERSION} from ${TZDB_DOWNLOAD_URL}"
  exit
fi

cp "${JAVA_TZDB_PATH}" "${JAVA_TZDB_PATH}.${TZDB_OLD_VERSION}.bak"
echo "Backed up existing TZDB to ${JAVA_TZDB_PATH}.${TZDB_OLD_VERSION}.bak"

cp "tzdb${TZDB_NEW_VERSION}.dat" "${JAVA_TZDB_PATH}"
echo "Copied new TZDB to ${JAVA_TZDB_PATH}"

chmod 644 "${JAVA_TZDB_PATH}"
echo "Setting TZDB file permissions to 644"

echo "Now you need to restart all Java applications on this server"

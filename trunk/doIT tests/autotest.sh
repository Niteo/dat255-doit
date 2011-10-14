#!/bin/bash

#This script will fetch the most recent source code from Google Code, build it, install it on an android device and either run tests or run coverage.
if [[ "$1" == "--help" || "$1" == "-h" ]]; then
	echo "This script fetches the latest source and runs the tests. Use the --coverage (or -c) parameter to run coverage instead of tests!"
	exit 0
fi
if [[ "$1" == "--coverage" || "$1" == "-c" ]]; then
	echo 'Running "ant coverage" after fetching newest source and updating project with android SDK-tool'
else
	echo 'Running "ant run-tests" after fetching newest source and updating project with android SDK-tool'
fi

svn checkout http://dat255-doit.googlecode.com/svn/trunk/ tmpTests
if [ $? -ne 0 ] ;
then
  echo "Failed checkout!"
  exit $?
fi
cd tmpTests
android update project -p ./doIT
if [ $? -ne 0 ] ;
then
  echo 'Failed "android update project".'
  exit $?
fi
android update test-project -m ../doIT -p ./doIT\ tests/
if [ $? -ne 0 ] ;
then
  echo 'Failed "android update test-project".'
  exit $?
fi
cd doIT\ tests
if [[ "$1" == "--coverage" || "$1" == "-c" ]]; then
	ant coverage
else
	ant run-tests
fi
if [ $? -ne 0 ] ;
then
  echo '"Failed ant coverage" or "ant run-tests"!'
  exit $?
fi

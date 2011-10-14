#!/bin/bash

#This script will fetch the most recent source code from Google Code, build it and attempt to install it on an Android device.

SDKDIR="/home/phelerox/bin/android-sdk-linux_x86"
#rm -rf dat255-doit-read-only
svn checkout http://dat255-doit.googlecode.com/svn/trunk/ dat255-doit-read-only
cd dat255-doit-read-only/doIT

echo "sdk.dir="$SDKDIR > local.properties
ant debug
$SDKDIR/platform-tools/adb install -r bin/MainActivity-debug.apk
#TODO: Code for running tests
$SDKDIR/platform-tools/adb shell am instrument -w -e package se.chalmers.doit se.chalmers.doit/android.test.InstrumentationTestRunner

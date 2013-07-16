#!/bin/bash
set -e
mypath="bin:cfg:lib/commons-codec-1.6.jar:lib/commons-collections-3.2.1.jar:lib/commons-exec-1.1.jar:lib/commons-io-2.2.jar:lib/commons-jxpath-1.3.jar:lib/commons-lang3-3.1.jar:lib/commons-logging-1.1.1.jar:lib/cssparser-0.9.8.jar:lib/guava-14.0.jar:lib/hamcrest-core-1.3.jar:lib/httpclient-4.2.1.jar:lib/httpcore-4.2.1.jar:lib/json-20080701.jar:lib/junit-dep-4.11.jar:lib/log4j-1.2.17.jar:lib/netty-3.6.6.Final.jar:lib/phantomjsdriver-1.0.1.jar:lib/selenium-java-2.31.0.jar:lib/temp.txt:lib/testng-6.8.jar"

runner="org.junit.runner.JUnitCore"

java -cp $mypath $runner com.contatta.smoke.QuickActions 
java -cp $mypath $runner com.contatta.smoke.StatusUpdate
java -cp $mypath $runner com.contatta.smoke.CompanyDetail
java -cp $mypath $runner com.contatta.smoke.ContactDetail
java -cp $mypath $runner com.contatta.smoke.ComposeEmail
java -cp $mypath $runner com.contatta.smoke.CompanyEdit
java -cp $mypath $runner com.contatta.smoke.ContactEdit
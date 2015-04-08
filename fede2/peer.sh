#!/bin/bash

guice="libs/guice/javax.inject.jar:libs/guice/aopalliance-1.0.jar:libs/guice/guice-3.0.jar:libs/guice/guice-assistedinject-3.0.jar"
rmi="-Djava.rmi.rver.codebase=file:bin/"
java -classpath ${guice}:bin it.unibo.cs.sd.poker.main.Main

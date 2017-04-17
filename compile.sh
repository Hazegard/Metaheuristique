#! /bin/bash
if [ -e src/City.class ]
then
rm src/City.class
fi
if [ -e src/Ants.class ]
then
rm src/Ants.class
fi
if [ -e src/Aco.class ]
then
rm src/Aco.class
fi
javac src/City.java src/Ants.java src/Aco.java

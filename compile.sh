#! /bin/bash
if ! [ -d bin/ ]
then
mkdir bin
fi
if [ -e bin/City.class ]
then
rm bin/City.class
fi
if [ -e bin/Ants.class ]
then
rm bin/Ants.class
fi
if [ -e bin/Aco.class ]
then
rm bin/Aco.class
fi
javac -d bin/ src/City.java src/Ants.java src/Aco.java

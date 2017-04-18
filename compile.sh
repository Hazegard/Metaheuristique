#! /bin/bash
if ! [ -d bin/ ]
then
mkdir bin
fi
if [ -e bin/City.class ]
then
rm bin/City.class
fi
if [ -e bin/Ordre.class ]
then
rm bin/Ordre.class
fi
if [ -e bin/SearchTabu.class ]
then
rm bin/SearchTabu.class
fi
javac -d bin/ src/City.java src/Ordre.java src/SearchTabu.java

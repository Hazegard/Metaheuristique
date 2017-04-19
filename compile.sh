#! /bin/bash
if [ -z "$(which javac)" ]
then
echo "Javac n'a pas été trouvé dans PATH"
exit
fi

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

if [ -e bin/Ordre.class ]
then
rm bin/Ordre.class
fi
if [ -e bin/SearchTabu.class ]
then
rm bin/SearchTabu.class
fi
javac -d bin/ src/City.java src/Ordre.java src/SearchTabu.java


#!/bin/bash
if [ -z "$(which java)" ]
then
        echo "Java n'a pas ete trouve dans PATH"
        exit
fi

java -cp bin/. SearchTabu

@echo off
if not exist bin\ (
mkdir bin\
)
if exist bin\Ants.class (
	del bin\Ants.class
)
if exist bin\Aco.class (
	del bin\Aco.class
)
if exist bin\City.class (
	del bin\City.class
)
javac -d bin\ src\City.java src\Ants.java src\Aco.java

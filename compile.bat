@echo off
where /q javac
if errorlevel 1 (
	echo javac n'a pas ete trouve dans PATH
	pause
	exit /B
) 
if not exist bin\ (
mkdir bin\
)
if exist bin\Ants.class (
	del bin\Ants.class
)
if exist bin\Aco.class (
	del bin\Aco.class
)
if exist bin\Ordre.class (
	del bin\Ordre.class
)
if exist bin\SearchTabu.class (
	del bin\SearchTabu.class
)
if exist bin\City.class (
	del bin\City.class
)

javac -d bin\ src\City.java src\Ants.java src\Aco.java
javac -d bin\ src\City.java src\Ordre.java src\SearchTabu.java

echo Compilation effectuee avec succes
pause

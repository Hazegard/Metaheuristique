@echo off
if not exist bin\ (
	mkdir bin\
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
javac -d bin\ src\City.java src\Ordre.java src\SearchTabu.java
pause

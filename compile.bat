@echo off
if exist src\Ants.class (
	del src\Ants.class
)
if exist src\Aco.class (
	del src\Aco.class
)
if exist src\City.class (
	del src\City.class
)
javac src\City.java src src\Ants.java src\Aco.java
pause
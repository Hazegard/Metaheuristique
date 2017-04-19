@echo off
where /q java
if errorlevel 1 (
        echo java n'a pas ete trouve dans PATH
        pause
        exit /B
)


java -cp bin\. SearchTabu
pause

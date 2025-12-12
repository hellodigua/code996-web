@echo off
REM Code996 Backend Start Script for Windows

echo ======================================
echo Code996 Backend Service
echo ======================================
echo.

REM Check Java
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

REM Check Maven
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo [OK] Java: OK
echo [OK] Maven: OK
echo.

REM Navigate to backend directory
cd /d "%~dp0"

REM Run backend
echo Starting Code996 Backend...
echo.
mvn spring-boot:run

pause

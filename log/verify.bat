@echo off
REM Code996 Module Verification Script

echo ======================================
echo Code996 Module Verification
echo ======================================
echo.

setlocal enabledelayedexpansion
set total_checks=0
set passed_checks=0

REM Root files
echo [Root files]
call :check_file "README.md"
call :check_file "QUICKSTART.md"
call :check_file "EXAMPLES.md"
call :check_file "PROJECT_SUMMARY.md"
echo.

REM Backend directory
echo [Backend directory]
call :check_dir "backend"
call :check_file "backend\pom.xml"
call :check_file "backend\README.md"
call :check_file "backend\start.sh"
call :check_file "backend\start.bat"
call :check_file "backend\.gitignore"
echo.

REM Backend Java source
echo [Backend Java sources]
call :check_dir "backend\src\main\java\com\code996"
call :check_file "backend\src\main\java\com\code996\Code996Application.java"
call :check_file "backend\src\main\java\com\code996\controller\AnalysisController.java"
call :check_file "backend\src\main\java\com\code996\service\GitAnalysisService.java"
call :check_file "backend\src\main\java\com\code996\model\AnalysisRequest.java"
call :check_file "backend\src\main\java\com\code996\model\AnalysisResult.java"
call :check_file "backend\src\main\java\com\code996\model\ApiResponse.java"
call :check_file "backend\src\main\java\com\code996\util\GitHelper.java"
call :check_file "backend\src\main\java\com\code996\config\CleanupScheduler.java"
echo.

REM Backend resources
echo [Backend resources]
call :check_file "backend\src\main\resources\application.yml"
echo.

REM Frontend directory
echo [Frontend directory]
call :check_dir "frontend"
call :check_file "frontend\api.ts"
call :check_file "frontend\OnlineAnalysis.vue"
call :check_file "frontend\package.json"
call :check_file "frontend\README.md"
echo.

REM Summary
echo ======================================
echo Verification Summary
echo ======================================
echo Total checks: %total_checks%
echo Passed: %passed_checks%
set /a failed_checks=%total_checks%-%passed_checks%
echo Failed: %failed_checks%
echo.

if %passed_checks% equ %total_checks% (
    echo [OK] All files are present!
    echo.
    echo Ready to use!
    echo.
    echo To start the backend:
    echo   cd backend
    echo   mvn spring-boot:run
    exit /b 0
) else (
    echo [ERROR] Some files are missing!
    echo Please check the missing files above.
    exit /b 1
)

:check_file
set /a total_checks+=1
if exist "%~1" (
    echo [OK] %~1
    set /a passed_checks+=1
) else (
    echo [MISSING] %~1
)
goto :eof

:check_dir
set /a total_checks+=1
if exist "%~1\" (
    echo [OK] %~1\
    set /a passed_checks+=1
) else (
    echo [MISSING] %~1\
)
goto :eof

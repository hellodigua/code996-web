#!/bin/bash

# Code996 Module Verification Script

echo "======================================"
echo "Code996 Module Verification"
echo "======================================"
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counters
total_checks=0
passed_checks=0

check_file() {
    total_checks=$((total_checks + 1))
    if [ -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
        passed_checks=$((passed_checks + 1))
    else
        echo -e "${RED}✗${NC} $1 (MISSING)"
    fi
}

check_dir() {
    total_checks=$((total_checks + 1))
    if [ -d "$1" ]; then
        echo -e "${GREEN}✓${NC} $1/"
        passed_checks=$((passed_checks + 1))
    else
        echo -e "${RED}✗${NC} $1/ (MISSING)"
    fi
}

echo "Checking directory structure..."
echo ""

# Root files
echo "📁 Root files:"
check_file "README.md"
check_file "QUICKSTART.md"
check_file "EXAMPLES.md"
check_file "PROJECT_SUMMARY.md"
echo ""

# Backend directory
echo "📁 Backend directory:"
check_dir "backend"
check_file "backend/pom.xml"
check_file "backend/README.md"
check_file "backend/start.sh"
check_file "backend/start.bat"
check_file "backend/.gitignore"
echo ""

# Backend Java source
echo "📁 Backend Java sources:"
check_dir "backend/src/main/java/com/code996"
check_file "backend/src/main/java/com/code996/Code996Application.java"
check_file "backend/src/main/java/com/code996/controller/AnalysisController.java"
check_file "backend/src/main/java/com/code996/service/GitAnalysisService.java"
check_file "backend/src/main/java/com/code996/model/AnalysisRequest.java"
check_file "backend/src/main/java/com/code996/model/AnalysisResult.java"
check_file "backend/src/main/java/com/code996/model/ApiResponse.java"
check_file "backend/src/main/java/com/code996/util/GitHelper.java"
check_file "backend/src/main/java/com/code996/config/CleanupScheduler.java"
echo ""

# Backend resources
echo "📁 Backend resources:"
check_file "backend/src/main/resources/application.yml"
echo ""

# Frontend directory
echo "📁 Frontend directory:"
check_dir "frontend"
check_file "frontend/api.ts"
check_file "frontend/OnlineAnalysis.vue"
check_file "frontend/package.json"
check_file "frontend/README.md"
echo ""

# Summary
echo "======================================"
echo "Verification Summary"
echo "======================================"
echo -e "Total checks: ${YELLOW}${total_checks}${NC}"
echo -e "Passed: ${GREEN}${passed_checks}${NC}"
echo -e "Failed: ${RED}$((total_checks - passed_checks))${NC}"
echo ""

if [ $passed_checks -eq $total_checks ]; then
    echo -e "${GREEN}✓ All files are present!${NC}"
    echo ""
    echo "🚀 Ready to use!"
    echo ""
    echo "To start the backend:"
    echo "  cd backend"
    echo "  mvn spring-boot:run"
    exit 0
else
    echo -e "${RED}✗ Some files are missing!${NC}"
    echo "Please check the missing files above."
    exit 1
fi

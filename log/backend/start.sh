#!/bin/bash

# Code996 Backend Start Script

echo "======================================"
echo "Code996 Backend Service"
echo "======================================"
echo ""

# Check Java version
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed"
    echo "Please install Java 11 or higher"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Error: Java 11 or higher is required"
    echo "Current version: $JAVA_VERSION"
    exit 1
fi

# Check Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

echo "✅ Java: OK"
echo "✅ Maven: OK"
echo ""

# Navigate to backend directory
cd "$(dirname "$0")"

# Run backend
echo "🚀 Starting Code996 Backend..."
echo ""
mvn spring-boot:run

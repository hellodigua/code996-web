# Code996 Online Analysis - Quick Start Guide

## 🚀 5-Minute Setup

### Step 1: Start Backend
```bash
cd src/log/backend
mvn spring-boot:run
```

Wait for: `Code996 Backend Service Started Successfully!`

### Step 2: Test API
```bash
curl http://localhost:8080/api/analyze/health
```

### Step 3: Analyze a Repository
```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://github.com/expressjs/express.git", "maxCommits": 1000}'
```

## 📖 Full Documentation

See `README.md` in this directory for complete documentation.

## 🎯 What This Module Does

- ✅ Analyzes Git repositories online (no local cloning needed by user)
- ✅ Provides REST API for analysis
- ✅ Calculates 996 index and work patterns
- ✅ Returns detailed statistics and charts data
- ✅ Auto-cleanup temporary files
- ✅ Fully isolated in `src/log/` directory

## 🔧 Tech Stack

**Backend:**
- Java 11
- Spring Boot 2.7
- Eclipse JGit
- Maven

**Frontend:**
- Vue 3
- TypeScript
- Composition API

## 📁 Everything is in src/log/

```
src/log/
├── backend/      # Java Spring Boot service
├── frontend/     # Vue3 components
└── README.md     # Full documentation
```

**No external files modified!** ✅

# Code996 Online Analysis Module

🚀 **Git Repository Online Analysis API Service**

This module provides an independent backend service for analyzing Git repositories online, without requiring local cloning.

## ⚙️ Required Configuration

**Important:** To ensure this module doesn't interfere with the original project's TypeScript compilation, one configuration change is required:

**File:** `tsconfig.json` (in project root)  
**Change:** Add `"src/log"` to the exclude array:

```json
"exclude": ["node_modules", "dist", "src/log"]
```

**Why?** This prevents TypeScript from trying to compile files in this optional module, which could cause build conflicts.

**Impact:** Zero - The original project builds exactly as before. See `TSCONFIG_UPDATE.md` for details.

---

## 📁 Project Structure

```
src/log/
├── backend/                    # Java Spring Boot Backend
│   ├── pom.xml                # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/code996/
│   │   │   │   ├── Code996Application.java      # Main application
│   │   │   │   ├── controller/
│   │   │   │   │   └── AnalysisController.java  # REST API
│   │   │   │   ├── service/
│   │   │   │   │   └── GitAnalysisService.java  # Core analysis logic
│   │   │   │   ├── model/
│   │   │   │   │   ├── AnalysisRequest.java     # Request model
│   │   │   │   │   ├── AnalysisResult.java      # Response model
│   │   │   │   │   └── ApiResponse.java         # API wrapper
│   │   │   │   ├── util/
│   │   │   │   │   └── GitHelper.java           # Git utilities
│   │   │   │   └── config/
│   │   │   │       └── CleanupScheduler.java    # Auto cleanup
│   │   │   └── resources/
│   │   │       └── application.yml              # Configuration
│   │   └── test/
│   └── README.md
├── frontend/                   # Vue3 Frontend Integration
│   ├── api.ts                 # API client
│   ├── OnlineAnalysis.vue     # Main component
│   └── README.md
└── README.md                   # This file
```

---

## 🎯 Features

### Backend Features
- ✅ **Online Git Analysis** - Analyze any public Git repository via URL
- ✅ **JGit Integration** - Robust Git operations using Eclipse JGit
- ✅ **RESTful API** - Clean and simple REST API
- ✅ **Automatic Cleanup** - Auto-delete temporary files
- ✅ **CORS Support** - Cross-origin resource sharing enabled
- ✅ **Configurable** - Easily configure via YAML
- ✅ **Lightweight** - No database, no authentication overhead

### Analysis Metrics
- 📊 Hour distribution (0-23)
- 📊 Week distribution (Monday-Sunday)
- 📊 996 Index calculation
- 📊 Working type classification (995/996/007/Open Source)
- 📊 Overtime ratio
- 📊 Top contributors
- 📊 Workday vs Weekend commits
- 📊 Work hours vs After hours commits

---

## 🚀 Quick Start

### Prerequisites
- **Java**: JDK 11 or higher
- **Maven**: 3.6 or higher
- **Git**: For cloning repositories (backend will use it)

### 1. Start Backend Service

```bash
# Navigate to backend directory
cd src/log/backend

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080/api`

### 2. Verify Backend is Running

```bash
# Health check
curl http://localhost:8080/api/analyze/health

# Service info
curl http://localhost:8080/api/analyze/info
```

### 3. Integrate Frontend (Optional)

The frontend component is already created in `src/log/frontend/`. To use it:

**Option A: Add to existing routes** (requires modifying external files - not recommended based on requirements)

**Option B: Use as standalone page**
- Copy `OnlineAnalysis.vue` to any location
- Import the component where needed
- Use the API client (`api.ts`) directly

---

## 📖 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Health Check
```http
GET /analyze/health
```

**Response:**
```json
{
  "code": 200,
  "message": "Code996 Backend is running!",
  "data": "Code996 Backend is running!",
  "timestamp": 1702468800000
}
```

---

#### 2. Service Info
```http
GET /analyze/info
```

**Response:**
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "name": "Code996 Backend",
    "version": "1.0.0",
    "description": "Git repository online analysis service",
    "endpoint": "POST /api/analyze"
  },
  "timestamp": 1702468800000
}
```

---

#### 3. Analyze Repository
```http
POST /analyze
Content-Type: application/json
```

**Request Body:**
```json
{
  "gitUrl": "https://github.com/username/repository.git",
  "branch": "main",          // Optional, default: main/master
  "maxCommits": 5000,        // Optional, default: 10000
  "authorEmail": "user@email.com"  // Optional, filter by author
}
```

**Response:**
```json
{
  "code": 200,
  "message": "Analysis completed",
  "data": {
    "repositoryName": "repository",
    "totalCommits": 1234,
    "timeRange": {
      "start": "2023-01-01 10:30:45",
      "end": "2023-12-31 18:20:15",
      "durationDays": 364
    },
    "hourDistribution": [
      {
        "hour": 0,
        "count": 10,
        "percentage": 0.81
      },
      // ... 0-23 hours
    ],
    "weekDistribution": [
      {
        "day": 1,
        "dayName": "Monday",
        "count": 200,
        "percentage": 16.21
      },
      // ... Monday-Sunday
    ],
    "statistics": {
      "index996": 35.5,
      "workingType": "996",
      "overtimeRatio": 42.3,
      "workdayCommits": 1000,
      "weekendCommits": 234,
      "workHourCommits": 800,
      "afterHourCommits": 434
    },
    "topContributors": [
      {
        "name": "John Doe",
        "email": "john@example.com",
        "commits": 500,
        "percentage": 40.52
      }
      // ... top 10 contributors
    ]
  },
  "timestamp": 1702468800000
}
```

---

## ⚙️ Configuration

Edit `src/log/backend/src/main/resources/application.yml`:

```yaml
server:
  port: 8080                    # Change backend port

code996:
  temp-dir: ${java.io.tmpdir}/code996  # Temp directory for cloning
  max-repo-size: 500            # Max repo size in MB
  analysis-timeout: 300         # Analysis timeout in seconds
  max-commits: 10000            # Max commits to analyze
  cleanup-interval: 30          # Cleanup interval in minutes
```

---

## 🧪 Testing

### Test with curl

```bash
# Analyze a repository
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/expressjs/express.git",
    "maxCommits": 1000
  }'
```

### Test with JavaScript

```javascript
fetch('http://localhost:8080/api/analyze', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    gitUrl: 'https://github.com/vuejs/core.git',
    maxCommits: 1000
  })
})
.then(res => res.json())
.then(data => console.log(data))
```

---

## 🛠️ Development

### Build for Production

```bash
cd src/log/backend
mvn clean package

# Run the JAR
java -jar target/code996-backend-1.0.0.jar
```

### Customize Analysis Logic

Edit `src/log/backend/src/main/java/com/code996/service/GitAnalysisService.java`

Key methods:
- `analyze()` - Main analysis entry point
- `extractCommits()` - Extract commits from repository
- `buildStatistics()` - Calculate 996 index and statistics
- `buildHourDistribution()` - Hour-based distribution
- `buildWeekDistribution()` - Week-based distribution

---

## 📝 Usage Examples

### Example 1: Basic Analysis

```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://github.com/torvalds/linux.git", "maxCommits": 5000}'
```

### Example 2: Specific Branch

```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/facebook/react.git",
    "branch": "main",
    "maxCommits": 3000
  }'
```

### Example 3: Filter by Author

```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/vuejs/core.git",
    "authorEmail": "evan@",
    "maxCommits": 2000
  }'
```

---

## 🔧 Troubleshooting

### Backend won't start
- Check Java version: `java -version` (should be 11+)
- Check Maven: `mvn -version`
- Check port 8080 is not in use: `netstat -ano | findstr :8080`

### Analysis fails
- Ensure Git URL is valid and public
- Check internet connection
- Increase timeout in `application.yml`
- Check logs in console

### CORS errors
- Verify `spring.mvc.cors.allowed-origins` in `application.yml`
- Try with `allowed-origins: "*"` for testing

---

## 🎨 Frontend Integration Guide

### Using the API Client

```typescript
import { analyzeRepository, checkHealth } from './log/frontend/api'

// Check if backend is online
const isOnline = await checkHealth()

if (isOnline) {
  // Analyze repository
  const result = await analyzeRepository({
    gitUrl: 'https://github.com/user/repo.git',
    maxCommits: 5000
  })
  
  console.log(result)
}
```

### Using the Vue Component

```vue
<template>
  <OnlineAnalysis />
</template>

<script setup>
import OnlineAnalysis from './log/frontend/OnlineAnalysis.vue'
</script>
```

---

## 📊 Working Type Classification

| Index996 | Type | Description |
|-----------|------|-------------|
| < 20 | 995 | Normal working hours |
| 20-40 | 996 | Work overtime occasionally |
| 40-60 | 007 | Heavy overtime |
| > 60 | Open Source | Open source or flexible schedule |

---

## 🚀 Performance Tips

1. **Limit commits**: Use `maxCommits` parameter for large repositories
2. **Use branches**: Specify branch to avoid analyzing all branches
3. **Filter authors**: Use `authorEmail` to analyze specific contributors
4. **Increase timeout**: For very large repos, increase `analysis-timeout`

---

## 🔒 Security Notes

- ⚠️ This service is designed for **public repositories only**
- ⚠️ No authentication implemented (intentionally lightweight)
- ⚠️ Temporary directories are auto-cleaned every 10 minutes
- ⚠️ Consider adding rate limiting for production use

---

## 📄 License

This module follows the same license as the parent project (MIT).

---

## 🤝 Contributing

This module is completely isolated in `src/log/` and doesn't modify any external files.

To contribute:
1. Only modify files within `src/log/`
2. Test thoroughly before submitting
3. Update this README if needed

---

## 📧 Support

For issues related to this module, please check:
1. Backend logs in console
2. Frontend browser console
3. Network tab for API calls

---

## 🎉 Features Roadmap

- [ ] Add caching for analyzed repositories
- [ ] Support private repositories (with token)
- [ ] Add GitHub API integration (faster than cloning)
- [ ] Export results as PDF/JSON
- [ ] Real-time progress updates via WebSocket
- [ ] Multi-repository comparison
- [ ] Historical trend analysis

---

**Enjoy analyzing Git repositories! 🚀**

# Code996 Online Analysis Module - Demo & Examples

## 📝 API Examples

### Example 1: Analyze Express.js Repository

**Request:**
```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/expressjs/express.git",
    "maxCommits": 2000
  }'
```

**Use Case:** Analyze a popular Node.js framework to see the team's work patterns.

---

### Example 2: Analyze Vue.js Core (Specific Branch)

**Request:**
```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/vuejs/core.git",
    "branch": "main",
    "maxCommits": 3000
  }'
```

**Use Case:** Analyze Vue 3's main branch to understand the core team's development intensity.

---

### Example 3: Filter by Specific Author

**Request:**
```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/facebook/react.git",
    "authorEmail": "dan.abramov",
    "maxCommits": 1000
  }'
```

**Use Case:** Analyze commits from a specific contributor.

---

### Example 4: Small Repository Quick Analysis

**Request:**
```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "gitUrl": "https://github.com/sindresorhus/is.git",
    "maxCommits": 500
  }'
```

**Use Case:** Quick analysis of a smaller repository.

---

## 🎨 Frontend Usage Examples

### Example 1: Basic Integration

```typescript
import { analyzeRepository } from '@/log/frontend/api'

async function analyze() {
  try {
    const result = await analyzeRepository({
      gitUrl: 'https://github.com/user/repo.git',
      maxCommits: 5000
    })
    
    console.log('Repository:', result.repositoryName)
    console.log('Total Commits:', result.totalCommits)
    console.log('996 Index:', result.statistics.index996)
    console.log('Working Type:', result.statistics.workingType)
  } catch (error) {
    console.error('Analysis failed:', error)
  }
}
```

---

### Example 2: Check Backend Status

```typescript
import { checkHealth } from '@/log/frontend/api'

async function checkBackendStatus() {
  const isOnline = await checkHealth()
  
  if (isOnline) {
    console.log('✅ Backend is ready!')
  } else {
    console.log('❌ Backend is offline')
  }
}
```

---

### Example 3: Use the Vue Component

```vue
<template>
  <div class="app">
    <!-- Existing content -->
    
    <!-- Add online analysis -->
    <OnlineAnalysis />
  </div>
</template>

<script setup lang="ts">
import OnlineAnalysis from '@/log/frontend/OnlineAnalysis.vue'
</script>
```

---

## 📊 Response Data Examples

### Successful Analysis Response

```json
{
  "code": 200,
  "message": "Analysis completed",
  "data": {
    "repositoryName": "express",
    "totalCommits": 5832,
    "timeRange": {
      "start": "2009-06-26 21:29:09",
      "end": "2023-12-10 15:42:33",
      "durationDays": 5281
    },
    "hourDistribution": [
      { "hour": 0, "count": 180, "percentage": 3.09 },
      { "hour": 1, "count": 165, "percentage": 2.83 },
      { "hour": 9, "count": 420, "percentage": 7.20 },
      { "hour": 14, "count": 510, "percentage": 8.75 }
    ],
    "weekDistribution": [
      { "day": 1, "dayName": "Monday", "count": 890, "percentage": 15.26 },
      { "day": 2, "dayName": "Tuesday", "count": 920, "percentage": 15.78 }
    ],
    "statistics": {
      "index996": 38.5,
      "workingType": "996",
      "overtimeRatio": 45.2,
      "workdayCommits": 4200,
      "weekendCommits": 1632,
      "workHourCommits": 3500,
      "afterHourCommits": 2332
    },
    "topContributors": [
      {
        "name": "TJ Holowaychuk",
        "email": "tj@vision-media.ca",
        "commits": 2150,
        "percentage": 36.87
      }
    ]
  },
  "timestamp": 1702468800000
}
```

---

### Error Response

```json
{
  "code": 500,
  "message": "Analysis failed: Repository not found",
  "data": null,
  "timestamp": 1702468800000
}
```

---

## 🧪 Testing Scenarios

### Test 1: Small Repository
```bash
# Fast analysis (< 10 seconds)
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://github.com/lodash/lodash.git", "maxCommits": 500}'
```

### Test 2: Large Repository
```bash
# Slower but comprehensive (30-60 seconds)
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://github.com/microsoft/vscode.git", "maxCommits": 10000}'
```

### Test 3: Invalid URL
```bash
# Should return error
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"gitUrl": "https://invalid-url.com/repo.git"}'
```

---

## 🎯 Real-World Use Cases

### Use Case 1: Team Productivity Analysis
Analyze your team's repository to understand work patterns and identify potential burnout risks.

### Use Case 2: Open Source Project Research
Before contributing to an open source project, analyze its commit patterns to understand the team's activity and responsiveness.

### Use Case 3: Hiring Decision Support
Analyze a candidate's public repositories to understand their coding habits and work ethic (with appropriate consent and ethical considerations).

### Use Case 4: Project Health Monitoring
Regularly analyze your project to track development intensity over time and ensure sustainable work practices.

---

## 💡 Tips & Best Practices

1. **Start Small**: Test with repositories < 1000 commits first
2. **Use maxCommits**: Limit commits for faster analysis
3. **Branch Specific**: Specify branch for focused analysis
4. **Cache Results**: Store results locally to avoid re-analyzing
5. **Monitor Backend**: Check logs for performance insights

---

## 🐛 Common Issues & Solutions

### Issue 1: Timeout
**Problem:** Analysis takes too long  
**Solution:** Reduce `maxCommits` or increase `analysis-timeout` in config

### Issue 2: Memory Error
**Problem:** Large repo causes memory issues  
**Solution:** Increase JVM heap size: `java -Xmx2g -jar app.jar`

### Issue 3: CORS Error
**Problem:** Frontend can't access backend  
**Solution:** Check CORS settings in `application.yml`

---

**Happy Analyzing! 🚀**

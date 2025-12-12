# Code996 Frontend Integration

Vue3 components for integrating with the backend analysis service.

## Files

- `api.ts` - API client for backend communication
- `OnlineAnalysis.vue` - Main analysis component

## Usage

### 1. Use API Client

```typescript
import { analyzeRepository, checkHealth } from './api'

// Check backend status
const isOnline = await checkHealth()

// Analyze repository
const result = await analyzeRepository({
  gitUrl: 'https://github.com/user/repo.git',
  maxCommits: 5000
})
```

### 2. Use Vue Component

```vue
<template>
  <OnlineAnalysis />
</template>

<script setup>
import OnlineAnalysis from './OnlineAnalysis.vue'
</script>
```

## Backend Required

Make sure backend service is running:
```bash
cd ../backend
mvn spring-boot:run
```

See parent README.md for full documentation.

<template>
  <div class="online-analysis">
    <div class="analysis-card">
      <h2 class="title">🌐 Online Git Analysis</h2>
      <p class="description">
        Analyze any public Git repository directly from URL
      </p>

      <!-- Backend Status -->
      <div class="backend-status" :class="{ online: backendOnline, offline: !backendOnline }">
        <span class="status-dot"></span>
        <span class="status-text">
          Backend: {{ backendOnline ? 'Online' : 'Offline' }}
        </span>
        <button v-if="!backendOnline" @click="checkBackend" class="btn-check">
          Retry
        </button>
      </div>

      <!-- Input Form -->
      <div class="form-group">
        <label for="gitUrl">Git Repository URL</label>
        <input
          id="gitUrl"
          v-model="gitUrl"
          type="text"
          placeholder="https://github.com/username/repository.git"
          :disabled="loading || !backendOnline"
          @keyup.enter="analyze"
        />
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="branch">Branch (optional)</label>
          <input
            id="branch"
            v-model="branch"
            type="text"
            placeholder="main"
            :disabled="loading || !backendOnline"
          />
        </div>
        <div class="form-group">
          <label for="maxCommits">Max Commits (optional)</label>
          <input
            id="maxCommits"
            v-model.number="maxCommits"
            type="number"
            placeholder="5000"
            :disabled="loading || !backendOnline"
          />
        </div>
      </div>

      <!-- Error Message -->
      <div v-if="error" class="error-message">
        {{ error }}
      </div>

      <!-- Action Buttons -->
      <div class="actions">
        <button
          class="btn-analyze"
          :disabled="!canAnalyze"
          @click="analyze"
        >
          <span v-if="loading" class="loading-spinner"></span>
          {{ loading ? 'Analyzing...' : 'Analyze Repository' }}
        </button>
      </div>

      <!-- Progress Info -->
      <div v-if="loading" class="progress-info">
        <p>{{ progressMessage }}</p>
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: progress + '%' }"></div>
        </div>
      </div>

      <!-- Quick Examples -->
      <div class="examples">
        <p class="examples-title">Quick Examples:</p>
        <button
          v-for="example in examples"
          :key="example.url"
          class="btn-example"
          :disabled="loading || !backendOnline"
          @click="loadExample(example)"
        >
          {{ example.name }}
        </button>
      </div>
    </div>

    <!-- Instructions -->
    <div class="instructions">
      <h3>📖 How to Use</h3>
      <ol>
        <li>Make sure the backend service is running (see status above)</li>
        <li>Enter a public Git repository URL</li>
        <li>Optionally specify branch and max commits</li>
        <li>Click "Analyze Repository" and wait for results</li>
      </ol>
      
      <h3>🚀 Start Backend</h3>
      <pre><code>cd src/log/backend
mvn spring-boot:run</code></pre>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { analyzeRepository, checkHealth, convertToFrontendFormat } from '../api'

const router = useRouter()

// State
const backendOnline = ref(false)
const gitUrl = ref('')
const branch = ref('')
const maxCommits = ref<number | null>(null)
const loading = ref(false)
const error = ref('')
const progressMessage = ref('')
const progress = ref(0)

// Examples
const examples = [
  { name: 'Vue.js', url: 'https://github.com/vuejs/core.git' },
  { name: 'React', url: 'https://github.com/facebook/react.git' },
  { name: 'Express', url: 'https://github.com/expressjs/express.git' },
]

// Computed
const canAnalyze = computed(() => {
  return backendOnline.value && !loading.value && gitUrl.value.trim() !== ''
})

// Methods
async function checkBackend() {
  try {
    backendOnline.value = await checkHealth()
    if (backendOnline.value) {
      error.value = ''
    }
  } catch (err) {
    backendOnline.value = false
  }
}

function loadExample(example: typeof examples[0]) {
  gitUrl.value = example.url
  branch.value = ''
  maxCommits.value = 1000 // Limit for quick demo
  error.value = ''
}

async function analyze() {
  if (!canAnalyze.value) return

  loading.value = true
  error.value = ''
  progress.value = 0
  progressMessage.value = 'Connecting to backend...'

  try {
    // Simulate progress
    const progressInterval = setInterval(() => {
      if (progress.value < 90) {
        progress.value += Math.random() * 10
        if (progress.value < 30) {
          progressMessage.value = 'Cloning repository...'
        } else if (progress.value < 60) {
          progressMessage.value = 'Analyzing commits...'
        } else {
          progressMessage.value = 'Generating statistics...'
        }
      }
    }, 500)

    // Call API
    const result = await analyzeRepository({
      gitUrl: gitUrl.value.trim(),
      branch: branch.value.trim() || undefined,
      maxCommits: maxCommits.value || undefined,
    })

    clearInterval(progressInterval)
    progress.value = 100
    progressMessage.value = 'Analysis completed!'

    // Convert to frontend format
    const frontendData = convertToFrontendFormat(result)

    // Store in sessionStorage for result page
    sessionStorage.setItem('code996_online_result', JSON.stringify(frontendData))

    // Navigate to result page
    setTimeout(() => {
      router.push('/result')
    }, 500)

  } catch (err: any) {
    error.value = err.message || 'Analysis failed. Please check the URL and try again.'
    loading.value = false
    progress.value = 0
  }
}

// Lifecycle
onMounted(() => {
  checkBackend()
})
</script>

<style scoped lang="scss">
.online-analysis {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.analysis-card {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
}

.title {
  font-size: 28px;
  margin-bottom: 10px;
  color: #333;
}

.description {
  color: #666;
  margin-bottom: 20px;
}

.backend-status {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border-radius: 8px;
  margin-bottom: 24px;
  font-size: 14px;

  &.online {
    background: #e8f5e9;
    color: #2e7d32;
  }

  &.offline {
    background: #ffebee;
    color: #c62828;
  }
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  
  .online & {
    background: #4caf50;
  }
  
  .offline & {
    background: #f44336;
  }
}

.btn-check {
  margin-left: auto;
  padding: 4px 12px;
  border: none;
  border-radius: 4px;
  background: #fff;
  color: #c62828;
  cursor: pointer;
  font-size: 12px;
  
  &:hover {
    background: #fdd;
  }
}

.form-group {
  margin-bottom: 20px;

  label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: #333;
  }

  input {
    width: 100%;
    padding: 12px;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    font-size: 14px;
    transition: border-color 0.3s;

    &:focus {
      outline: none;
      border-color: #2196f3;
    }

    &:disabled {
      background: #f5f5f5;
      cursor: not-allowed;
    }
  }
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.error-message {
  padding: 12px;
  background: #ffebee;
  color: #c62828;
  border-radius: 8px;
  margin-bottom: 20px;
  font-size: 14px;
}

.actions {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.btn-analyze {
  flex: 1;
  padding: 14px 24px;
  border: none;
  border-radius: 8px;
  background: #2196f3;
  color: white;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;

  &:hover:not(:disabled) {
    background: #1976d2;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(33, 150, 243, 0.3);
  }

  &:disabled {
    background: #bdbdbd;
    cursor: not-allowed;
    transform: none;
  }
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.progress-info {
  margin-bottom: 20px;

  p {
    color: #666;
    margin-bottom: 8px;
    font-size: 14px;
  }
}

.progress-bar {
  width: 100%;
  height: 8px;
  background: #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #2196f3, #21cbf3);
  transition: width 0.3s ease;
}

.examples {
  padding-top: 20px;
  border-top: 1px solid #e0e0e0;

  .examples-title {
    color: #666;
    font-size: 14px;
    margin-bottom: 12px;
  }

  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.btn-example {
  padding: 8px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  background: white;
  color: #666;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s;

  &:hover:not(:disabled) {
    border-color: #2196f3;
    color: #2196f3;
    background: #f0f8ff;
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
}

.instructions {
  background: #f5f5f5;
  padding: 24px;
  border-radius: 12px;

  h3 {
    font-size: 18px;
    margin-bottom: 12px;
    color: #333;
  }

  ol {
    margin-left: 20px;
    margin-bottom: 20px;
    color: #666;

    li {
      margin-bottom: 8px;
      line-height: 1.6;
    }
  }

  pre {
    background: #263238;
    color: #aed581;
    padding: 16px;
    border-radius: 8px;
    overflow-x: auto;
    font-size: 13px;
    margin: 0;

    code {
      font-family: 'Consolas', 'Monaco', monospace;
    }
  }
}
</style>

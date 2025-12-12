/**
 * Code996 Online Analysis Service
 * API client for backend communication
 */

export interface AnalysisRequest {
  gitUrl: string
  branch?: string
  maxCommits?: number
  authorEmail?: string
}

export interface AnalysisResult {
  repositoryName: string
  totalCommits: number
  timeRange: {
    start: string
    end: string
    durationDays: number
  }
  hourDistribution: Array<{
    hour: number
    count: number
    percentage: number
  }>
  weekDistribution: Array<{
    day: number
    dayName: string
    count: number
    percentage: number
  }>
  statistics: {
    index996: number
    workingType: string
    overtimeRatio: number
    workdayCommits: number
    weekendCommits: number
    workHourCommits: number
    afterHourCommits: number
  }
  topContributors: Array<{
    name: string
    email: string
    commits: number
    percentage: number
  }>
}

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  timestamp: number
}

// API Configuration
const API_BASE_URL = 'http://localhost:8080/api'

/**
 * Analyze Git repository via backend API
 */
export async function analyzeRepository(request: AnalysisRequest): Promise<AnalysisResult> {
  try {
    const response = await fetch(`${API_BASE_URL}/analyze`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(request),
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    const result: ApiResponse<AnalysisResult> = await response.json()

    if (result.code !== 200) {
      throw new Error(result.message || 'Analysis failed')
    }

    return result.data
  } catch (error) {
    console.error('Analysis failed:', error)
    throw error
  }
}

/**
 * Check backend service health
 */
export async function checkHealth(): Promise<boolean> {
  try {
    const response = await fetch(`${API_BASE_URL}/analyze/health`)
    const result: ApiResponse<string> = await response.json()
    return result.code === 200
  } catch (error) {
    console.error('Health check failed:', error)
    return false
  }
}

/**
 * Get service information
 */
export async function getServiceInfo(): Promise<any> {
  try {
    const response = await fetch(`${API_BASE_URL}/analyze/info`)
    const result: ApiResponse<any> = await response.json()
    return result.data
  } catch (error) {
    console.error('Failed to get service info:', error)
    return null
  }
}

/**
 * Convert backend result to frontend format (for compatibility with existing result page)
 */
export function convertToFrontendFormat(result: AnalysisResult): any {
  return {
    repositoryName: result.repositoryName,
    totalCount: result.totalCommits,
    timeStr: `${result.timeRange.start} ~ ${result.timeRange.end}`,
    index996: result.statistics.index996,
    workingType: result.statistics.workingType,
    workingTypeStr: getWorkingTypeDescription(result.statistics.workingType),
    overTimeRadio: result.statistics.overtimeRatio,
    isStandard: result.totalCommits > 50,
    
    // Hour distribution
    hourDistribution: result.hourDistribution.map(h => ({
      label: `${h.hour}:00`,
      value: h.count,
    })),
    
    // Week distribution
    weekDistribution: result.weekDistribution.map(w => ({
      label: w.dayName.substring(0, 3),
      value: w.count,
    })),
    
    // Contributors
    contributors: result.topContributors,
  }
}

function getWorkingTypeDescription(type: string): string {
  const descriptions: Record<string, string> = {
    '995': 'Normal working hours',
    '996': 'Work overtime occasionally',
    '007': 'Heavy overtime',
    'Open Source': 'Open source or flexible schedule',
  }
  return descriptions[type] || type
}

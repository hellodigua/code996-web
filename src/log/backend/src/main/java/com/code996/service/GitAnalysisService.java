package com.code996.service;

import com.code996.model.AnalysisRequest;
import com.code996.model.AnalysisResult;
import com.code996.util.GitHelper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Git analysis service
 */
@Slf4j
@Service
public class GitAnalysisService {

    @Value("${code996.temp-dir}")
    private String tempDir;

    @Value("${code996.max-commits:10000}")
    private Integer maxCommits;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Analyze Git repository
     */
    public AnalysisResult analyze(AnalysisRequest request) throws Exception {
        log.info("Starting analysis for repository: {}", request.getGitUrl());

        // Create temp directory
        File repoDir = new File(tempDir, "repo_" + System.currentTimeMillis());
        repoDir.mkdirs();

        try {
            // Clone repository
            log.info("Cloning repository to: {}", repoDir.getAbsolutePath());
            Git git = GitHelper.cloneRepository(request.getGitUrl(), repoDir, request.getBranch());
            Repository repository = git.getRepository();

            // Get commits
            List<CommitData> commits = extractCommits(git, request);
            log.info("Extracted {} commits", commits.size());

            if (commits.isEmpty()) {
                throw new RuntimeException("No commits found in repository");
            }

            // Build analysis result
            AnalysisResult result = buildAnalysisResult(commits, request.getGitUrl());

            git.close();
            return result;

        } finally {
            // Cleanup
            GitHelper.deleteDirectory(repoDir);
            log.info("Cleanup completed");
        }
    }

    /**
     * Extract commits from repository
     */
    private List<CommitData> extractCommits(Git git, AnalysisRequest request) throws Exception {
        List<CommitData> commits = new ArrayList<>();
        Iterable<RevCommit> logs = git.log().all().call();

        int count = 0;
        int maxToAnalyze = request.getMaxCommits() != null ? request.getMaxCommits() : maxCommits;

        for (RevCommit commit : logs) {
            if (maxToAnalyze > 0 && count >= maxToAnalyze) {
                break;
            }

            PersonIdent author = commit.getAuthorIdent();
            
            // Filter by author if specified
            if (request.getAuthorEmail() != null && 
                !request.getAuthorEmail().isEmpty() &&
                !author.getEmailAddress().contains(request.getAuthorEmail())) {
                continue;
            }

            Instant instant = author.getWhen().toInstant();
            ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());

            commits.add(CommitData.builder()
                    .hash(commit.getName())
                    .author(author.getName())
                    .email(author.getEmailAddress())
                    .message(commit.getShortMessage())
                    .timestamp(instant.toEpochMilli())
                    .dateTime(dateTime)
                    .hour(dateTime.getHour())
                    .dayOfWeek(dateTime.getDayOfWeek().getValue())
                    .build());

            count++;
        }

        return commits;
    }

    /**
     * Build analysis result from commits
     */
    private AnalysisResult buildAnalysisResult(List<CommitData> commits, String gitUrl) {
        // Sort commits by time
        commits.sort(Comparator.comparing(CommitData::getTimestamp));

        // Extract repository name
        String repoName = extractRepoName(gitUrl);

        // Time range
        CommitData firstCommit = commits.get(0);
        CommitData lastCommit = commits.get(commits.size() - 1);
        long durationDays = Duration.between(
                firstCommit.getDateTime(),
                lastCommit.getDateTime()
        ).toDays();

        AnalysisResult.TimeRange timeRange = AnalysisResult.TimeRange.builder()
                .start(firstCommit.getDateTime().format(DATE_FORMATTER))
                .end(lastCommit.getDateTime().format(DATE_FORMATTER))
                .durationDays(durationDays)
                .build();

        // Hour distribution
        List<AnalysisResult.HourData> hourDistribution = buildHourDistribution(commits);

        // Week distribution
        List<AnalysisResult.WeekData> weekDistribution = buildWeekDistribution(commits);

        // Statistics
        AnalysisResult.Statistics statistics = buildStatistics(commits);

        // Top contributors
        List<AnalysisResult.Contributor> topContributors = buildTopContributors(commits);

        return AnalysisResult.builder()
                .repositoryName(repoName)
                .totalCommits(commits.size())
                .timeRange(timeRange)
                .hourDistribution(hourDistribution)
                .weekDistribution(weekDistribution)
                .statistics(statistics)
                .topContributors(topContributors)
                .build();
    }

    /**
     * Build hour distribution (0-23)
     */
    private List<AnalysisResult.HourData> buildHourDistribution(List<CommitData> commits) {
        Map<Integer, Long> hourMap = commits.stream()
                .collect(Collectors.groupingBy(CommitData::getHour, Collectors.counting()));

        List<AnalysisResult.HourData> result = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            long count = hourMap.getOrDefault(hour, 0L);
            double percentage = (count * 100.0) / commits.size();
            
            result.add(AnalysisResult.HourData.builder()
                    .hour(hour)
                    .count((int) count)
                    .percentage(Math.round(percentage * 100.0) / 100.0)
                    .build());
        }
        return result;
    }

    /**
     * Build week distribution (1-7)
     */
    private List<AnalysisResult.WeekData> buildWeekDistribution(List<CommitData> commits) {
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        Map<Integer, Long> weekMap = commits.stream()
                .collect(Collectors.groupingBy(CommitData::getDayOfWeek, Collectors.counting()));

        List<AnalysisResult.WeekData> result = new ArrayList<>();
        for (int day = 1; day <= 7; day++) {
            long count = weekMap.getOrDefault(day, 0L);
            double percentage = (count * 100.0) / commits.size();
            
            result.add(AnalysisResult.WeekData.builder()
                    .day(day)
                    .dayName(dayNames[day - 1])
                    .count((int) count)
                    .percentage(Math.round(percentage * 100.0) / 100.0)
                    .build());
        }
        return result;
    }

    /**
     * Build statistics
     */
    private AnalysisResult.Statistics buildStatistics(List<CommitData> commits) {
        int workdayCommits = 0;
        int weekendCommits = 0;
        int workHourCommits = 0;
        int afterHourCommits = 0;

        for (CommitData commit : commits) {
            // Workday vs Weekend (Monday-Friday vs Saturday-Sunday)
            if (commit.getDayOfWeek() <= 5) {
                workdayCommits++;
            } else {
                weekendCommits++;
            }

            // Work hours (9:00-18:00) vs After hours
            int hour = commit.getHour();
            if (hour >= 9 && hour < 18) {
                workHourCommits++;
            } else {
                afterHourCommits++;
            }
        }

        // Calculate 996 index
        double overtimeRatio = (afterHourCommits * 100.0) / commits.size();
        double weekendRatio = (weekendCommits * 100.0) / commits.size();
        double index996 = Math.round((overtimeRatio + weekendRatio) / 2.0 * 100.0) / 100.0;

        // Determine working type
        String workingType;
        if (index996 < 20) {
            workingType = "995";
        } else if (index996 < 40) {
            workingType = "996";
        } else if (index996 < 60) {
            workingType = "007";
        } else {
            workingType = "Open Source";
        }

        return AnalysisResult.Statistics.builder()
                .index996(index996)
                .workingType(workingType)
                .overtimeRatio(Math.round(overtimeRatio * 100.0) / 100.0)
                .workdayCommits(workdayCommits)
                .weekendCommits(weekendCommits)
                .workHourCommits(workHourCommits)
                .afterHourCommits(afterHourCommits)
                .build();
    }

    /**
     * Build top contributors
     */
    private List<AnalysisResult.Contributor> buildTopContributors(List<CommitData> commits) {
        Map<String, ContributorData> contributorMap = new HashMap<>();

        for (CommitData commit : commits) {
            String key = commit.getEmail();
            contributorMap.putIfAbsent(key, new ContributorData(commit.getAuthor(), commit.getEmail()));
            contributorMap.get(key).incrementCommits();
        }

        return contributorMap.values().stream()
                .sorted((a, b) -> b.getCommits().compareTo(a.getCommits()))
                .limit(10)
                .map(c -> AnalysisResult.Contributor.builder()
                        .name(c.getName())
                        .email(c.getEmail())
                        .commits(c.getCommits())
                        .percentage(Math.round((c.getCommits() * 100.0 / commits.size()) * 100.0) / 100.0)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Extract repository name from URL
     */
    private String extractRepoName(String gitUrl) {
        String[] parts = gitUrl.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.replace(".git", "");
    }

    // Inner classes
    @lombok.Data
    @lombok.Builder
    private static class CommitData {
        private String hash;
        private String author;
        private String email;
        private String message;
        private Long timestamp;
        private ZonedDateTime dateTime;
        private Integer hour;
        private Integer dayOfWeek;
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    private static class ContributorData {
        private String name;
        private String email;
        private Integer commits = 0;

        public ContributorData(String name, String email) {
            this.name = name;
            this.email = email;
            this.commits = 0;
        }

        public void incrementCommits() {
            this.commits++;
        }
    }
}

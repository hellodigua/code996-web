package com.code996.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Analysis result model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {

    /**
     * Repository name
     */
    private String repositoryName;

    /**
     * Total commits analyzed
     */
    private Integer totalCommits;

    /**
     * Analysis time range
     */
    private TimeRange timeRange;

    /**
     * Hour distribution (0-23)
     */
    private List<HourData> hourDistribution;

    /**
     * Week distribution (Monday-Sunday)
     */
    private List<WeekData> weekDistribution;

    /**
     * Statistics summary
     */
    private Statistics statistics;

    /**
     * Top contributors
     */
    private List<Contributor> topContributors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeRange {
        private String start;
        private String end;
        private Long durationDays;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HourData {
        private Integer hour;
        private Integer count;
        private Double percentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekData {
        private Integer day; // 1-7 (Monday-Sunday)
        private String dayName;
        private Integer count;
        private Double percentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Statistics {
        private Double index996;
        private String workingType;
        private Double overtimeRatio;
        private Integer workdayCommits;
        private Integer weekendCommits;
        private Integer workHourCommits;
        private Integer afterHourCommits;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contributor {
        private String name;
        private String email;
        private Integer commits;
        private Double percentage;
    }
}

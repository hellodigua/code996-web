package com.code996.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Cleanup scheduler for temporary directories
 */
@Slf4j
@Component
public class CleanupScheduler {

    @Value("${code996.temp-dir}")
    private String tempDir;

    @Value("${code996.cleanup-interval:30}")
    private Integer cleanupInterval;

    /**
     * Clean up old temporary directories
     * Runs every 10 minutes
     */
    @Scheduled(fixedRate = 600000) // 10 minutes
    public void cleanupTempDirectories() {
        try {
            File tempDirectory = new File(tempDir);
            if (!tempDirectory.exists()) {
                return;
            }

            log.info("Starting cleanup of temp directories in: {}", tempDir);

            File[] files = tempDirectory.listFiles();
            if (files == null) {
                return;
            }

            int deletedCount = 0;
            Instant cutoffTime = Instant.now().minus(cleanupInterval, ChronoUnit.MINUTES);

            for (File file : files) {
                if (file.isDirectory() && file.getName().startsWith("repo_")) {
                    try {
                        Path path = Paths.get(file.getAbsolutePath());
                        Instant lastModified = Files.getLastModifiedTime(path).toInstant();

                        if (lastModified.isBefore(cutoffTime)) {
                            deleteDirectory(file);
                            deletedCount++;
                        }
                    } catch (IOException e) {
                        log.error("Failed to check/delete directory: {}", file.getAbsolutePath(), e);
                    }
                }
            }

            log.info("Cleanup completed. Deleted {} directories", deletedCount);

        } catch (Exception e) {
            log.error("Error during cleanup", e);
        }
    }

    private void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}

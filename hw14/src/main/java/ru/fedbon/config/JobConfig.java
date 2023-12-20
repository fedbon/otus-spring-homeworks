package ru.fedbon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;


@RequiredArgsConstructor
@Configuration
@Slf4j
public class JobConfig {

    private final JobRepository jobRepository;

    @Bean
    public Job libraryMigrationJob(Step bookMigration, Step authorsMigration, Step genresMigration) {
        return new JobBuilder("libraryMigrationJob", jobRepository)
                .start(authorsMigration)
                .next(genresMigration)
                .next(bookMigration)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }
}
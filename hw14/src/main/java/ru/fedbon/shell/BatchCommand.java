package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Console;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import static java.lang.String.format;


@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class BatchCommand {

    private final JobLauncher jobLauncher;

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    private final Job libraryMigrationJob;

    @ShellMethod(value = "start-h2-web-console", key = "sc")
    public String startConsole(@ShellOption(defaultValue = "-browser") String[] args) {
        try {
            Console.main(args);
        } catch (SQLException e) {
            return e.getMessage();
        }
        return format("Консоль запущена %s", Arrays.toString(args));
    }

    @ShellMethod(value = "startMigration", key = "sm")
    public void startMigrationWithJobExplorer() throws Exception {
        var execution = jobLauncher.run(libraryMigrationJob, new JobParametersBuilder()
                .addDate("currentDate", new Date())
                .toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        var jobParameters = new JobParametersBuilder()
                .addDate("currentDate", new Date())
                .toJobParameters();

        var executionId = jobLauncher.run(libraryMigrationJob, jobParameters).getId();
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance("libraryMigrationJob"));
    }
}

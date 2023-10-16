package ru.fedbon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;


@ConfigurationProperties(prefix = "app.properties")
@RequiredArgsConstructor
public class AppProperties implements LocaleProvider, ScoreToPassProvider, LocalizedResourceNameProvider {

    private final String resourceName;

    private final int scoreToPass;

    private final Locale locale;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getScoreToPass() {
        return scoreToPass;
    }

    @Override
    public String getLocalizedResourceName() {
        return locale.toString() + "_" + resourceName;
    }
}

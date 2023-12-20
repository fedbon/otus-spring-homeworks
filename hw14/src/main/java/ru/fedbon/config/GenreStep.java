package ru.fedbon.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.fedbon.model.jpa.Genre;
import ru.fedbon.model.mongo.GenreDocument;




@RequiredArgsConstructor
@Configuration
public class GenreStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final Converter<Genre, GenreDocument> genreConverter;


    @Bean
    public ItemReader<Genre> genreReader() {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .queryString("from Genre")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


    @Bean
    public ItemProcessor<Genre, GenreDocument> genreProcessor() {
        return genreConverter::convert;
    }

    @Bean
    public MongoItemWriter<GenreDocument> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreDocument>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public Step genresMigration(ItemReader<Genre> reader, ItemWriter<GenreDocument> writer,
                                     ItemProcessor<Genre, GenreDocument> itemProcessor) {
        return new StepBuilder("genresMigration", jobRepository)
                .<Genre, GenreDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
               .build();
    }
}

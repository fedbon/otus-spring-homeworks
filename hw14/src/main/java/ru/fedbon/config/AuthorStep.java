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
import ru.fedbon.model.jpa.Author;
import ru.fedbon.model.mongo.AuthorDocument;



@RequiredArgsConstructor
@Configuration
public class AuthorStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final Converter<Author, AuthorDocument> authorConverter;


    @Bean
    public ItemReader<Author> authorReader() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorItemReader")
                .queryString("from Author")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorDocument> authorProcessor() {
        return authorConverter::convert;
    }

    @Bean
    public MongoItemWriter<AuthorDocument> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorDocument>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public Step authorsMigration(ItemReader<Author> reader, ItemWriter<AuthorDocument> writer,
                                     ItemProcessor<Author, AuthorDocument> itemProcessor) {
        return new StepBuilder("authorsMigration", jobRepository)
                .<Author, AuthorDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
               .build();
    }
}

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
import ru.fedbon.model.jpa.Book;
import ru.fedbon.model.mongo.BookDocument;



@RequiredArgsConstructor
@Configuration
public class BookStep {
    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;


    private final PlatformTransactionManager platformTransactionManager;


    private final EntityManagerFactory entityManagerFactory;

    private final Converter<Book, BookDocument> bookConverter;

    @Bean
    public ItemReader<Book> bookReader() {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookItemReader")
                .queryString("from Book")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookDocument> bookProcessor() {
        return bookConverter::convert;
    }


    @Bean
    public MongoItemWriter<BookDocument> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookDocument>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Step bookMigration(ItemReader<Book> bookReader, ItemWriter<BookDocument> bookWriter,
                              ItemProcessor<Book, BookDocument> bookProcessor) {
        return new StepBuilder("booksMigration", jobRepository)
                .<Book, BookDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
               .build();
    }
}

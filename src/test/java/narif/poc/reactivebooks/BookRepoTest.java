package narif.poc.reactivebooks;

import narif.poc.reactivebooks.config.DataConfig;
import narif.poc.reactivebooks.model.Book;
import narif.poc.reactivebooks.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(DataConfig.class)
@Testcontainers
public class BookRepoTest {

    @Container
    static PostgreSQLContainer<?> postgresql =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"))
                    .withInitScript("test.sql");

    @Autowired
    BookRepository bookRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", BookRepoTest::r2dbcUrl);
        registry.add("spring.r2dbc.username", postgresql::getUsername);
        registry.add("spring.r2dbc.password", postgresql::getPassword);
        registry.add("spring.flyway.url", postgresql::getJdbcUrl);
    }


    private static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                postgresql.getContainerIpAddress(),
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresql.getDatabaseName());
    }

    @Test
    @DisplayName("Given a book, should insert it into the DB.")
    public void shouldInsertBook(){
        final var book = createBook();
        StepVerifier.create(bookRepository.save(book))
                .expectNextMatches(book1 -> book1.id()!=null)
                .verifyComplete();
    }

    @Test
    @DisplayName("given a isbn and price, should update the price")
    public void shouldUpdatePrice(){
        final var book = createBook();
        final var savedBook = bookRepository.save(book).block();
        StepVerifier.create(bookRepository.setPriceForIsbn(30.5, savedBook.isbn()))
                .expectNext(1)
                .verifyComplete();
    }

    private static Book createBook() {
        return new Book(null, "ISBN-1", "name01", "authorname01", 26.5, 5, null, null, 0);
    }
}

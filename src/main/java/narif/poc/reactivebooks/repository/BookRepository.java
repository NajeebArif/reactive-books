package narif.poc.reactivebooks.repository;

import narif.poc.reactivebooks.model.Book;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Modifying
    @Query("UPDATE books SET price = :price where isbn = :isbn")
    Mono<Integer> setPriceForIsbn(Double price, String isbn);
}

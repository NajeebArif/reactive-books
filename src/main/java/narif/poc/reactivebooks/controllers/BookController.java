package narif.poc.reactivebooks.controllers;

import jakarta.validation.Valid;
import narif.poc.reactivebooks.model.Book;
import narif.poc.reactivebooks.model.BookDTO;
import narif.poc.reactivebooks.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public Mono<BookDTO> createBook(@RequestBody @Valid Mono<BookDTO> bookDTO){
        return bookDTO.map(Book::of)
                .flatMap(bookRepository::save)
                .map(BookDTO::of)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping
    public Flux<BookDTO> fetchAllBooks(){
        return bookRepository.findAll()
                .map(BookDTO::of)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PutMapping("/{isbn}")
    public Mono<Integer> updatePrice(@PathVariable String isbn, @RequestParam Double price ){
        return bookRepository.setPriceForIsbn(price, isbn);
    }
}

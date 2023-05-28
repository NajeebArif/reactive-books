package narif.poc.reactivebooks.controllers;

import jakarta.validation.Valid;
import narif.poc.reactivebooks.model.BookDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("books")
public class BookController {

    @PostMapping
    public Mono<BookDTO> createBook(@RequestBody @Valid Mono<BookDTO> bookDTO){
        return bookDTO;
    }
}

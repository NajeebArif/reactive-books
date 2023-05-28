package narif.poc.reactivebooks.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/greet")
public class GreetController {

    @GetMapping
    public Mono<String> greet(){
        return Mono.just("Hello World");
    }
}

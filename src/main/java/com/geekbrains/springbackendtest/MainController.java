package com.geekbrains.springbackendtest;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {
    // GET http://localhost:8189/app/hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, User!!!";
    }

    // GET http://localhost:8189/app/sum?a=10&second_number=20
    @GetMapping("/sum")
    public Integer sumNumbers(@RequestParam Integer a, @RequestParam(name = "second_number") Integer secondNumber) {
        return a + secondNumber;
    }

    // GET http://localhost:8189/app/greetings?[name=John]&[surname=Johnson]
    @GetMapping("/greetings")
    public String greetings(@RequestParam(required = false) String name, @RequestParam(required = false) String surname) {
        StringBuilder outMessage = new StringBuilder("Hello, ");
        if (name == null && surname == null) {
            outMessage.append("Stranger");
        } else {
            outMessage.append(name != null ? name : "Unknown");
            outMessage.append(" ");
            outMessage.append(surname != null ? surname : "Unknown");
        }
        outMessage.append("!!!");
        return outMessage.toString();
    }

    // GET http://localhost:8189/app/check_params?a=10&b=John&c=1000...
    @GetMapping("/check_params")
    public String showMyRequestParams(@RequestParam Map<String, String> params) {
        return params.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining("\n"));
    }

    @GetMapping("/product")
    public Product showProduct() {
        Product product = new Product(1L, "Bread", 100);
        return product;
    }

    @PostMapping("/saveNewProduct")
    public String showProduct(@RequestBody Product product) {
        return product.getTitle() + " received";
    }
}

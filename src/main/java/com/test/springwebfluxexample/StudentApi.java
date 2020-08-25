package com.test.springwebfluxexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@RestController
@RequestMapping("/student")
public class StudentApi {

    private StudentRepo studentRepo;

    public StudentApi(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> get() {
        return studentRepo.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    private Mono<Student> getEmployeeById(@PathVariable String id) {
        return studentRepo.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Student student) {
        studentRepo.save(student).subscribe();
    }
}

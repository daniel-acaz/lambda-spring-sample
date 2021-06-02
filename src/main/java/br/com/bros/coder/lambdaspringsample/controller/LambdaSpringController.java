package br.com.bros.coder.lambdaspringsample.controller;

import br.com.bros.coder.lambdaspringsample.model.LambdaSpringModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/lambda-spring")
public class LambdaSpringController {

    private static final Random RANDOM = new Random(999999999);

    private static final List<LambdaSpringModel> LAMBDA_SPRING_VALUES = new ArrayList<>(Arrays.asList(
                    LambdaSpringModel.builder()
                            .id(RANDOM.nextLong())
                            .name("Value 1")
                            .description("Spring Lambda Value 1")
                            .active(true)
                            .build(),
                    LambdaSpringModel.builder()
                            .id(RANDOM.nextLong())
                            .name("Value 2")
                            .description("Spring Lambda Value 2")
                            .active(true)
                            .build(),
                    LambdaSpringModel.builder()
                            .id(RANDOM.nextLong())
                            .name("Value 3")
                            .description("Spring Lambda Value 3")
                            .active(false)
                            .build()
                    ));

    @GetMapping
    public ResponseEntity<?> getValues() {
        return ResponseEntity.ok(LAMBDA_SPRING_VALUES);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getValueById(@PathVariable Long id) {
        try {
            LambdaSpringModel model = LAMBDA_SPRING_VALUES.stream()
                    .filter(value -> value.getId().equals(id))
                    .findFirst()
                    .orElseThrow(Exception::new);
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteValueById(@PathVariable Long id) {
        if (LAMBDA_SPRING_VALUES.removeIf(value -> value.getId().equals(id)))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> createValue(@RequestBody LambdaSpringModel model) {
        model.setId(RANDOM.nextLong());
        LAMBDA_SPRING_VALUES.add(model);
        return ResponseEntity.created(URI.create("/".concat(model.getId().toString()))).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateValue(@PathVariable Long id, @RequestBody LambdaSpringModel body) {
        try {
            LambdaSpringModel model = LAMBDA_SPRING_VALUES.stream()
                    .filter(value -> value.getId().equals(id))
                    .findAny()
                    .orElseThrow(Exception::new);
            model.setActive(body.getActive());
            model.setDescription(body.getDescription());
            model.setName(body.getName());
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package br.com.bros.coder.lambdaspringsample.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LambdaSpringModel {

    private Long id;
    private String name;
    private String description;
    private Boolean active;
}

package com.example.AdminXpert.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UpdateRequestDTO {

    @JsonProperty("use_case")  // Ensure this field is correctly serialized as "use_case"
    private String useCase;

    @JsonProperty("query")
    private String query;

    @JsonProperty("params")
    private List<Object> params;
}

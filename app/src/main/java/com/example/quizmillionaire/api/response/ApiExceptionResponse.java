package com.example.quizmillionaire.api.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {
    private String status;
    private String message;
    private List<String> exceptions;
}

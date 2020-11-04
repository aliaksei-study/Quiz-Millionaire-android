package com.example.quizmillionaire.api.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiExceptionResponse {
    private String status;
    private String message;
    private List<String> exceptions;
}

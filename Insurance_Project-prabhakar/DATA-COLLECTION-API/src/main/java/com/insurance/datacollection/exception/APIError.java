package com.insurance.datacollection.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIError {

    private String message;
    private String code;
}

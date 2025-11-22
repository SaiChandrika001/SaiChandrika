package com.sts.admin.Exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class APIError {

    private String message;

    private String code;

    public APIError(String message, String code) {
        this.message = message;
        this.code = code;
    }
}

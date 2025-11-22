package com.number.binding;
import java.time.LocalDate;

import com.number.entity.Citizens;

import lombok.Data;

@Data
public class CitizenResponse {

    private Citizens citizen;
    private String status;
    private String message;
   
}

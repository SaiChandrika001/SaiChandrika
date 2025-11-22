package com.insurance.ed.binding;

import lombok.Data;

@Data
public class ChildRequest {
    private Long caseNumber;

    private String childrenName;

    private Integer childrenAge;

    private Long childrenSSN;
}

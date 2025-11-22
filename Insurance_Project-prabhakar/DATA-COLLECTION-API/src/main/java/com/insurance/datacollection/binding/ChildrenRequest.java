package com.insurance.datacollection.binding;

import lombok.Data;

import java.util.List;

@Data
public class ChildrenRequest {

    private Long caseNumber;

    private List<ChildRequest> children;}

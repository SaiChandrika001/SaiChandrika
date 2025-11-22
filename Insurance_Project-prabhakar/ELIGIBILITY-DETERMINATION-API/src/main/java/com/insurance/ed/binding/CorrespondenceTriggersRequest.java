package com.insurance.ed.binding;

import lombok.Data;

@Data
public class CorrespondenceTriggersRequest {

    private Long caseNumber;

    private byte[] coPdf = null;

    private String triggerStatus = "PENDING";

}

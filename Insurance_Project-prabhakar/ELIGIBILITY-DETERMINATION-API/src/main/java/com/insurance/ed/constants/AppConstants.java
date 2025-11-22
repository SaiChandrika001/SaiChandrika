package com.insurance.ed.constants;

public class AppConstants {

    private AppConstants() {
    }

    //Plan Names
    public static final String SNAP = "SNAP";

    public static final String CCAP = "CCAP";

    public static final String MEDICAID = "MEDICAID";

    public static final String MEDICARE = "MEDICARE";

    public static final String NJW = "NJW";

    //Plan status
    public static final String APPROVED = "APPROVED";

    public static final String DENIED = "DENIED";

    //Trigger status
    public static final String PENDING = "PENDING";

    //Denial Messages
    public static final String HIGH_INCOME = "Income is greater than $300";

    public static final String AGE_NOT_MATCHED = "Age is not matched";

    public static final String CHILD_CONDITION_MISMATCH = "Children condition is mismatched";

    public static final String RULES_NOT_SATISFIED = "Rules not satisfied";

}

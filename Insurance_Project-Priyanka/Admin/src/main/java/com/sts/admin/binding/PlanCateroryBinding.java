package com.sts.admin.binding;

import lombok.Data;

@Data
public class PlanCateroryBinding {

    private String planCategoryName;

    private String activeSw;

	public String getPlanCategoryName() {
		return planCategoryName;
	}

	public void setPlanCategoryName(String planCategoryName) {
		this.planCategoryName = planCategoryName;
	}

	public String getActiveSw() {
		return activeSw;
	}

	public void setActiveSw(String activeSw) {
		this.activeSw = activeSw;
	}
}


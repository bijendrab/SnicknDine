package com.wityo.modules.product.model;

public class SubCategory {
    private Long id;
    private String subCategoryName;
    private String subCategoryType;
    private int sequence;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getSubCategoryType() {
		return subCategoryType;
	}
	public void setSubCategoryType(String subCategoryType) {
		this.subCategoryType = subCategoryType;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
    
}

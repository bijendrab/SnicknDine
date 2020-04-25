package com.wityo.modules.product.model;

import java.util.Set;

public class AddOnProfile {
    private String profileId;
    private String profileName;
    private Set<AddOnItems> customItems;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Set<AddOnItems> getCustomItems() {
        return customItems;
    }

    public void setCustomItems(Set<AddOnItems> customItems) {
        this.customItems = customItems;
    }
}

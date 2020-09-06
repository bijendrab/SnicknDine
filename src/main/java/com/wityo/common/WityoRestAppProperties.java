package com.wityo.common;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class WityoRestAppProperties {
    @NotNull
    private String wityoUserAppUrl;
    private String wityoRestSslKeyStore;

    public String getWityoUserAppUrl() {
        return wityoUserAppUrl;
    }

    public void setWityoUserAppUrl(String wityoUserAppUrl) {
        this.wityoUserAppUrl = wityoUserAppUrl;
    }

    public String getWityoRestSslKeyStore() {
        return wityoRestSslKeyStore;
    }

    public void setWityoRestSslKeyStore(String wityoRestSslKeyStore) {
        this.wityoRestSslKeyStore = wityoRestSslKeyStore;
    }
}

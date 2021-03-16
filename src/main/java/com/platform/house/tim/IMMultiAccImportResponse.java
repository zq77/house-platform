package com.platform.house.tim;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IMMultiAccImportResponse extends IMActionResponse {

    @JsonProperty("FailAccounts")
    private List<String> FailAccounts;


    public List<String> getFailAccounts() {
        return FailAccounts;
    }

    public void setFailAccounts(List<String> failAccounts) {
        FailAccounts = failAccounts;
    }
}

package com.platform.house.tim;

public class TencentIMConfig {
    private String sdkAppid;
    private String jnisigcheckLibPath;
    private String privateKeyPath;
    private String privateKey;
    private String defaultImAdminAccount;

    public String getSdkAppid() {
        return sdkAppid;
    }

    public void setSdkAppid(String sdkAppid) {
        this.sdkAppid = sdkAppid;
    }

    public String getJnisigcheckLibPath() {
        return jnisigcheckLibPath;
    }

    public void setJnisigcheckLibPath(String jnisigcheckLibPath) {
        this.jnisigcheckLibPath = jnisigcheckLibPath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getDefaultImAdminAccount() {
        return defaultImAdminAccount;
    }

    public void setDefaultImAdminAccount(String defaultImAdminAccount) {
        this.defaultImAdminAccount = defaultImAdminAccount;
    }
}

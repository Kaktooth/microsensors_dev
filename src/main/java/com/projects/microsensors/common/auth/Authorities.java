package com.projects.microsensors.common.auth;

public enum Authorities {
    USER(0), ADMIN(1);

    private final Integer numVal;

    Authorities(Integer numVal) {
        this.numVal = numVal;
    }

    public Integer getNumVal() {
        return numVal;
    }
}

package com.jmhreif.sdnaurajavaversions.domain;

public abstract class Delta {
    private String name, docURL, status;

    public Delta(String name, String docURL, String status) {
        this.name = name;
        this.docURL = docURL;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDocURL() {
        return docURL;
    }

    public String getStatus() {
        return status;
    }
}

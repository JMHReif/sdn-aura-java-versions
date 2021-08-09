package com.jmhreif.sdnaurajavaversions.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class Module extends Delta {
    @Id
    @GeneratedValue
    private final Long neoId;

    @Relationship("HAS_DELTA")
    private List<Package> packages;

    public Module(String name, String docURL, String status, Long neoId) {
        super(name, docURL, status);
        this.neoId = neoId;
    }

    public Long getNeoId() {
        return neoId;
    }

    public List<Package> getPackages() {
        return packages;
    }
}

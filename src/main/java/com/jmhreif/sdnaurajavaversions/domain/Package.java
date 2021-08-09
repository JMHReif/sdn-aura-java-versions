package com.jmhreif.sdnaurajavaversions.domain;

import com.jmhreif.sdnaurajavaversions.domain.Delta;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Package extends Delta {
    @Id
    @GeneratedValue
    private final Long neoId;

    public Package(String name, String docURL, String status, Long neoId) {
        super(name, docURL, status);
        this.neoId = neoId;
    }

    public Long getNeoId() {
        return neoId;
    }
}

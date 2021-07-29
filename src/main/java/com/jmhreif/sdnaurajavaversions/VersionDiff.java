package com.jmhreif.sdnaurajavaversions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class VersionDiff {
    @Id
    @GeneratedValue
    private final Long neoId;

    private String fromVendor, toVendor, fromVersion, toVersion;

    @Relationship(value = "FROM_NEWER", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties({"olderVersionDiffs", "newerVersionDiffs"})
    JavaVersion newerVersion;

    @Relationship(value = "FROM_OLDER",direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties({"olderVersionDiffs", "newerVersionDiffs"})
    JavaVersion olderVersion;

    public VersionDiff(Long neoId, String fromVendor, String toVendor, String fromVersion, String toVersion) {
        this.neoId = neoId;
        this.fromVendor = fromVendor;
        this.toVendor = toVendor;
        this.fromVersion = fromVersion;
        this.toVersion = toVersion;
    }

    public Long getNeoId() {
        return neoId;
    }

    public String getFromVendor() {
        return fromVendor;
    }

    public String getToVendor() {
        return toVendor;
    }

    public String getFromVersion() {
        return fromVersion;
    }

    public String getToVersion() {
        return toVersion;
    }

    public JavaVersion getNewerVersion() {
        return newerVersion;
    }

    public JavaVersion getOlderVersion() {
        return olderVersion;
    }
}

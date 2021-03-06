package com.jmhreif.sdnaurajavaversions.domain;

import com.jmhreif.sdnaurajavaversions.domain.Module;
import com.jmhreif.sdnaurajavaversions.domain.Package;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
public class VersionDiff {
    @Id
    @GeneratedValue
    private final Long neoId;

    private String fromVendor, toVendor, fromVersion, toVersion;

    @Relationship("HAS_DELTA")
    private List<Module> modules;

    @Relationship("HAS_DELTA")
    private List<Package> packages;

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

    public List<Module> getModules() {
        return modules;
    }

    public List<Package> getPackages() {
        return packages;
    }
}

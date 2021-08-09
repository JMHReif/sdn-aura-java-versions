package com.jmhreif.sdnaurajavaversions.domain;

import java.util.List;

public interface VersionDiffProjection {
    String getFromVersion();
    String getToVersion();
    List<ModuleProjection> getModules();
    List<PackageProjection> getPackages();

    interface ModuleProjection {
        String getName();
    }

    interface PackageProjection {
        String getName();
    }
}

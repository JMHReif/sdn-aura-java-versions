package com.jmhreif.sdnaurajavaversions.controller;

import com.jmhreif.sdnaurajavaversions.domain.VersionDiffProjection;
import com.jmhreif.sdnaurajavaversions.repository.VersionDiffRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diffs")
public class VersionDiffController {
    private final VersionDiffRepo versionDiffRepo;

    public VersionDiffController(VersionDiffRepo versionDiffRepo) {
        this.versionDiffRepo = versionDiffRepo;
    }

    @GetMapping()
    public Iterable<VersionDiffProjection> findAllVersionDiffs() { return versionDiffRepo.findVersionDiffs(); }
}

package com.jmhreif.sdnaurajavaversions.repository;

import com.jmhreif.sdnaurajavaversions.domain.VersionDiff;
import com.jmhreif.sdnaurajavaversions.domain.VersionDiffProjection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface VersionDiffRepo extends Neo4jRepository<VersionDiff, Long> {
    @Query("MATCH (d:VersionDiff)-[r:HAS_DELTA]->(delta)" +
            "RETURN d, collect(r), collect(delta);")
    Iterable<VersionDiffProjection> findVersionDiffs();
}

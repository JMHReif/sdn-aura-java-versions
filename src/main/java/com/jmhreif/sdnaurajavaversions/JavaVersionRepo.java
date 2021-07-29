package com.jmhreif.sdnaurajavaversions;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface JavaVersionRepo extends Neo4jRepository<JavaVersion, String> {

    @Query("MATCH (j:JavaVersion)-[r:FROM_NEWER]->(d:VersionDiff) RETURN j, collect(r), collect(d);")
    Iterable<JavaVersionProjection> findJavaVersions();
}

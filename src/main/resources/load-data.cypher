//Github project source: https://raw.githubusercontent.com/marchof/java-almanac/

//Setup:
CREATE INDEX java_version FOR (j:JavaVersion) ON (j.version);
CREATE INDEX version_diff FOR (v:VersionDiff) ON (v.fromVersion, v.toVersion);

//Queries:
//1) Load Java versions, along with related sources, features, and refs
WITH [1.0,1.1,1.2,1.3,1.4,5,6,7,8,9,10,11,12,13,14,15,16,17,18] as versions
CALL apoc.periodic.iterate('UNWIND $versions as version RETURN version',
'CALL apoc.load.json("https://raw.githubusercontent.com/marchof/java-almanac/master/site/data/jdk/versions/"+version+".json")
YIELD value
MERGE (j:JavaVersion {version: value.version})
 ON CREATE SET j.name = value.name, j.codeName = value.codename,
 j.gaDate = date(apoc.date.convertFormat(value.ga,"yyyy/MM/dd","iso_date")),
 j.status = value.status, j.bytecode = value.bytecode, j.vmSpec = value.documentation.vm,
 j.languageSpec = value.documentation.lang, j.apiSpec = value.documentation.api
WITH value, j
WHERE value.eol IS NOT NULL
WITH value, j, trim(split(value.eol, "(")[0]) as dateString
WITH value, j, dateString, split(dateString,"/") as dateParts
WITH value, j, dateString, (CASE
    WHEN size(dateString) = 7 THEN date({year: toInteger(dateParts[0]), month: toInteger(dateParts[1])})
    WHEN size(dateString) = 10 THEN date({year: toInteger(dateParts[0]), month: toInteger(dateParts[1]), day: toInteger(dateParts[2])})
    ELSE date(dateString)
    END) as eolDate
 SET j.eolDate = eolDate
WITH value, j
WHERE value.scm IS NOT NULL
UNWIND value.scm as scm
MERGE (s:SourceCode {type: scm.type})
 ON CREATE SET s.sourceURL = scm.url
MERGE (j)-[r:MANAGED_BY]-(s)
WITH value, j
WHERE value.features IS NOT NULL
UNWIND value.features as feature
MERGE (f:Feature {title: feature.title})
 ON CREATE SET f.category = feature.category
WITH value, j, feature, f
CALL {
    WITH f, feature
    UNWIND feature.refs as ref
    MERGE (r:Reference {id: ref.identifier})
     ON CREATE SET r.type = ref.type
    MERGE (f)-[r3:HAS]->(r)
    RETURN r
}
WITH value, j, f
MERGE (j)-[r2:INCLUDES]->(f)',
{batchSize: 50, iterateList:false, params:{versions:versions}})
YIELD batches, total, timeTaken, committedOperations, failedOperations, failedBatches , retries, errorMessages , batch , operations, wasTerminated
RETURN batches, total, timeTaken, committedOperations, failedOperations, failedBatches , retries, errorMessages , batch , operations, wasTerminated;
//Total nodes: 237
//Total rels: 506

//2) Load Java version diffs for each version
CALL apoc.periodic.iterate('MATCH (j:JavaVersion)
    WHERE toFloat(j.version) >= 1.1
    WITH j.version as version ORDER BY toFloat(version)
    WITH collect(version) as list
    UNWIND list as startVersion
    WITH startVersion, list[0..apoc.coll.indexOf(list, startVersion)] as prevList
    WITH startVersion, prevList
    WHERE size(prevList) > 0
    UNWIND prevList as prevVersion
    RETURN startVersion, prevVersion',
'CALL apoc.load.json("https://raw.githubusercontent.com/marchof/java-almanac/master/site/data/jdk/versions/"+startVersion+"/apidiff/"+prevVersion+".json")
YIELD value
MATCH (start:JavaVersion {version: startVersion})
MATCH (prev:JavaVersion {version: prevVersion})
WITH value, start, prev
MERGE (d:VersionDiff {fromVersion: value.base.version, toVersion: value.target.version})
 ON CREATE SET d.fromVendor = value.base.vendor, d.toVendor = value.target.vendor
MERGE (start)-[r:FROM_NEWER]->(d)<-[r2:FROM_OLDER]-(prev)
RETURN count(*)', {batchSize: 500, iterateList:false})
YIELD batches, total, timeTaken, committedOperations, failedOperations, failedBatches , retries, errorMessages , batch , operations, wasTerminated
RETURN batches, total, timeTaken, committedOperations, failedOperations, failedBatches , retries, errorMessages , batch , operations, wasTerminated;
//Total nodes: 390
//Total rels: 1118
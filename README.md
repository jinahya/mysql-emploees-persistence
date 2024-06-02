# mysql-employees-persistence

[![Java CI with Maven](https://github.com/jinahya/mysql-emploees-persistence/actions/workflows/maven.yml/badge.svg)](https://github.com/jinahya/mysql-emploees-persistence/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jinahya_mysql-emploees-persistence&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jinahya_mysql-emploees-persistence)

[Jakarta Persistence] for the [Employees Sample Database](https://dev.mysql.com/doc/employee/en/).

---

### API Versions

| platform / framework                        | version                             | notes               |
|---------------------------------------------|-------------------------------------|---------------------|
| [Jakarta Persistence]                       | [Jakarta Persistence 3.2]           |                     |
| [Jakarta Persistence]                       | [Jakarta Persistence 3.1]           |                     |
| [Spring Boot Dependency Versions (current)] | `:jakarta.persistence-api:3.1.0`    |                     |
| [Spring Boot Dependency Versions (3.2.6)]   | `:jakarta.persistence-api:3.1.0`    |                     |
| [Jakarta EE Platform 11]                    | `:jakarta.persistence-api:3.2.0-M2` | (Under development) |
| [Jakarta EE Platform 10]                    | `:jakarta.persistence-api:3.1.0`    |                     |

---

## JDK

The [latest LTS](https://www.oracle.com/java/technologies/java-se-support-roadmap.html) is required to build/run this
module.

```text
$ grep \<maven\\.compiler\\. pom.xml
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>${maven.compiler.source}</maven.compiler.target>
    <maven.compiler.release>${maven.compiler.target}</maven.compiler.release>
    <maven.compiler.testSource>${maven.compiler.source}</maven.compiler.testSource>
    <maven.compiler.testTarget>${maven.compiler.testSource}</maven.compiler.testTarget>
    <maven.compiler.testRelease>${maven.compiler.testTarget}</maven.compiler.testRelease>
```

## Apache Maven

* [Introduction to the Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
* [Introduction to Build Profiles](https://maven.apache.org/guides/introduction/introduction-to-profiles.html)

### Directory Layout

```text
$ tree -d -L 2 --charset=ascii src
src
|-- main
|   |-- java
|   |-- java-application
|   |-- java-application-jakarta-ee
|   |-- java-application-spring-boot
|   |-- java-persistence
|   |-- java-querydsl
|   |-- resources
|   |-- resources-application
|   |-- resources-application-jakarta--ee
|   |-- resources-application-spring-boot
|   |-- resources-persistence
|   `-- resources-querydsl
`-- test
    |-- java
    |-- java-application
    |-- java-application-jakarta-ee
    |-- java-framework-quarkus
    |-- java-framework-spring-boot
    |-- java-persistence
    |-- java-querydsl
    |-- resources
    |-- resources-application
    |-- resources-application-jakarta-ee
    |-- resources-application-spring-boot
    |-- resources-framework-quarkus
    |-- resources-persistence
    |-- resources-querydsl
    `-- sql
```

### Profiles

* &#x2705; - should be selected
* &#x2713; - either one should be selected

| Profiles                | Java | Jakarta Persistence | Querydsl | Jakarta EE | Spring Boot | Notes   |
|-------------------------|------|---------------------|----------|------------|-------------|---------|
| application             |      |                     |          | &#x2705;   | &#x2705;    |         |
| application-jakarta-ee  |      |                     |          | &#x2705;   |             |         |
| application-spring-boot |      |                     |          |            | &#x2705;    |         |
| failsafe                |      | &#x2705;            | &#x2705; | &#x2705;   | &#x2705;    | for ITs |
| persistence             |      | &#x2705;            | &#x2705; |            |             |         |
| persistence-eclipselink |      | &#x2713;            | &#x2713; |            |             |         |
| persistence-hibernate   |      | &#x2713;            | &#x2713; |            |             |         |
| querydsl                |      |                     | &#x2705; |            |             |         |
| querydsl-5              |      |                     | &#x2713; |            |             |         |
| querydsl-6              |      |                     | &#x2713; |            |             |         |

e.g.

```shell
## Java
$ mvn clean test

## Jakarta Persistence + EclipseLink
$ mvn -Pfailsafe,persistence,persistence-eclipselink clean verify 

## Jakarta Persistence + Hibernate + Querydsl
$ mvn -Pfailsafe,persistence,persistence-hibernate,querydsl,querydsl-5 clean verify 

## Spring Data JPA 
$ mvn -Pfailsafe,application,application-spring-boot clean verify 
```

---

## Docker

### Build the image

```shell
$ sh ./.docker.build.sh
```

We don't have to repeat this job unless the `Dockerfile` changed.

### Run the image as a container

```shell
$ sh ./.docker.run.sh
```

We'd better to wait a few seconds for the MySQL engine is fully up and running.

### Connect to the running container

```shell
$ sh ./.docker.connect.sh
```

### Stop the container

```shell
$ sh ./.docker.stop.sh
```

---

## Links

### [Employees Sample Database](https://dev.mysql.com/doc/employee/en/)

* [3 Installation](https://dev.mysql.com/doc/employee/en/employees-installation.html)
    * [Employees DB on GitHub](https://github.com/datacharmer/test_db)
* [5 Employees Structure](https://dev.mysql.com/doc/employee/en/sakila-structure.html)

### [Jakarta Annotations](https://jakarta.ee/specifications/annotations/)

#### [Jakarta Annotations 3.0](https://jakarta.ee/specifications/annotations/3.0/)

* [Jakarta Annotations 3.0 Specification Document (PDF)](https://jakarta.ee/specifications/annotations/3.0/annotations-spec-3.0.pdf)
* [Jakarta Annotations 3.0 Specification Document (HTML)](https://jakarta.ee/specifications/annotations/3.0/annotations-spec-3.0)
* [Jakarta Annotations 3.0 Javadoc](https://jakarta.ee/specifications/annotations/3.0/apidocs/)

### [Jakarta Persistence](https://jakarta.ee/specifications/persistence/)

#### [Jakarta Persistence 3.2](https://jakarta.ee/specifications/persistence/3.2/)

* [Jakarta Persistence 3.2 Specification Document](https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m2)
* [Jakarta Persistence 3.2 Javadoc](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/module-summary.html)

#### [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/)

* [Jakarta Persistence 3.1 Specification Document](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1)
* [Jakarta Persistence 3.1 Javadoc](https://jakarta.ee/specifications/persistence/3.1/apidocs)

### [EclipseLink](https://eclipse.dev/eclipselink/)

*
* [EclipseLink Documentation Center](https://eclipse.dev/eclipselink/documentation/)

#### [EclipseLink JPA](https://eclipse.dev/eclipselink/#jpa)

* [EclipseLink JPA Extensions Reference](https://eclipse.dev/eclipselink/documentation/4.0/jpa/extensions/jpa-extensions.html)
* [Bug 328730 - Query.setMaxResults(0) returns unexpected results](https://bugs.eclipse.org/bugs/show_bug.cgi?id=328730)
* [Using Weaving](https://eclipse.dev/eclipselink/documentation/2.7/solutions/testingjpa004.htm)

#### [eclipse-ee4j/eclipselink](https://github.com/eclipse-ee4j/eclipselink)

### [Hibernate](https://hibernate.org/)

#### [Hibernate ORM](https://hibernate.org/orm/)

#### [hibernate/hibernate-orm](https://github.com/hibernate/hibernate-orm)

---

### [Querydsl](http://querydsl.com/)

* Apidocs
    * [com.querydsl:querydsl-core](https://javadoc.io/doc/com.querydsl/querydsl-core/latest/index.html)
    * [com.querydsl:querydsl-jpa](https://javadoc.io/doc/com.querydsl/querydsl-jpa/latest/index.html)
* [Querydsl Reference Guide](http://querydsl.com/static/querydsl/latest/reference/html/)
    * [2.1. Querying JPA](http://querydsl.com/static/querydsl/latest/reference/html/ch02.html#jpa_integration)

#### [OpenFeign/querydsl](https://github.com/OpenFeign/querydsl)

* [Why forking?](https://github.com/OpenFeign/querydsl#why-forking)
* [\[#274\] About this fork!](https://github.com/OpenFeign/querydsl/issues/274)

---

### [Spring](https://spring.io/)

#### [Spring Boot](https://spring.io/projects/spring-boot)

* [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
    * [Common Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
    * [Dependency Versions](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html)

#### [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

### [Jakarta EE](https://jakarta.ee/specifications/)

#### [Jakarta EE Platform](https://jakarta.ee/specifications/platform/)

#### [Jakarta EE Web Profile](https://jakarta.ee/specifications/webprofile/)

##### [Jakarta EE Web Profile 10](https://jakarta.ee/specifications/webprofile/10/)

* [Jakarta Web Profile 10 Specification Document](https://jakarta.ee/specifications/webprofile/10/jakarta-webprofile-spec-10.0)
* [Jakarta Web Profile 10 Javadoc](Jakarta Web Profile 10 Specification Document)
* [Jakarta EE 10 Compatible Implementations](https://jakarta.ee/compatibility/certification/10/)

#### [Jakarta EE Core Profile](https://jakarta.ee/specifications/coreprofile/)

### [Quarkus](https://quarkus.io/)

* [Using Hibernate ORM and Jakarta Persistence ](https://quarkus.io/guides/hibernate-orm)
* [Simplified Hibernate ORM with Panache](https://quarkus.io/guides/hibernate-orm-panache)

### [GitHub](https://github.com)

### [Bealdung](https://www.baeldung.com)

* [JPA Entity Graph](https://www.baeldung.com/jpa-entity-graph)

### Et cetera

### Miscellaneous

* [기간 영어로 (duration, period, time frame, span, length of time, interval, term, tenure 차이)](https://blog-ko.engram.us/duration/)
* [기타 영어로 (Etcetera (etc.), Miscellaneous, Additional, Non-specific, Assorted 차이)](https://blog-ko.engram.us/etcetera/)

---

[Jakarta Persistence]: https://jakarta.ee/specifications/persistence/

[Jakarta Persistence 3.2]: https://jakarta.ee/specifications/persistence/3.2/

[Jakarta Persistence 3.1]: https://jakarta.ee/specifications/persistence/3.1/

[Spring Boot Dependency Versions (current)]: https://docs.spring.io/spring-boot/appendix/dependency-versions/coordinates.html

[Spring Boot Dependency Versions (3.2.6)]: https://docs.spring.io/spring-boot/docs/3.2.6/reference/html/dependency-versions.html#appendix.dependency-versions

[Jakarta EE Platform 11]: https://jakarta.ee/specifications/platform/11/

[Jakarta EE Platform 10]: https://jakarta.ee/specifications/platform/10/
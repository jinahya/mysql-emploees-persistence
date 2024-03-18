# mysql-employees-persistence

[![Java CI with Maven](https://github.com/jinahya/mysql-emploees-persistence/actions/workflows/maven.yml/badge.svg)](https://github.com/jinahya/mysql-emploees-persistence/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jinahya_mysql-emploees-persistence&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jinahya_mysql-emploees-persistence)

Jakarta Persistence for the [Employees Sample Database](https://dev.mysql.com/doc/employee/en/).

---

## Apache Maven

* [Introduction to the Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
* [Introduction to Build Profiles](https://maven.apache.org/guides/introduction/introduction-to-profiles.html)

```text
$ tree -d -L 2 --charset=ascii src
src
|-- main
|   |-- java
|   |-- java-framework
|   |-- java-framework-quarkus
|   |-- java-framework-spring
|   |-- java-persistence
|   |-- java-querydsl
|   |-- resources
|   |-- resources-framework
|   |-- resources-framework-quarkus    
|   |-- resources-framework-spring
|   |-- resources-persistence
|   `-- resources-querydsl
`-- test
    |-- java
    |-- java-framework
    |-- java-framework-quarkus
    |-- java-framework-spring
    |-- java-persistence
    |-- java-querydsl
    |-- resources
    |-- resources-framework
    |-- resources-framework-quarkus
    |-- resources-framework-spring
    |-- resources-persistence
    |-- resources-querydsl
    `-- sql
```

### Profiles

| Profiles                | Java | Jakarta Persistence | Querydsl | Spring Boot | Quarkus  | Notes |
|-------------------------|------|---------------------|----------|-------------|----------|-------|
| failsafe                |      | &#x2705;            | &#x2705; | &#x2705;    | &#x2705; |       |
| framework               |      |                     |          | &#x2705;    | &#x2705; |       |
| framework-quarkus       |      |                     |          |             | &#x2705; |       |
| framework-spring        |      |                     |          | &#x2705;    |          |       |
| persistence             |      | &#x2705;            | &#x2705; |             |          |       |
| persistence-eclipselink |      | &#x2713;            | &#x2713; |             |          |       |
| persistence-hibernate   |      | &#x2713;            | &#x2713; |             |          |       |
| querydsl                |      |                     | &#x2705; |             |          |       |
| querydsl-5              |      |                     | &#x2713; |             |          |       |
| querydsl-6              |      |                     | &#x2713; |             |          |       |

---

## Links

### [Employees Sample Database](https://dev.mysql.com/doc/employee/en/)

* [3 Installation](https://dev.mysql.com/doc/employee/en/employees-installation.html)
    * [Employees DB on GitHub](https://github.com/datacharmer/test_db)
* [5 Employees Structure](https://dev.mysql.com/doc/employee/en/sakila-structure.html)

### [Jakarta Persistence](https://jakarta.ee/specifications/persistence/)

#### [Jakarta Persistence 3.2](https://jakarta.ee/specifications/persistence/3.2/)

* [Jakarta Persistence 3.2 Specification Document](https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m2)
* [Jakarta Persistence 3.2 Javadoc](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/module-summary.html)

#### [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/)

* [Jakarta Persistence 3.1 Specification Document](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1)
* [Jakarta Persistence 3.1 Javadoc](https://jakarta.ee/specifications/persistence/3.1/apidocs)

### [EclipseLink](https://eclipse.dev/eclipselink/)

#### [EclipseLink JPA](https://eclipse.dev/eclipselink/#jpa)

* [Bug 328730 - Query.setMaxResults(0) returns unexpected results](https://bugs.eclipse.org/bugs/show_bug.cgi?id=328730)

#### [eclipse-ee4j/eclipselink](https://github.com/eclipse-ee4j/eclipselink)

### [Hibernate](https://hibernate.org/)

#### [Hibernate ORM](https://hibernate.org/orm/)

#### [hibernate/hibernate-orm](https://github.com/hibernate/hibernate-orm)

### [Querydsl](http://querydsl.com/)

* Apidocs
    * [com.querydsl:querydsl-core](https://javadoc.io/doc/com.querydsl/querydsl-core/latest/index.html)
    * [com.querydsl:querydsl-jpa](https://javadoc.io/doc/com.querydsl/querydsl-jpa/latest/index.html)
* [Querydsl Reference Guide](http://querydsl.com/static/querydsl/latest/reference/html/)
    * [2.1. Querying JPA](http://querydsl.com/static/querydsl/latest/reference/html/ch02.html#jpa_integration)

#### [OpenFeign/querydsl](https://github.com/OpenFeign/querydsl)

* [Why forking?](https://github.com/OpenFeign/querydsl#why-forking)
* [\[#274\] About this fork!](https://github.com/OpenFeign/querydsl/issues/274)

### [Spring Boot](https://spring.io/projects/spring-boot)

* [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
    * [Common Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
    * [Dependency Versions](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html)

### [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

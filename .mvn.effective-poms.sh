#!/bin/sh
#mvn -Ppersistence-provider-datanucleus help:effective-pom -Doutput=effective-pom-datanucleus.xml
#mvn -Ppersistence-provider-eclipselink help:effective-pom -Doutput=effective-pom-eclipselink.xml
#mvn -Ppersistence-provider-hibernate help:effective-pom -Doutput=effective-pom-hibernate.xml
mvn -Ppersistence,persistence-eclipselink,querydsl,querydsl-5 help:effective-pom -Doutput=effective-pom-querydsl-5.xml

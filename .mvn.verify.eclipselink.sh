#!/bin/sh
mvn -Pfailsafe,persistence,persistence-eclipselink,persistence-eclipselink-weaving clean verify

#!/bin/sh
mvn -Pfailsafe,persistence-provider-eclipselink clean verify

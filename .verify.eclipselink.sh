#!/bin/sh
mvn -Pfailsafe,persistence-provider-ecliseplink clean verify

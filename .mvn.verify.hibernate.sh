#!/bin/sh
mvn -Pfailsafe,persistence-provider-hibernate clean verify

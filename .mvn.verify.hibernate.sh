#!/bin/sh
mvn -Pfailsafe,persistence,persistence-hibernate clean verify

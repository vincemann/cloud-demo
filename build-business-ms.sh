#!/bin/bash
cd posting-service/ && mvn clean package dockerfile:build -DskipTests;cd .. ;cd comment-service/ && mvn clean package dockerfile:build -DskipTests; cd ..

#!/bin/sh

#java -cp app:app/lib/* discovery_service.DiscoveryServiceApplication

java $JAVA_OPTS -jar app.jar "--server.port=8080"
#!/bin/bash

# Stop all running Docker containers
docker stop $(docker ps -q)

# Remove all stopped Docker containers
docker rm $(docker ps -aq)

# Run your build script
./build-all.sh

# Start your Docker containers using docker-compose
docker-compose up -d

# Attach terminal to 'cli' container
docker attach cli

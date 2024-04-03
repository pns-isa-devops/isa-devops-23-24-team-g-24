#!/bin/bash

# Check if the container is already running
if [ "$(docker ps -q -f name=artifactory)" ]; then
    echo "Artifactory container is already running."
else
    # Check if there's a stopped container with the same name
    if [ "$(docker ps -aq -f status=exited -f name=artifactory)" ]; then
        # Run the container
        docker run artifactory
    else
        # Pull the image only if it's not already available
        if ! docker inspect releases-docker.jfrog.io/jfrog/artifactory-oss:7.47.14 &> /dev/null; then
            echo "Image not found locally. Attempting to pull..."
            docker pull releases-docker.jfrog.io/jfrog/artifactory-oss:7.47.14
        fi

        # Run the container
        docker run --name artifactory \
            -v /home/jfrog/artifactory/var/:/var/opt/jfrog/artifactory \
            -d -p 8001:8081 -p 8002:8082 \
            releases-docker.jfrog.io/jfrog/artifactory-oss:7.47.14
    fi
fi


# docker run --name artifactory -v /home/jfrog/artifactory/var/:/var/opt/jfrog/artifactory -d -p 8001:8081 -p 8002:8082 releases-docker.jfrog.io/jfrog/artifactory-oss:7.47.14

#!/usr/bin/env bash

echo "Compiling the NestJS Bank system within a multi-stage docker build"

docker build -t team-g-tcf-bank-service .

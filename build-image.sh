#!/bin/bash

docker image build \
  -t hnwj/hisnamewasjaan:1.7 \
  --build-arg JAR_FILE=target/hisnamewasjaan-1.7.jar \
  .

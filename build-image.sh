#!/bin/bash

docker image build \
  -t hnwj/hisnamewasjaan \
  --build-arg JAR_FILE=target/hisnamewasjaan-1.6.jar \
  .

#!/bin/bash

DOCKER_USER_HOME=/root

docker run \
  --name hisnamewasjaan \
  -p 8081:8080 \
  -it \
  --rm \
  hnwj/hisnamewasjaan \
#  /bin/bash

#  --detach \

#     --name string                    Assign a name to the container
# -p, --publish list                   Publish a container's port(s) to the host
# -i, --interactive                    Keep STDIN open even if not attached
# -t, --tty                            Allocate a pseudo-TTY
# -v, --volume list                    Bind mount a volume
# -d, --detach                         Run container in background and print container ID
# -e, --env list                       Set environment variables
#     --rm                             Automatically remove the container when it exits



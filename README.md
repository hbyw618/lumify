# Lumify

![Lumify Logo](web/war/src/main/webapp/img/lumify-logo.png?raw=true)

Lumify is an open source big data analysis and visualization platform. Please see [http://lumify.io](http://lumify.io) for more details and videos.

## Getting Started

To get started quickly, you can try out a hosted installation of Lumify, or download a virtual machine image with Lumify installed and pre-configured.

- [Try Lumify Now](http://lumify.io/try.html)
- [Watch Lumify Videos](https://www.youtube.com/playlist?list=PLDX7b-6_sNA7SCJw5rB9EF0TDpQyrO2XR)

## Quick Start

1. Install Docker per their instructions: [https://docs.docker.com/installation](https://docs.docker.com/installation/#installation)

1. Install node and npm per their instructions: [http://nodejs.org/](http://nodejs.org/)

1. Clone the Lumify repo:
    ```sh
    git clone https://github.com/lumifyio/lumify.git
    ```
    **_This will clone the repo to a `lumify` directory in your current working directory.  This absolute path will be referred to as `<cloned_repo_dir>` for the remainder of these steps._**

1. Install the Lumify npm dependencies:
    ```sh
    cd <cloned_repo_dir>/web/war/src/main/webapp
    npm install -g inherits bower grunt
    npm install -g grunt-cli
    ```

1. Update your hosts file:
    - Linux

        ```sh
        echo '127.0.0.1 lumify-dev' >> /etc/hosts
        ```
    - OS X

        ```sh
        echo "$(boot2docker ip 2>/dev/null) lumify-dev" >> /etc/hosts
        ```

1. Create the docker image:
    ```sh
    cd <cloned_repo_dir>
    docker/build-dev.sh
    ```

1. Run the docker image: (This will start ZooKeeper, HDFS, YARN, ElasticSearch, and RabbitMQ)
    ```sh
    docker/run-dev.sh
    ```

1. Run the web server. Choose one of the following:
   * [Run in the docker image](docker/README.md#docker-web-server)
   * [Run locally in an IDE](docs/ide.md#development-jetty-web-server)

See [docker/README.md](docker/) for more information on the docker dev image.

See [docs/developer.md](docs/developer.md) for more information on developing for Lumify.

See [datasets](datasets) for sample datasets to get started.

## Developing / Contributing

If you're a system administrator or developer looking to install your own instance of Lumify or do custom development,
please read our [Developer Guide](docs/developer.md).


## License

Copyright 2014 Altamira Technologies Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

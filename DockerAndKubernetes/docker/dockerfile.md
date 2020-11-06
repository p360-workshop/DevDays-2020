Welcome to the Basics of Docker.

You will learn how to run the most often used commands in day to day work with docker.


### Dockerfile

At the core of everything in Docker is the Dockerfile.

A dockerfile describes how to build a container, anything that should be included with it and any command it should execute when executed.

Look at the file `/Dockerfile`


```
FROM openjdk:10-jdk

CMD ["gradle"]

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_VERSION 4.9

ARG GRADLE_DOWNLOAD_SHA256=e66e69dce8173dd2004b39ba93586a184628bc6c28461bc771d6835f7f9b0d28
RUN set -o errexit -o nounset \
	&& echo "Downloading Gradle" \
	&& wget --no-verbose --output-document=gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
	\
	&& echo "Checking download hash" \
	&& echo "${GRADLE_DOWNLOAD_SHA256} *gradle.zip" | sha256sum --check - \
	\
	&& echo "Installing Gradle" \
	&& unzip gradle.zip \
	&& rm gradle.zip \
	&& mv "gradle-${GRADLE_VERSION}" "${GRADLE_HOME}/" \
	&& ln --symbolic "${GRADLE_HOME}/bin/gradle" /usr/bin/gradle \
	\
	&& echo "Adding gradle user and group" \
	&& groupadd --system --gid 1000 gradle \
	&& useradd --system --gid gradle --uid 1000 --shell /bin/bash --create-home gradle \
	&& mkdir /home/gradle/.gradle \
	&& chown --recursive gradle:gradle /home/gradle \
	\
	&& echo "Symlinking root Gradle cache to gradle Gradle cache" \
	&& ln -s /home/gradle/.gradle /root/.gradle

# Create Gradle volume
USER gradle
VOLUME "/home/gradle/.gradle"
WORKDIR /home/gradle

RUN set -o errexit -o nounset \
	&& echo "Testing Gradle installation" \
	&& gradle --version

```
### Building a container image

To build a docker container image from a Dockerbuild file run the following

` docker build . -t <ecr repo name>:<your name>`

To see image you have available in your machine run the following:

` docker images`


To push the image that you just built , run the following command:

`docker push <ecr repo name>:<your name>`

To remove an image that you just built, run the following command:

`docker rmi <ecr repo name>:<your name>`


To pull an image that you just pushed, run the following command:

`docker pull <ecr repo name>:<your name>`


### Launching a container

To launch a container in docker we specify the following

`docker run -d nginx`

The `-d` means run in detached mode.

This doesn't allow us to access our nginx instance though as we didn't expose it!

Let's try again:

`docker run -d -p 8081:80 nginx`

Now you should be able to access the basic html website with:

`curl localhost:8081`


To see currently running containers run:

`docker ps`

Note the container id from the command above. you will need this later

If you want to see the logs from the container run:

`docker logs <container id>` 


Run this command to kill the docker container

`docker kill <docker container id>`
 






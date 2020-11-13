## What will you build

You will learn how to run the most often used commands in day to day work with docker.


## Concept

At the core of everything in Docker is the Dockerfile.

A dockerfile describes how to build a container, anything that should be included with it and any command it should execute when executed.

Look at the file `/Dockerfile`


```
# Set the base image to Ubuntu
FROM ubuntu

# Update the repository
RUN apt-get update

# Install necessary tools
RUN apt-get install -y vim wget dialog net-tools nginx

# Remove the default Nginx configuration file
RUN rm -v /etc/nginx/nginx.conf

# Copy a configuration file from the current directory
ADD nginx.conf /etc/nginx/

RUN mkdir /etc/nginx/logs

# Add a sample index file
ADD index.html /www/data/

# Append "daemon off;" to the beginning of the configuration
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

# Create a runner script for the entrypoint
COPY runner.sh /runner.sh
RUN chmod +x /runner.sh

# Expose ports
EXPOSE 80

ENTRYPOINT ["/runner.sh"]

# Set the default command to execute
# when creating a new container
CMD ["nginx"]

```

## What will you need

Make sure you have code for the lab and you are in right path

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Clone the repo if you don't have.

`git clone https://github.com/p360-workshop/DevDays-2020.git`

Change your directory to following folder

`cd DevDays-2020\DockerAndKubernetes\lab-content\0-docker`


## Building a container image

To build a docker container image from a Dockerbuild file run the following

`docker build . -t 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:<firstname-lastname>`

To see image you have available in your machine run the following:

`docker images`


To push the image that you just built , run the following command:

`docker push 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:<firstname-lastname>`

To remove an image that you just built, run the following command:

`docker rmi 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:<firstname-lastname>`


To pull an image that you just pushed, run the following command:

`docker pull 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:<firstname-lastname>`


## Launching a container

To launch a container in docker we specify the following

`docker run -d -p 8081:80 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:<firstname-lastname>`

The `-d` means run in detached mode.

The `-p 8081:80` is for port mapping between host machine and container so that we can access the application outside of container


Now you should be able to access the basic html website with:

`curl localhost:8081`


To see currently running containers run:

`docker ps`

Note the container id from the command above. you will need this later

If you want to see the logs from the container run:

`docker logs <container id>` 


Run this command to kill the docker container

`docker kill <docker container id>`
 






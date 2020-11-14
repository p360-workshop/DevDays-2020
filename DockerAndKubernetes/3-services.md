# Services

## Concept

An abstract way to expose an application running on a set of Pods as a network service


## What will you need

Make sure you have code for the lab and you are in right path

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Clone the repo if you don't have it already

`git clone https://github.com/p360-workshop/DevDays-2020.git`

Change your directory to following folder

`cd DevDays-2020\DockerAndKubernetes\lab-content\3-services`


## Create Service

First things first, lets create a deployment that we will use to learn the various service types.

To do so run:

`kubectl create -f nginx-deployment.yml`

and make sure all is healthy:

`kubectl get deploy`

Now that we have a working deployment, lets expose it to the cluster so that other deployments can access it too.

`kubectl create -f clusterip-service.yml`

We can check if it was created with:

`kubectl get svc -o wide`

or if we want more information:

`kubectl describe svc clusterip-nginx-service`

## Validate

Lets see if we can hit the service from another pod running in the cluster:

Run another pod in the cluster

`kubectl run --image 840891909344.dkr.ecr.us-east-1.amazonaws.com/swissknife:latest swissknife`

Make sure that your pod is spun up

`kubectl get pods`


Enter into the pod using this:

`kubectl exec -it swissknife-78dcc876d5-ptphl bash`

Replace the pod name with yours from above command


Hit the service from this pod

curl http://clusterip-nginx-service


## Cleanup

Run this command to clean up the service and deployment at once. Make sure you are in right folder before you run this command. 

`kubectl delete -f- . -n <firstname-lastname>`


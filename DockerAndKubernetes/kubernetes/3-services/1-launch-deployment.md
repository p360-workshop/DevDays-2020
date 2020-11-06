First things first, lets create a deployment that we will use to learn the various service types.

To do so run:

`kubectl create -f nginx-deployment.yml`{{execute}}

and make sure all is healthy:

`kubectl get deploy`{{execute}}
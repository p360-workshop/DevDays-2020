Now that we have a working deployment, lets expose it to the cluster so that other deployments can access it too.

`kubectl create -f clusterip-service.yml`{{execute}}

We can check if it was created with:

`kubectl get svc -o wide`{{execute}}

or if we want more information:

`kubectl describe svc clusterip-nginx-service`{{execute}}
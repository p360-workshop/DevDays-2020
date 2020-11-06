What if we wanted to expose our service outside of the cluster? This is where NodePort comes in. NodePort is one the most often utilized service types in kubernetes.

Lets create one:

`kubectl create -f nodeport-service.yml`

We can check if it was created with:

`kubectl get svc -o wide`

or if we want more information:

`kubectl describe svc nodeport-nginx-service`

We can now access our service with:

`curl http://<NODEPORT-IP>`
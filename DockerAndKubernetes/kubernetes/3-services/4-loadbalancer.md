What if we wanted a single point of entry for our service from the oustide? For that we need a LoadBalancer type of service. 
If you are running on any of the major cloud providers it will be freely available for you, but if you are on-prem or in this case katacoda, then you need to make this functionality available.

Lets make katacoda LoadBalancer friendly:

`kubectl create -f cloudprovider.yml`

Once we have that we can create our service:

`kubectl create -f loadbalancer-service.yml`

We can check if it was created with:

`kubectl get svc -o wide`

or if we want more information:

`kubectl describe svc lb-nginx-service`

We can now access our service with:

`curl http://<EXTERNAL-IP>`

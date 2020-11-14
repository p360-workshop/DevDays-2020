# Ingress 



## Concept

Ingress gives you a way to route requests to services based on the request host or path, centralizing a number of services into a single entrypoint (or in this case, load balancer).



Ingress is split up into two main pieces. The first is an Ingress resource, which defines how you want requests routed to the backing services.

For example, a definition that defines an Ingress to handle requests for manish-rajkarnikar.p360.providerdataplatform.net  routes them to the Kubernetes services named nginx would look like:

```
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  name: demo-ingress
spec:
  rules:
  - host: manish-rajkarnikar.p360.providerdataplatform.net
    http:
      paths:
      - backend:
          serviceName: nginx
          servicePort: 80

```

image::lab-content/ingressoverview.jpeg[Caption="Ingress Overview"]


Here is where things seem to get confusing, though. Ingress on its own doesn't really do anything. You need something to listen to the Kubernetes API for Ingress resources and then handle requests that match them. This is where the second piece to the puzzle comes in — the Ingress Controller.

Ingress Controllers can technically be any system capable of reverse proxying,  the most common is Nginx, HA-Proxy, Traefik.


## What will you need

Make sure you have code for the lab and you are in right path. Skip this step if you already done this

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Open up terminal if a terminal is not open

`Top left menu button> Terminal > New Terminal`

Change directory to /root 

`cd /root`

Clone the repo if you don't have it already

`git clone https://github.com/p360-workshop/DevDays-2020.git`

You should already have a namespace created called `<firstname-lastname>` all lowercase. If you do not have this namespace yet, or you have deleted it, then please re-create it:

`kubectl create namespace <firstname-lastname>`

Set your default namespace so that you don't to specify namespace everytime. 

`kubectl config set-context --current --namespace=<firstname-lastname>`




## Creating Ingress

For purpose of this lab,  Ingress Controller has already been created. 


Change your directory to following folder

`cd /root/DevDays-2020/DockerAndKubernetes/lab-content/5-ingress/`


To test things out, you need to get your Ingress Definition.


Replace <firstname-lastname> in demo-ing.yaml with your firstname and lastname

`kubectl create -f demo-ing.yaml --namespace=<firstname-lastname>`

## Validation

Open a browser and go https://<firstname-lastname>.p360.providerdataplatform.net/

## Cleanup

`kubectl delete -f demo-ing.yaml --namespace=<firstname-lastname>`
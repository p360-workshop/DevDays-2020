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
![image](lab-content/ingressoverview.jpeg)


Here is where things seem to get confusing, though. Ingress on its own doesn't really do anything. You need something to listen to the Kubernetes API for Ingress resources and then handle requests that match them. This is where the second piece to the puzzle comes in — the Ingress Controller.

Ingress Controllers can technically be any system capable of reverse proxying,  the most common is Nginx, HA-Proxy, Traefik.


## What will you need

Make sure you have code for the lab and you are in right path

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Clone the repo if you don't have it already

`git clone https://github.com/p360-workshop/DevDays-2020.git`

Change your directory to following folder

`cd DevDays-2020\DockerAndKubernetes\lab-content\5-ingress`



For purpose of this lab,  Ingress Controller has already been created. 


## Creating Ingress

To test things out, you need to get your Ingress Definition.


Replace <firstname-lastname> in demo-ing.yaml with your firstname and lastname

`kubectl create -f demo-ing.yaml`

## Validation

Open a browser and go https://<firstname-lastname>.p360.providerdataplatform.net/
Here is where things seem to get confusing, though. Ingress on its own doesn't really do anything. You need something to listen to the Kubernetes API for Ingress resources and then handle requests that match them. This is where the second piece to the puzzle comes in — the Ingress Controller.

Ingress Controllers can technically be any system capable of reverse proxying, but the most common is Nginx. A full example Nginx Ingress Controller (and LoadBalancer service) is as follows. Please note that if you are not on a provider that supports LoadBalancer services (ie. bare-metal), you can create a NodePort Service instead and point to your nodes with an alternative solution that fills that role — a reverse proxy capable of routing requests to the exposed NodePort for the Ingress Controller on each of your nodes.

### Creating the Controller

First, inspect the file:

`cat ing-controller.yaml; echo`{{execute}}

Now create it:

`kubectl create -f ing-controller.yaml`{{execute}}

This is essentially an Nginx image that monitors Ingress resources for requested routes to serve. One callout you may have noticed is that it specifies a --default-backend-service as a startup argument, passing in nginx-default-backend. This is wanting a service that can simply handle returning a 404 response for requests that the Ingress Controller is unable to match to an Ingress rule. Let’s create that as well with the specified name:

`kubectl create -f ing-backend.yaml`{{execute}}

I said Ingress is made up of two main components and we introduced three new things. This is because the default backend isn’t really a piece of Ingress itself, but rather is something that the Nginx Ingress Controller requires. Other Ingress Controllers won’t necessarily have this component.

### Wiring it Up

Assuming you’ve created the Ingress Controller above with the dependent default backend, your Ingress resources should be handled by the LoadBalancer created with the Ingress Controller service. Of course, you would need services named website and forums to route to in the above example.

As a quick test, you can deploy the following instead.

`kubectl create -f demo-ing.yaml`{{execute}}

To test things out, you need to get your Ingress Controller entrypoint.

For LoadBalancer services that will be:

`kubectl get service ingress-nginx -o wide`{{execute}}

For NodePort services, you can find the exposed port with:

`kubectl describe service ingress-nginx`{{execute}}
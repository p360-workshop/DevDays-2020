**Namespaces** isolate pods to separate workloads from each other and to give you the capability of setting up resource constrainsts. You can map namespaces to the different application environments you might consider. 

## Discover Kubernetes namespaces 

Let's take a look at the initial Kubernetes namespaces:

`kubectl get namespace`{{execute}}

By default, Kubernetes comes with a few predefined namespaces. We can see them here:

<p style="text-align:center;"><img src="/contino/courses/kubernetes/pods/assets/namespaces.png" alt="Namespaces"></p>

### kube-system

This namespace has objects created by Kubernetes system:

`kubectl get pods -n kube-system; echo`{{execute}}

Pods inside of this namespace are needed to make Kubernetes work, such as controllers and add-ons natively integrated with Kubernetes which we will talk later on.

> **Note:** Usually, each cloud provider will run specific implementation pods in this namespace too - so don't worry if the `kube-system` namespace differs between clusters/cloud providers.

### kube-public

This namespace has a `ConfigMap` which contains the [bootstrapping and certificate](https://kubernetes.io/docs/reference/access-authn-authz/bootstrap-tokens/) configuration for the Kubernetes cluster:

`kubectl get pods -n kube-public; echo`{{execute}}

You won't see anything running in this namespace, but we can see a `cluster-info` ConfigMap:

`kubectl get configmap -n kube-public cluster-info -o yaml; echo`{{execute}}

In addition, this namespace might be treated as a location used to run an object which should be visible and readable throughout the whole cluster.

### default

All objects created without specifying a namespace will automatically be created in the `default` namespace.

This namespace is initially empty and doesn't contain any objects:

`kubectl get pods -n default; echo`{{execute}}

One thing to note about the `default` namespace is that it can't be deleted, unlike other namespaces within the Kubernetes cluster.

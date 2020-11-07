

You can think of a [**Namespace**](https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/) as a virtual cluster inside of your Kubernetes deployment. You can have multiple namespaces inside of a single Kubernetes cluster and each one of them is isolated from each other. As all pods run in a specifc namespace, they can help your team with the organisation, security and performance of your pods.







**Namespaces** isolate pods to separate workloads from each other and to give you the capability of setting up resource constrainsts. You can map namespaces to the different application environments you might consider. 

**kubectl** 

To communicate with the Kubernetes API, we use a CLI tool called ```kubectl```. Most likely, you are also going to leverage configuration files in YAML format in order to provide cluster configuration and create objects.



## Discover Kubernetes namespaces 

Let's take a look at the initial Kubernetes namespaces:

`kubectl get namespaces`

By default, Kubernetes comes with a few predefined namespaces such as kube-system, kube-public, default etc.

### Create a namespace:

Creating a namespace can be done with a single command. Since in this lab we share our k8s cluster. Let's create a namespace
 
We will create namespace with name as our msid so that we don't step over each others toes. 

`kubectl create namespace <YourMSid>`


### Create test namespace using yaml:
You can create a YAML file and apply it, just like any other Kubernetes resource.

Look at the file `test-namespace.yaml`


```
kind: Namespace
apiVersion: v1
metadata:
  name: <YourMSid>
```



`kubectl apply -f /pods-manifests/test-namespace.yaml`

### List all namespaces:

`kubectl get namespaces`

### Delete a Namespace:

To delete a namespace there are two options, we can use a yaml file or a single command:

`kubectl delete -f /pods-manifests/test-namespace.yaml`

or
 
`kubectl delete namespace <YourMSid>`

Check that the `<YourMSid>` namespace has been successfully deleted:

`kubectl get namespaces`

We are  going to re-create   ```<YourMSid>``` namespace as we will use it later in this course.

`kubectl apply -f /pods-manifests/test-namespace.yaml`

### Set your default namespace:

You can specify your default namespace so that you don't to specify namespace everytime. 


`kubectl config set-context --current --namespace=<YourMSId>`


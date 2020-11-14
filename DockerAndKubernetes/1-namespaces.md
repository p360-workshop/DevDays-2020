# Namespace

## Concept
You can think of a https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/[Namespace] as a virtual cluster inside of your Kubernetes deployment. You can have multiple namespaces inside of a single Kubernetes cluster and each one of them is isolated from each other. As all pods run in a specifc namespace, they can help your team with the organisation, security and performance of your pods.


**Namespaces** isolate pods to separate workloads from each other and to give you the capability of setting up resource constrainsts. You can map namespaces to the different application environments you might consider. 

**kubectl** 

To communicate with the Kubernetes API, we use a CLI tool called ```kubectl```. Most likely, you are also going to leverage configuration files in YAML format in order to provide cluster configuration and create objects.

## What will you need

Make sure you have code for the lab and you are in right path

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Clone the repo if you don't have.

`git clone https://github.com/p360-workshop/DevDays-2020.git`

Change your directory to following folder

`cd DevDays-2020\DockerAndKubernetes\lab-content\1-namespace`


## Discover Kubernetes namespaces 

Let's take a look at the initial Kubernetes namespaces:

`kubectl get namespaces`

By default, Kubernetes comes with a few predefined namespaces such as kube-system, kube-public, default etc.

## Create a namespace:

Creating a namespace can be done with a single command. Since in this lab we share our k8s cluster. Let's create a namespace
 
We will create namespace with name as our msid so that we don't step over each others toes. 

`kubectl create namespace <firstname-lastname>`




## List all namespaces:

`kubectl get namespaces`

## Delete a Namespace:

To delete a namespace there are two options, we can use a yaml file or a single command:

`kubectl delete -f test-namespace.yaml`

or
 
`kubectl delete namespace <firstname-lastname>`

Check that the `<firstname-lastname>` namespace has been successfully deleted:

`kubectl get namespaces`
## Create test namespace using yaml:
We are  going to re-create   ```<firstname-lastname>``` namespace as we will use it later in this course. But this time we will create it using yaml file.

You can create a YAML file and apply it, just like any other Kubernetes resource.

Look at the file `test-namespace.yaml`


```
kind: Namespace
apiVersion: v1
metadata:
  name: <firstname-lastname>
```



`kubectl apply -f test-namespace.yaml`


## Set your default namespace:

You can specify your default namespace so that you don't to specify namespace everytime. 


`kubectl config set-context --current --namespace=<firstname-lastname>`


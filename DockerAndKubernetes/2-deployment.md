# Deployment

## Concept

The Kubernetes documentation states that a Deployment allows you to:

> ... describe a desired state in a Deployment object, and the Deployment controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.

In this lab, we’ll explain both how Deployments work from a high-level perspective, and then get our hands dirty by creating a Deployment.

### Deployments

Kubernetes deployments manage stateless services running in your cluster (as opposed to - for example - StatefulSets, which manage stateful services). Their purpose is to keep a set of identical pods running and upgrade them in a controlled way – performing a rolling update by default. There are different deployment strategies that work with Deployments. They are, however, out of scope of this scenario. For more information on deployment strategies, read the [Kubernetes Documentation here](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#writing-a-deployment-spec).



image::lab-content/deployment-high-level.png[Deployment,500,500]


### Deployment Object

Next, we're going to cover the main deployment object, or `spec`. Below is a sample deployment spec

```
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    run: nginx-deployment
  name: nginx-deployment
  namespace: <firstname-lastname>
spec:
  replicas: 1
  revisionHistoryLimit: 10
  selector:
    matchLabels:
      run: nginx-deployment
  strategy:
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 1
    type: RollingUpdate
  template:
    metadata:
      labels:
        run: nginx-deployment
    spec:
      containers:
      - image: 840891909344.dkr.ecr.us-east-1.amazonaws.com/workshop:nginx
        imagePullPolicy: Always
        name: nginx-deployment
        ports:
        - containerPort: 80
          protocol: TCP

```

The metadata contains the name of the deployment (which must be unique), an internal uid used by Kubernetes, and the annotations object. It contains one annotation, namely that the current deployment revision is 1. Also as seen in other scenarios throughout this course, each object in Kubernetes can have a set of labels, which are key-value pairs.

>**Take Away Point:** Label everything!


The spec (specification) of the deployment has two keys you must set:

- `replicas`: describes how many pods this deployment should have. In our case, there will be one only one pod created.
- `template`: describes how each pod should look like. It describes a list of containers that should be in the Pod.

The two other keys can be set to customize the behavior of the deployment.

- `selector`: determines which pods are considered to be part of this deployment. This uses labels to 'select' pods.
- `strategy`: states how an update to a deployment should be rolled out. See earlier at the start of this chapter for the Kubernetes API link on more information on deployment strategies.



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



## Creating a Deployment 

Change your directory to following folder

`cd /root/DevDays-2020/DockerAndKubernetes/lab-content/2-deployment`


Create a deployment using this command:

`kubectl create -f dep-manifest.yaml -n <firstname-lastname>`

List the deployment:

`kubectl get deployments -n <firstname-lastname>`

Look at the pods that got spunned up

`kubectl get pods -n <firstname-lastname>`

## Scale Deployments

Now that you've created your deployment, let's look at scaling. You can update the replicas through the deployment manifest or by the `kubectl` command. For example:

`kubectl scale deployment nginx-deployment --replicas=10 -n <firstname-lastname>`

This will scale the `nginx-deployment` Deployment to `10` replicas.

Check that you now have 10 pods running

`kubectl get pods -n <firstname-lastname>`

You can also edit this in the manifest. For example:

Find the line `8` in the manifest file(dep-manifest.yaml), and update it to 2 replicas. Then replace the deployment:

`kubectl replace -f dep-manifest.yaml -n <firstname-lastname>`

Once the deployment has been updated, check the pods:

`kubectl get po -n <firstname-lastname>`

You should see 2 nginx pods now. You've successfully just scaled your deployment. 

## Cleanup

Run this command to delete the deployment

`kubectl delete -f dep-manifest.yaml -n <firstname-lastname>`
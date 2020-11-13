# Deployment

## Concept

The Kubernetes documentation states that a Deployment allows you to:

> ... describe a desired state in a Deployment object, and the Deployment controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.

In this lab, we’ll explain both how Deployments work from a high-level perspective, and then get our hands dirty by creating a Deployment and seeing how it relates to ReplicaSet and Pod objects.

### Deployments

Kubernetes deployments manage stateless services running in your cluster (as opposed to - for example - StatefulSets, which manage stateful services). Their purpose is to keep a set of identical pods running and upgrade them in a controlled way – performing a rolling update by default. There are different deployment strategies that work with Deployments. They are, however, out of scope of this scenario. For more information on deployment strategies, read the [Kubernetes Documentation here](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#writing-a-deployment-spec).



![image](lab-content/deployment-high-level.png)


## What will you need

Make sure you have code for the lab and you are in right path

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Clone the repo if you don't have.

`git clone https://github.com/p360-workshop/DevDays-2020.git`

Change your directory to following folder

`cd DevDays-2020\DockerAndKubernetes\lab-content\2-deployment`



## Creating a Deployment in kubectl

Before we start, you should already have a namespace created called `<firstname-lastname>` all lowercase. If you do not have this namespace yet, or you have deleted it, then please re-create it:

`kubectl create namespace <YourNamespaceName>`

Create a basic deployment called `nginx-deployment` using the `nginx` image, and expose port 80 in the container:

`kubectl run nginx-deployment -n <YourNamespaceName> --image=nginx --port 80`

```
$ kubectl run nginx-deployment -n <firstname-lastname> --image=nginx --port 80

deployment.apps "nginx-deployment" created
```

Now let's inspect the deployment that we've just created:

`kubectl get deployment nginx-deployment -n <firstname-lastname> -o yaml`

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  annotations:
    deployment.kubernetes.io/revision: "1"
  creationTimestamp: 2018-08-07T18:30:51Z
  generation: 1
  labels:
    run: nginx-deployment
  name: nginx-deployment
  namespace: <firstname-lastname>
  resourceVersion: "849"
  selfLink: /apis/extensions/v1beta1/namespaces/contino/deployments/nginx-deployment
  uid: 035c4414-9a70-11e8-a7a6-0242ac110067
```

The metadata contains the name of the deployment (which must be unique), an internal uid used by Kubernetes, and the annotations object. It contains one annotation, namely that the current deployment revision is 1. Also as seen in other scenarios throughout this course, each object in Kubernetes can have a set of labels, which are key-value pairs.

>**Take Away Point:** Label everything!

## Deployment Object

Next, we're going to cover the main deployment object, or `spec`.

```
spec:
  progressDeadlineSeconds: 600
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
      creationTimestamp: null
      labels:
        run: nginx-deployment
    spec:
      containers:
      - image: nginx
        imagePullPolicy: Always
        name: nginx-deployment
        ports:
        - containerPort: 80
          protocol: TCP
```

The spec (specification) of the deployment has two keys you must set:

- `replicas`: describes how many pods this deployment should have. In our case, there will be one only one pod created.
- `template`: describes how each pod should look like. It describes a list of containers that should be in the Pod.

The two other keys can be set to customize the behavior of the deployment.

- `selector`: determines which pods are considered to be part of this deployment. This uses labels to 'select' pods.
- `strategy`: states how an update to a deployment should be rolled out. See earlier at the start of this chapter for the Kubernetes API link on more information on deployment strategies.

## Deployment using yaml file

Because you have already created a deployment earlier called `nginx-deployment`, we need to delete it before we can create a new deployment with the YAML manifest:

`kubectl delete deployment nginx-deployment -n <firstname-lastname>`

Before we create the new deployment, let's inspect the file:

`cat dep-manifest.yaml; `

```
apiVersion: extensions/v1beta1
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
      - image: nginx
        imagePullPolicy: Always
        name: nginx-deployment
        ports:
        - containerPort: 80
          protocol: TCP

```

As you can see, it essentially re-creates the YAML output from the deployment that we created manually in the previous chapter.

Now create the deployment:

`kubectl create -f dep-manifest.yaml -n <firstname-lastname>`

Get the deployment:

`kubectl get deployments -n <firstname-lastname> -o yaml`

If you go back to the previous chapter where we created the deployment manually, you'll see that the output is very similar to the output above.


## Scale Deployments

Now that you've created your deployment, let's look at scaling. Similar to how we created a deployment, you can update the replicas through the deployment manifest or by the `kubectl` command. For example:

`kubectl scale deployment nginx-deployment --replicas=10 -n <firstname-lastname>`

This will scale the `nginx-deployment` Deployment to `10` replicas.

You can also edit this in the manifest. For example:

`vi dep-manifest.yaml`

Find the line `9` in the manifest, and update it to 10 replicas. `:wq` in `vim` to save and exit. Then replace the deployment:

`kubectl replace -f dep-manifest.yaml`

Once the deployment has been updated, check the pods:

`kubectl get po -n <firstname-lastname>`

You should see 10 nginx pods now. You've successfully just scaled your deployment. Equally, to scale down your deployment, simply change the number of replicas and update the deployment again.

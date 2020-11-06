Kubernetes deployments manage stateless services running in your cluster (as opposed to - for example - StatefulSets, which manage stateful services). Their purpose is to keep a set of identical pods running and upgrade them in a controlled way – performing a rolling update by default. There are different deployment strategies that work with Deployments. They are, however, out of scope of this scenario. For more information on deployment strategies, read the [Kubernetes Documentation here](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#writing-a-deployment-spec).

<p style="text-align:center;"><img src="/contino/courses/kubernetes/basic-deployments/assets/deployment-high-level.png" alt="deployments-high-level-overview"></p>

### Creating a Deployment in kubectl

Before we start, you should already have a namespace created called `contino`. If you do not have this namespace yet, or you have deleted it, then please re-create it:

`kubectl create namespace contino`{{execute}}

Create a basic deployment called `nginx-deployment` using the `nginx` image, and expose port 80 in the container:

`kubectl run nginx-deployment -n contino --image=nginx --port 80`{{execute}}

```
$ kubectl run nginx-deployment -n contino --image=nginx --port 80

deployment.apps "nginx-deployment" created
```

Now let's inspect the deployment that we've just created:

`kubectl get deployment nginx-deployment -n contino -o yaml`{{execute}}

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
  namespace: contino
  resourceVersion: "849"
  selfLink: /apis/extensions/v1beta1/namespaces/contino/deployments/nginx-deployment
  uid: 035c4414-9a70-11e8-a7a6-0242ac110067
```

The metadata contains the name of the deployment (which must be unique), an internal uid used by Kubernetes, and the annotations object. It contains one annotation, namely that the current deployment revision is 1. Also as seen in other scenarios throughout this course, each object in Kubernetes can have a set of labels, which are key-value pairs.

>**Take Away Point:** Label everything!

### Deployment Object

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

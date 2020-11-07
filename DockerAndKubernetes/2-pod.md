

[**Pod**](https://kubernetes.io/docs/concepts/workloads/pods/pod/) is the smallest concept we have in Kubernetes. Pods consist of one or more containers.

![image](assets/pod.png)



## Kubernetes API Reference 

In this exercise, we want to create a Pod and first of all, we are going to check the [Kubernetes API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#pod-v1-core) to check the Pod's full specification.

Kubernetes API Reference provides us with the necessary information to write the Pod's specification in YAML format. 

Take a look at the file `pod.yaml`

```
apiVersion: v1
kind: Pod
metadata:
  name: happypanda
spec:
  containers:
  - name: nginx
    image: nginx
```

The pod name is ```happypanda``` and it deploys an `nginx` container image from Docker Hub's public registry.

## Create a Pod

By using the ```kubectl``` CLI tool, we authenticate to the Kubernetes API and apply our Pod specification to the Kubernetes Cluster:

`kubectl apply -f /pods-manifests/pod.yaml`

## Validation

Let's have a look at the pods running in **<YourNameSpace>** namespace:

`kubectl get pods -n <YourMSid>`

Great! The `happypanda` pod is running in the `<YourMSid>` namespace!

## Delete a Pod

We can delete pods by using a YAML file or a single command:

`kubectl delete -f /pods-manifests/pod.yaml`

or 

`kubectl delete pod happypanda`

Check if the `happypanda` pod has been deleted:

`kubectl get pods`



> **NOTE:** Don’t use naked Pods (that is, Pods not bound to a ReplicaSet or Deployment) if you can avoid it. Naked Pods will not be rescheduled in the event of a node failure. For further information, refer to the Kubernetes [Best Practices](https://kubernetes.io/docs/concepts/configuration/overview/#naked-pods-vs-replicasets-deployments-and-jobs) documentation.

We are going to update our `happypanda` pod running in **dev-service1** namespace and to do that you need to apply the ```pod-update.yaml``` configuration.

Look at the file `/pod-update.yaml`:


```
apiVersion: v1
kind: Pod
metadata:
  name: happypanda
  namespace: dev-service1
  labels: 
    app: redis
    segment: backend
    company: mycompany    
spec:
  containers:
  - name: redis
    image: redis:4.0.10
    ports:
    - name: redisport
      containerPort: 6379
      protocol: TCP
```

1. Pods labels has been added in the metadata section
2. Container image has been updated in the containers section. Notice that you can specify image tags if not, latest is used.
3. Pod ports has been added in the containers section

### Update the existing Pod 

A pod can be updated by applying a yaml file so let's apply our ```pod-update.yaml``` that includes the above changes:

`kubectl apply -f /pods-manifests/pod-update.yaml`

### Did it work? What happened?

The error we are getting is the following:

```
The Pod "happypanda" is invalid: spec: Forbidden: pod updates may not change fields other than `spec.containers[*].image`, `spec.initContainers[*].image`, `spec.activeDeadlineSeconds` or `spec.tolerations` (only additions to existing tolerations)
```

Ok, let's review the [Kubernetes API Reference](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core) and we can find the following statement in containers ports specification:

![image](assets/ports.png)

In Kubernetes, there are some fields that can't be updated. The Kubernetes API Reference can be helpful to highlight some of the API restrictions and the available object specification.

In order to update those forbidden updates, we should delete the pod and create it up again.
We could bypass these situations with "deployments" which we will cover in other chapter.

### Fixing the highlighted problem

Delete the pod:

`kubectl delete pod happypanda -n dev-service1`

Apply the yaml file:

`kubectl apply -f /pods-manifests/pod-update.yaml`

Check it out:

`kubectl describe pod happypanda --namespace dev-service1`

Our happypanda pod is now running with labels, port specification and a new container image!

`kubectl get pod -n dev-service1`

### Clean up

Delete pod:

`kubectl delete pod happypanda -n dev-service1`

Delete namespace:

`kubectl delete namespace dev-service1`
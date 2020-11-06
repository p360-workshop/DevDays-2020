We are going to update our `happypanda` pod running in **dev-service1** namespace and to do that you need to apply the ```pod-update.yaml``` configuration.

Look at the file `/pod-update.yaml`{{open}}:

1. Pods labels has been added in the metadata section
2. Container image has been updated in the containers section. Notice that you can specify image tags if not, latest is used.
3. Pod ports has been added in the containers section

### Update the existing Pod 

A pod can be updated by applying a yaml file so let's apply our ```pod-update.yaml``` that includes the above changes:

`kubectl apply -f /pods-manifests/pod-update.yaml`{{execute}}

### Did it work? What happened?

The error we are getting is the following:

```
The Pod "happypanda" is invalid: spec: Forbidden: pod updates may not change fields other than `spec.containers[*].image`, `spec.initContainers[*].image`, `spec.activeDeadlineSeconds` or `spec.tolerations` (only additions to existing tolerations)
```

Ok, let's review the [Kubernetes API Reference](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#container-v1-core) and we can find the following statement in containers ports specification:

<p style="text-align:center;"><img src="/contino/courses/kubernetes-basic-concepts/pods/assets/ports.png" alt="Ports"></p>

In Kubernetes, there are some fields that can't be updated. The Kubernetes API Reference can be helpful to highlight some of the API restrictions and the available object specification.

In order to update those forbidden updates, we should delete the pod and create it up again.
We could bypass these situations with "deployments" which we will cover in other chapter.

### Fixing the highlighted problem

Delete the pod:

`kubectl delete pod happypanda -n dev-service1`{{execute}}

Apply the yaml file:

`kubectl apply -f /pods-manifests/pod-update.yaml`{{execute}}

Check it out:

`kubectl describe pod happypanda --namespace dev-service1`{{execute}}

Our happypanda pod is now running with labels, port specification and a new container image!

`kubectl get pod -n dev-service1`{{execute}}

### Clean up

Delete pod:

`kubectl delete pod happypanda -n dev-service1`{{execute}}

Delete namespace:

`kubectl delete namespace dev-service1`{{execute}}
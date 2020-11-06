
To communicate with the Kubernetes API, we use a CLI tool called ```kubectl```. Most likely, you are also going to leverage configuration files in YAML format in order to provide cluster configuration and create objects.

In this YAML file, we are going to write our configuration to tell Kubernetes how to deploy and run our Pod.

Let's begin!

## Kubernetes API Reference 

Every Kubernetes object has an API specification and this specification can be written it down in a YAML file.

In this exercise, we want to create a Pod and first of all, we are going to check the [Kubernetes API](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.18/#pod-v1-core) to check the Pod's full specification.

Kubernetes API Reference provides us with the necessary information to write the Pod's specification in YAML format. 

Take a look at the file `pod.yaml`{{open}}

The pod name is ```happypanda``` and it deploys an `nginx` container image from Docker Hub's public registry.

## Create a Pod

By using the ```kubectl``` CLI tool, we authenticate to the Kubernetes API and apply our Pod specification to the Kubernetes Cluster:

`kubectl apply -f /pods-manifests/pod.yaml`{{execute}}

## Validation

In order to validate if `happypanda` pod is running, we need to run the following command:

`kubectl get pods`{{execute}}

Notice that `happypanda` pod is running in the `default` namespace as we didn't set any namespace configuration in our specification.

## Delete a Pod

We can delete pods by using a YAML file or a single command:

`kubectl delete -f /pods-manifests/pod.yaml`{{execute}}

or 

`kubectl delete pod happypanda`{{execute}}

Check if the `happypanda` pod has been deleted:

`kubectl get pods`{{execute}}

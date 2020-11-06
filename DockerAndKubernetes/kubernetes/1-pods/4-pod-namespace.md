Take a look at the file `pod-namespace.yaml`{{open}}

Notice that `happypanda` pod has been configured to be scheduled in the **dev-service1** namespace.

Let's apply this yaml file, leveraging the following command:

`kubectl apply -f /pods-manifests/pod-namespace.yaml`{{execute}}

## Validation

Let's have a look at the pods running in **dev-service1** namespace:

`kubectl get pods -n dev-service1`{{execute}}

Great! The `happypanda` pod is running in the `dev-service1` namespace!

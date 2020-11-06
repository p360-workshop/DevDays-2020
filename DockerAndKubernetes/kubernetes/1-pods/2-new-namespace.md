Creating a namespace can be done with a single command. Let's create **dev-service1** namespace:

`kubectl create namespace dev-service1`{{execute}}

Or you can create a YAML file and apply it, just like any other Kubernetes resource.

Look at the file `test-namespace.yaml`{{open}}

### Create test namespace:

`kubectl apply -f /pods-manifests/test-namespace.yaml`{{execute}}

### List all namespaces:

`kubectl get namespaces`{{execute}}

### Delete a Namespace:

To delete a namespace there are two options, we can use a yaml file or a single command:

`kubectl delete -f /pods-manifests/test-namespace.yaml`{{execute}}

or
 
`kubectl delete namespace test`{{execute}}

Check that the `test` namespace has been successfully deleted:

`kubectl get namespaces`{{execute}}

>**Note:** We are not going to remove ```dev-service1``` namespace as we will use it later in this course.
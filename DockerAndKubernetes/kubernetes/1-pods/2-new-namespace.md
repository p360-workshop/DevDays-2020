Creating a namespace can be done with a single command. Let's create **dev-service1** namespace:

`kubectl create namespace dev-service1`

Or you can create a YAML file and apply it, just like any other Kubernetes resource.

Look at the file `test-namespace.yaml`{{open}}

### Create test namespace:

`kubectl apply -f /pods-manifests/test-namespace.yaml`

### List all namespaces:

`kubectl get namespaces`

### Delete a Namespace:

To delete a namespace there are two options, we can use a yaml file or a single command:

`kubectl delete -f /pods-manifests/test-namespace.yaml`

or
 
`kubectl delete namespace test`

Check that the `test` namespace has been successfully deleted:

`kubectl get namespaces`

>**Note:** We are not going to remove ```dev-service1``` namespace as we will use it later in this course.
To scale down the Service to 2 replicas, run again the `scale` command:

`kubectl scale deployments/kubernetes-bootcamp --replicas=2`
 
List the Deployments to check if the change was applied with the `get deployments` command:

`kubectl get deployments`

The number of replicas decreased to 2. List the number of Pods, with `get pods`:
 
`kubectl get pods -o wide`

This confirms that 2 Pods were terminated.


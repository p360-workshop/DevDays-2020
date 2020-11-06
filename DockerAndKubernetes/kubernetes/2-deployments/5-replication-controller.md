The Replication Controller is the original form of replication in Kubernetes. It has been replaced by ReplicaSets, so we won't cover it in too much detail, but it's worth understanding.

Similar to Deployments, it allows you to tell the Kubernetes scheduler how many pods to ensure stay running. If a pod does die, then the replication controller will create another pod to ensure the replica count is matched.

### Creating a Replication Controller

Look at the replication controller manifest:

`cat rc-nginx.yaml; echo`{{execute}}

Now create the replication controller:

`kubectl create -f rc-nginx.yaml`{{execute}}

Check for RCs:

`kubectl get rc -n contino`{{execute}}

Now check for pods:

`kubectl get po -n contino`{{execute}}

As you can see, there are now 3 pods, as per the replication controller manifest that we just created.

To delete a replication controller:

`kubectl delete rc nginx -n contino`{{execute}}

### Scaling RCs

Scaling RCs is very similar to deployments. Edit the replicas in the manifest and use the `kubectl replace -f` command:

`kubectl replace -f rc-nginx.yaml`{{execute}}
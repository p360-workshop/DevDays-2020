Now that you've created your deployment, let's look at scaling. Similar to how we created a deployment, you can update the replicas through the deployment manifest or by the `kubectl` command. For example:

`kubectl scale deployment nginx-deployment --replicas=10 -n contino`

This will scale the `nginx-deployment` Deployment to `10` replicas.

You can also edit this in the manifest. For example:

`vi dep-manifest.yaml`

Find the line `9` in the manifest, and update it to 10 replicas. `:wq` in `vim` to save and exit. Then replace the deployment:

`kubectl replace -f dep-manifest.yaml`

Once the deployment has been updated, check the pods:

`kubectl get po -n contino`

You should see 10 nginx pods now. You've successfully just scaled your deployment. Equally, to scale down your deployment, simply change the number of replicas and update the deployment again.

## Proportional scaling

RollingUpdate Deployments support running multiple versions of an application at the same time. When you scale a RollingUpdate Deployment that is in the middle of a rollout (either in progress or paused), then the Deployment controller will balance the additional replicas in the existing active ReplicaSets (ReplicaSets with Pods) in order to mitigate risk. This is called proportional scaling.

For example, you are running a Deployment with 10 replicas, maxSurge=3, and maxUnavailable=2.

`kubectl get deployments -n contino`

```sh
NAME                 DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment     10        10        10           10          50s
```

You update to a new image which happens to be unresolvable from inside the cluster.

`kubectl set image deploy/nginx-deployment nginx-deployment=nginx:unresolvabletag -n contino`

```sh
deployment "nginx-deployment" image updated
```

The image update starts a new rollout with updated image nginx-deployment, but the rollout is halted due to failing pods and maxUnavailable requirement that we mentioned above.

`kubectl -n contino get pods`

```sh
NAME                          DESIRED   CURRENT   READY     AGE
nginx-deployment-6975c45ccc-rmcj2   0/1     ErrImagePull   0          3m26s
nginx-deployment-6975c45ccc-v9s6h   0/1     ErrImagePull   0          3m26s
...
```

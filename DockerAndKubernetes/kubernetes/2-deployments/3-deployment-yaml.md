Because you have already created a deployment earlier called `nginx-deployment`, we need to delete it before we can create a new deployment with the YAML manifest:

`kubectl delete deployment nginx-deployment -n contino`

Before we create the new deployment, let's inspect the file:

`cat dep-manifest.yaml; echo`

As you can see, it essentially re-creates the YAML output from the deployment that we created manually in the previous chapter.

Now create the deployment:

`kubectl create -f dep-manifest.yaml`

Get the deployment:

`kubectl get deployments -n contino -o yaml`

If you go back to the previous chapter where we created the deployment manually, you'll see that the output is very similar to the output above.
# Managing Application Configurations and Secrets

## Concept

Many applications require configuration settings and secrets such as TLS certificates to run in a production environment. In this lab you will learn how to:

* Create secrets to store sensitive application data
* Create configmaps to store application configuration data
* Expose secrets and configmaps to Pods at runtime

In this lab we will create a new Deployment named `secure-monolith` based on the `healthy-monolith` Pod. The `secure-monolith` deployment secures access to the `monolith` container using http://nginx.org/en[Nginx] container, which will serve as a reverse proxy serving HTTPS.

> The nginx container will be deployed in the same pod as the monolith container because they are tightly coupled.

## What will you need

Make sure you have code for the lab and you are in right path. Skip this step if you already done this

Open up your IDE here

`https://<firstname-lastname>.hue.providerdataplatform.net/`

Open up terminal if a terminal is not open

`Top left menu button> Terminal > New Terminal`

Change directory to /root 

`cd /root`

Clone the repo if you don't have it already

`git clone https://github.com/p360-workshop/DevDays-2020.git`

You should already have a namespace created called `<firstname-lastname>` all lowercase. If you do not have this namespace yet, or you have deleted it, then please re-create it:

`kubectl create namespace <firstname-lastname>`

Set your default namespace so that you don't to specify namespace everytime. 

`kubectl config set-context --current --namespace=<firstname-lastname>`


## Creating Secrets

Change your directory to following folder

`cd /root/DevDays-2020/DockerAndKubernetes/lab-content/4-configmaps-secrets`



Before we can use the `nginx` container to serve HTTPS traffic we need some TLS certificates. In this tutorial you will store a set of self-signed TLS certificates in Kubernetes as secrets.

Create the `tls-certs` secret from the TLS certificates stored under the tls directory:

`
kubectl create secret generic tls-certs --from-file=tls/ --namespace=<firstname-lastname>
`

Examine the `tls-certs` secret:

`
kubectl describe secrets tls-certs --namespace=<firstname-lastname>
`

### Quiz

* How many items are stored under the `tls-certs` secret?
* What are the key names?

## Creating Configmaps

The nginx container also needs a configuration file to setup the secure reverse proxy. In this tutorial you will create a configmap from the `proxy.conf` nginx configuration file.

Create the `nginx-proxy-conf` configmap based on the `proxy.conf` nginx configuration file:

`
kubectl create configmap nginx-proxy-conf --from-file=nginx/proxy.conf --namespace=<firstname-lastname>
`

Examine the `nginx-proxy-conf` configmap:

`
kubectl describe configmaps nginx-proxy-conf --namespace=<firstname-lastname>
`

### Quiz

* How many items are stored under the `nginx-proxy-conf` configmap?
* What are the key names?

## Use Configmaps and Secrets

In this tutorial you will expose the `nginx-proxy-conf` configmap and the `tls-certs` secrets to the `secure-monolith` pod at runtime:

Examine the `secure-monolith` pod configuration file:

```
cat pods/secure-monolith.yaml
```

### Quiz

* How are secrets exposed to the `secure-monolith` Pod?
* How are configmaps exposed to the `secure-monolith` Pod?

Create the `secure-monolith` Pod using kubectl:

```
kubectl create -f pods/secure-monolith.yaml
```

#### Test the HTTPS endpoint

Forward local port 10444 to 443 of the `secure-monolith` Pod:

```
kubectl port-forward secure-monolith 10444:443
```

Open up a new terminal 

`Top left menu button> Terminal > New Terminal`

Change directory to `cd /root/DevDays-2020/DockerAndKubernetes/lab-content/4-configmaps-secrets`

Use the `curl` command to test the HTTPS endpoint:

```
curl --cacert tls/ca.pem https://127.0.0.1:10444 -k
```


Expected Output
```
{"message":"Hello"}
```

Switch back to first terminal and use the `kubectl logs` command to verify traffic to the `secure-monolith` Pod:

```
kubectl logs -c nginx secure-monolith
```

## Cleanup

```
kubectl delete -f pods/secure-monolith.yaml
kubectl delete configmaps nginx-proxy-conf
kubectl delete secret tls-certs
```


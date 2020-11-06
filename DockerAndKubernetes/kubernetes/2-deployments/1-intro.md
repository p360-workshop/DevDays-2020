## Overview

The Kubernetes documentation states that a Deployment allows you to:

> ... describe a desired state in a Deployment object, and the Deployment controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.

In this scenario, we’ll explain both how Deployments work from a high-level perspective, and then get our hands dirty by creating a Deployment and seeing how it relates to ReplicaSet and Pod objects.
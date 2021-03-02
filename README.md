# Kubernaties
kubernaties

![kubernetes-model-architecture](https://github.com/marun790/Kubernaties/blob/main/images/full-kubernetes-model-architecture.png?raw=true)
![Communication Flow](https://github.com/marun790/Kubernaties/blob/main/images/k8s_communication_flow.jpeg?raw=true)


##Components
### Master(Control plane):
* Kube-Api server
which handles all communication, external as well as intrnal
* etcd
consist of metatada(with key-value pair) about the cluster.
* Kube schedular
which is responsble for chosing the node for the pod
* Kube controller manager 
It continuously monitors the state of the cluster via the kube API server.
IT will keep check on the giivel criterias like 
* Kube cloud manager 

### Worker Nodes
* Kublet
Agent for kubernate to manage node.
The kubelet uses the container runtime to start the pod, monitors its lifecycle, checks for the readiness, etc
* kube proxy
Maintain communication amoung the pods using the network infirmation it have.

Kuberantes package manager helm
Taint on node , Tolerration on pod (key and value) EX: chosing pod for bigdata appllication


REF: https://medium.com/analytics-vidhya/the-kubernetes-control-plane-f4bf460c848f


Each component in kubernatis is an object.
## Objects in kubernaties
Each object having 2 main factor
* Spec - descripes the desired state of the object and when to recreate the object
* Status - Current status of the object
* we have to pass any abject spec via kubectl, kubectl will convert the yml to json and pass to api service

### Name space:
  Namespace is like bubble, we can do partitions on the nodes using this and also team level boundary is defigned by name space.
  Complete Spec: 
  
### pods:
  Smallest unit in the kubernate infra. which having containers that
* Complete spec : https://github.com/marun790/k8s-guide/blob/master/pod-sample.yaml
* Basic spec:
```

```
 
### Deploymet (Manages Pods):

* Comlplete Spec : https://github.com/marun790/k8s-guide/blob/master/deployment-sample.yml
* Basic Spec:
```

```

### Services:

* Complete Spec : https://github.com/marun790/k8s-guide/blob/master/services-sample.yml

* Basic Spec:
```

```

### ConfigMaps:
* Basic Spec:
```

```

### ReplicationController (Manages Pods)
### StatefulSets
### DaemonSets
### Volumes





Commends							|Description
--------------------------------------------------------------|-------------------------------------------------------------------------
kubectl get namespace						|to get all namespace
kubectl create namespace <namespace_name> 			|will create namespace
kuectl delete namespace <namespace_name> 			|will delte namespace
eval $(minikube docker-env)					|Will bind the docker container to kubernaties which allow to build images
kubectl apply -f <OBJECT_SPEC>				| Will apply the yml spec for the given object
kubectl apply -f arun-namspace.yaml				| create namespace with yaml file
kubectl get namespace arun-namespace -o yaml			| open namespace spec in yaml format
kubectl get pod 						| to get pod
kubectl delete pod muthu-emp-pod				| to delete pod




step:

* Create namespace
> kubectl apply -f arun-namspace.yaml

* switch to namespace using kubens
> kubens arun-namespace

* Image for the pod must be created inside namespace
> sudo docker image build . -t arun-emp:latest

* create pod
> kubectl apply -f arun-emp-pod.yml -> will create the pod

* do port forward
> kubectl port-forward <POD_NAME> 8080:8081 -n <NAMEPACE_NAME>

* do the build using kubernaties docker not with the root docker
> docker image build . -t arun-emp:latest

### Installation
* k8s install with minikube https://www.howtoforge.com/how-to-install-kubernetes-with-minikube-ubuntu-20-04/
* kubectl get pods -n kube-system
* sudo cp minikube-linux-amd64 /usr/local/bin/minikube
* sudo cp minikube-linux-amd64 /usr/local/bin/minikube
* sudo chmod 755 /usr/local/bin/minikube
* minikube version
* sudo curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
* echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list
* sudo echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list
* sudo apt-get update -y
* sudo apt-get install kubectl kubeadm kubectl -y
* minikube start
* kubectl get nodes
* kubectl get pods -n kube-system


kubectx - https://packages.debian.org/buster/all/kubectx/download
kubens

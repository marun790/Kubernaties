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


### Installation
k8s install with minikube https://www.howtoforge.com/how-to-install-kubernetes-with-minikube-ubuntu-20-04/
kubectl get pods -n kube-system
sudo cp minikube-linux-amd64 /usr/local/bin/minikube
sudo cp minikube-linux-amd64 /usr/local/bin/minikube
sudo chmod 755 /usr/local/bin/minikube
minikube version
sudo curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list
sudo echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list
sudo apt-get update -y
sudo apt-get install kubectl kubeadm kubectl -y
minikube start
kubectl get nodes
kubectl get pods -n kube-system


kubectx - https://packages.debian.org/buster/all/kubectx/download
kubens

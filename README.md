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

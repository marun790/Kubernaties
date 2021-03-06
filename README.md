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

* Spec:

```
apiVersion: v1
kind: Namespace
metadata:
  name: arun-namespace
  
``` 
  
### pods:
  Smallest unit in the kubernate infra. which having containers that
  One pod must have minimum of one container(a application) along with may have side car container, using single container per pod is best practice.
  which having responsibilities like resource managing minimal, maxmum resources, application heart beat checking resources, chosing node(taint - in pod, tollorent- on node).
  
* Complete spec : https://github.com/marun790/k8s-guide/blob/master/pod-sample.yaml
* Basic spec:

```
apiVersion: v1
kind: Pod
metadata:
  name: "arun-emp-pod"
  namespace: "arun-namespace"
  labels:
    app: "emp"
spec:
  containers:
  - image: arun-emp:latest
    name: arun-emp-container
    ports:
    - name: "http"
      containerPort: 8081
    imagePullPolicy: Never
    resources:
      limits:
        cpu: 500m
        memory: 512Mi
      requests:
        cpu: 500m
        memory: 512Mi
    livenessProbe:
      httpGet:
        path: "/employee/all"
        port: 8081
      failureThreshold: 3
      initialDelaySeconds: 20
      periodSeconds: 15
      successThreshold: 1
    readinessProbe:
      httpGet:
        path: "/employee/all"
        port: 8081
      failureThreshold: 3
      initialDelaySeconds: 20
      periodSeconds: 15
      successThreshold: 1


```
 
### Deploymet (Manages Pods):
* which is mainy focusing on replicas of the pod
* Deployment must have a spec 'selector' which is a pointer for the pod, if the selector for the pod missmatches the pod will keep on creates as the deployment unable to get the desired state of the pod
* responsible for rools and update(tag- 'rollingUpdate' - specifing minimum number of avilability of the pod based on this destroying and creating of pod happends)

* main thing is selector.matchLabels must be eqal to template.metadata.labels, the object pick the pod 
* to delete the pod we have to delete the deployment first, as we having 'type: RollingUpdate' deployment will do pod createion continuesly. 
* With  deployment image replaecement will make issue, rebuilding image with same version will not replace the deployment image version / registory.
* Comlplete Spec : https://github.com/marun790/k8s-guide/blob/master/deployment-sample.yml

* Basic Spec:
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name:  arun-emp-deployment
  labels:
    name:  arun-emp-deployment
spec:
  progressDeadlineSeconds: 2
  revisionHistoryLimit: 1
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 50%
      maxUnavailable: 1
  minReadySeconds: 0
  replicas: 2
  selector:
    matchLabels:
      app: "arun-emp"
      version: 0.0.01
  template:
    metadata:
      name: "arun-emp-pod"
      namespace: "arun-namespace"
      labels:
        app: "arun-emp"
        version: 0.0.01
    spec:
      containers:
        - image: arun-emp:latest
          name: arun-emp-container
          ports:
            - name: "http"
              containerPort: 8081
          imagePullPolicy: Never
          livenessProbe:
            httpGet:
              path: "/employee/all"
              port: 8081
            failureThreshold: 3
            initialDelaySeconds: 20
            periodSeconds: 15
            successThreshold: 1
          readinessProbe:
            httpGet:
              path: "/employee/all"
              port: 8081
            failureThreshold: 3
            initialDelaySeconds: 20
            periodSeconds: 15
            successThreshold: 1

```

### Services:
* same as ribbon(clientside load balancer which forwards reqest to client via round drobin) in spring cloud
* Specifies the way in which the pod should be communicated. Like through load balancer / through host IP / through  cluster IP
* we can group the replicas using 'selector.app' whick will match with 'lable.app' in pod. and uses these info in DNS, as becuse each deployment will allocate unique ip for the pod 
* these specs will leads to way for pod to pod communication
* Complete Spec : https://github.com/marun790/k8s-guide/blob/master/services-sample.yml

* Basic Spec:
```
apiVersion: v1
kind: Service
metadata:
  name: "arun-emp-service"
  labels:
    app: "arun-emp-service"
spec:
  type: ClusterIP
  ports:
    - name: http
      protocol: TCP
      port: 80
      targetPort: 8081
  selector:
    app: "arun-emp"
```

* after spinning the service portforward have to be done as the service is not exposed in host.
> kubectl port-forward service/arun-emp-service 8080:80 //for service port forward we have to mention /service as prefix

* Pod to pod communication can be acchived by service name in URI.
```

department-uri=http://arun-dept-service:80 // 'arun-dept-service:80' -> service details which exposed for department project

```

### ConfigMaps:
Used to create the configuration which used to share accross the pods, changing of config value won't reflect on the pod just like that, for that we hae to use ?
* Basic Spec:
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name:  arun-emp-deployment
  labels:
    name:  arun-emp-deployment
spec:
  progressDeadlineSeconds: 2
  revisionHistoryLimit: 1
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 50%
      maxUnavailable: 1
  minReadySeconds: 0
  replicas: 2
  selector:
    matchLabels:
      app: "arun-emp"
      version: 0.0.01
  template:
    metadata:
      name: "arun-emp-pod"
      namespace: "arun-namespace"
      labels:
        app: "arun-emp"
        version: 0.0.01
    spec:
      volumes:
        - name: config-volume
          configMap:
            name: employee-configmap
      containers:
        - image: arun-emp:latest
          name: arun-emp-container
          ports:
            - name: "http"
              containerPort: 8081
          imagePullPolicy: Never
          env:
            - name: address
              valueFrom:
                configMapKeyRef:
                  name: employee-configmap
                  key: address
            - name: spring.config.location
              value: /config/application.properties //Using mounted application.properties from mounted space
          volumeMounts:
            - name: config-volume    
              mountPath: /config		//Mounting config map in a directory in the pod host
          livenessProbe:
            httpGet:
              path: "/employee/all"
              port: 8081
            failureThreshold: 3
            initialDelaySeconds: 20
            periodSeconds: 15
            successThreshold: 1
          readinessProbe:
            httpGet:
              path: "/employee/all"
              port: 8081
            failureThreshold: 3
            initialDelaySeconds: 20
            periodSeconds: 15
            successThreshold: 1
```

### Scalling:
Scalling is a mechanism which auto increase resouces/applucations based on the resource availability
* Vertical scalling:
Will increase the phiscal hardware, like node and memory interms of traffic. currently kubernaties not supporting this feature.
* Horzondal auto scalling
will increase the application deployment count so can we will have more resources to handle the request, in kubernaties we having Horzondal pod auto scalling

### HPA - Horizondal pod auto scalling
depends on the CPU Load the new deployment will get spinned which will increase the replicas count based on the specification that we gave. vices versa if the CPU load decreases the deployment gets reverted.

Sepcification:

```

```


### Limit range
will specify the resource limit in namespace level, the pod can overwride this configuration. if the resorce configuraion is not specified in the pod defualtly the pod will tack the resource configuration which specified in limit range.

the resource allocation will priorotised like this.

KUBERNATIES_CONTROLLER -> SPEC_IN_POD > SPEC_IN_LIMIT_RANGE > FULL_RESOURCE_IN_NAME_SPACE

Sepcification:

```

```

### Resource quota
To give limit for the namespace, by this we can avoid a pod taking all avilable resources in host.

Sepcification:

```

```

### Ingress
Exposing services to outside the world, like API gteway
* the communication will happend like user request -> Ingress -> Service -> Pod

* ingress is a common term in networking
* Ingress - Incoming Load
* Egress - Outgoing load 

### ReplicationController
### StatefulSets
### DaemonSets
Can specify like each node should contain a single pod.
Use case: for logging purpose 'fluid' can be added in separate pod and can be attached in every node.
### Volumes
### Fluid:
fluid is a side car container. which is entrypoint for the pod. can be used to collect the standerd out of the node and pass to the message brocker to persist the log.
### persistance volume:
used to persist the logs when publishing event in message brocker.

### Ressiliancy 4j:
used as circuit breacker same as hystricks- retry logic for all transaction, it also will act as sidecar container.
- default response
- circuit breaking

## Microservice paatternss achived via kubernaties
### Distributed logging:
spring cloud slauth used for logging.
logbck used to supply json formated value

Fluid
---------------------------------       -------         ------
|Aggricator   ------> forwader  |------>|Kafka|------->|Splunk|
(message collector)             |       -------         -------
|-------------------------------

* splunk | kibana -> we can use slunk or kibana to view as log dashboard

### Distributed transaction:
SAGA pattern 
* Event based -> transaction rollback done by message brocker
* Carriography -> using rollback aggrigation technique we can achive. event based is better to use

### Pagerduty:
 For allert system
### Promothies === Dynatrace
For monitoring
### API managment service - azur service which manages api request response







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
kubectl delete pod arun-emp-pod				| to delete pod
kubectl get events						| get event in namespace

step:

* Create namespace
> kubectl apply -f arun-namspace.yaml

* switch to namespace using kubens
> kubens arun-namespace

* Image for the pod must be created inside namespace
> sudo docker image build . -t arun-emp:latest

* create pod
> kubectl apply -f arun-emp-pod.yml -> will create the pod

* Port forward for pod
> kubectl port-forward <POD_NAME> 8080:8081 -n <NAMEPACE_NAME>
*Port forward for service
> kubectl port-forward service/arun-emp-service 8080:80

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

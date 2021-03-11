## ISTIO
Service mesh:

It provides transperent and language independant way to flexbily and easyly automate application network function.
The mesh provides critical capabilities including service discovery, load balancing, encryption, observability, traceability, authentication and authorization, and support for the circuit breaker pattern.

#### What?
* one of the service mesh wich achives 
-service mesh managing communication between the microservices
* Sidecar proxy pattern - ENVOY proxt attach with all container as side card
#### Why?
* Automatic loadbalancing
* differnt version pointing - cannary deployment
* Crosscutting functionality
* Throshold limi| rate limit how much load should go to which container
* Matrix collection like http status as 500 or 200
* Security feature | TLS handling - mSL

Features:
* Transaction managment - Circuit breaking
* Security - TLS termination, Policy enforcement, Authentication, Autherization
* Observability - Log collection Matrix

Architecture:
![kubernetes-model-architecture](https://github.com/marun790/Kubernaties/blob/main/istio/image/arch.svg?raw=true)

* Istio having a same architecture as kubernaties, control plane and which will attached with every container as side car and cntrolls all ingresstraffic and do the logging , matrix collection everything

### Components in Istio
#### Control plane
* istioD- Entry pont for istio
* pilot - Convert istiod request to Envoy proxy
* citadel - Security related things
* Gally Configurations for pilot istod

* After insalling Istiod all the component will be in namespace called "istio-system"

#### Trafic management:
Can be achived by "virtual-service" and "destination-rule"

* Virtual service - will handle when to forward the request
* Destination rule - Executing the configuration

#### Virtual service
* virual service will works like a proxy for the pods, which will closely work with "service" so the request to reacch the pod will go like below
Api service ->  Kubelet -> service -> virtual service -> pod
* we can have configurations for url routing and and other routing configuration
* and the URL resolve firs will execute fist

#### Destination rule:
* Chosing which pod to execute
* 

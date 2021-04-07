## Kubernaties workouts
### REF : https://gitlab.com/nanuchi/youtube-tutorial-series/-/tree/master/demo-kubernetes-components
### Video : https://www.youtube.com/watch?v=X48VuDVv0do&t=4015s

* Create a nginx deployment from docker repo
> kubectl create deployment nginix-depl --image=nginix

* edit the deployment and change the replica
> kubectl edit deployment nginx-depl // will give an edit window
> press i change the replica
> save the file

once the above done the replicaset will automatically gets updated 

# creating deployment with below configuration
![kubernetes-model-architecture](https://github.com/marun790/Kubernaties/tutorial-workout/blob/main/images/fmogodb-mongoexpress-comoponents_overview.jpeg?raw=true)
![Communication Flow](https://github.com/marun790/Kubernaties/tutorial-workout/blob/main/images/mogodb-mongoexpress-comoponents_flow.jpeg?raw=true)

For the aove flow we will be needing kubernaties objects below
* mongo-configmap.yaml -> Config map will have the mogo-db connection URL
* mongo-secret.yaml -> credentials for mongo-db
* mongo-db.yaml -> mongo-db pod and corresponding service
* mongo-express.yaml -> will have serivice and mongo-express pod configuration

### mongodb-configmap.yaml
```apiVersion: v1
kind: ConfigMap
metadata:
  name: mongodb-configmap
data:
  database_url: mongodb-service
```
### mongodb-secret.yaml
```
apiVersion: v1
kind: Secret
metadata:
    name: mongodb-secret
type: Opaque
data:
    mongo-root-username: dXNlcm5hbWU=   // value created using below base64 command
    mongo-root-password: cGFzc3dvcmQ=
```
In secrets the values must be base64 encoded to get those valuese use the below command

> echo -n 'username' | base64
> echo -n 'password' | base64

### mongo-db.yaml
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mongodb-deployment
  labels:
    app: mongodb
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mongodb
  template:
    metadata:
      labels:
        app: mongodb
    spec:
      containers:
      - name: mongodb
        image: mongo
        ports:
        - containerPort: 27017                            ## Must match with mongodb-service.targetPort
        env:                                             ## key value pair which loading from secrets
        - name: MONGO_INITDB_ROOT_USERNAME               ## can get from docker repo documentation https://hub.docker.com/_/mongo
          valueFrom:
            secretKeyRef:
              name: mongodb-secret
              key: mongo-root-username
        - name: MONGO_INITDB_ROOT_PASSWORD
          valueFrom: 
            secretKeyRef:
              name: mongodb-secret
              key: mongo-root-password
---                                                     ## File separation for having both configuration in same file
apiVersion: v1
kind: Service
metadata:
  name: mongodb-service
spec:
  selector:
    app: mongodb
  ports:
    - protocol: TCP
      port: 27017                               ## port to export to outside
      targetPort: 27017                         ## port of the container 

```

### mongo-express.yaml
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mongo-express
  labels:
    app: mongo-express
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mongo-express
  template:
    metadata:
      labels:
        app: mongo-express
    spec:
      containers:
      - name: mongo-express
        image: mongo-express
        ports:
        - containerPort: 8081                                 ## must match with mongo-express-service.targetPort
        env:                                                  ## key value pair which loading from secrets
        - name: ME_CONFIG_MONGODB_ADMINUSERNAME             
          valueFrom:
            secretKeyRef:
              name: mongodb-secret
              key: mongo-root-username
        - name: ME_CONFIG_MONGODB_ADMINPASSWORD
          valueFrom: 
            secretKeyRef:
              name: mongodb-secret
              key: mongo-root-password
        - name: ME_CONFIG_MONGODB_SERVER
          valueFrom:                ## key value pair which loading from configmap
            configMapKeyRef:
              name: mongodb-configmap
              key: database_url
---
apiVersion: v1
kind: Service
metadata:
  name: mongo-express-service
spec:
  selector:
    app: mongo-express
  type: LoadBalancer        ## notice te type is load balancer as we will expose  the same to outside the we gave like this
  ports:
    - protocol: TCP
      port: 8081
      targetPort: 8081      ## must match with mongo-express.port
      nodePort: 30000       ## node port will open a port in the node alternate for porf-forward range from 30000 - 32767

```
The excution order is key here, the order should be like below

> kubectl apply -f mongo-secrets.yaml
> kubectl apply -f mongo-configmap.yaml
> kubectl apply -f mongo-db.yaml
> kubectl apply -f mongo-express.yaml

if you wxecute 
> kubectl get pods
the EXTERNAL-IP for the mongo-express-service will be <pending> so we have to assign a public ip for the service to access the mongo-express from browser

> minikube service mongo-express-service -n arun-namespace

the above command will open a browser and will assign a publicIp for mongo-express-service

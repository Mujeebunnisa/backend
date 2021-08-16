## Steps to build

./mvnw package

mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

## Run with jar locally

java -jar target/backend-0.0.1-SNAPSHOT.jar

## Run with docker

docker build -t mujeeb/backend-docker .
docker run -p 8080:8080 -t mujeeb/backend-docker (using the local docker image)

## Deploy on kubernetes and run using minikube

### start minikube

minikube start — vm-driver=hyperkit (default driver is VirtualBox, for mac it is hyperkit)

### set the context to use minikube

kubectl config use-context minikube

### verify that kubectl is configured to communicate with your cluster

kubectl cluster-info

### Without pushing the docker image to the registry we can still deploy on minikube by setting minikube vm as docker host
### (backend-deployment.yaml has imagePullPolicy: Never which will ensure the image is pulled from local , it can be changed to Always when we want to pull
### image from registry)

eval $(minikube docker-env)


### Now build to create docker image for the app
docker build -t mujeeb/backend-docker .

### Now deploy backend on minikube using the namespace.yaml, deployment.yaml and service.yaml

kubectl apply -f kubernetes/namespace.yaml
kubectl apply -f kubernetes/backend-deployment.yaml
kubectl apply -f kubernetes/backend-service.yaml

### check if pods and service is created successfully

kubectl get pods -n mspace
kubectl get svc -n mspace


## To test this service expose this service outside the cluster

uncomment line --- type: LoadBalancer in the backend-service.yaml and then re apply (Just for test)

** The LoadBalancer type makes the Service accessible through the minikube service command
** This automatically opens up a browser window using a local IP address that serves your app and shows the "Welcome" message. To check hello message use/message api
minikube service hello -n mspace


## Note: To run LoadBalancer type in minikube using minikube tunnel(start this service in seperate terminal) without which external IP would not be generated


## Deploy using helm chart
helm install backend backend-helm-chart

### To change the value of name
 helm install  --set envNameValue="<your value>" backend backend-helm-chart




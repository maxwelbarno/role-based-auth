# Role Based Authentication Application

This application demonstrates the capabilities of Spring Security in authenticating users based on their roles

## Technologies

- Java 21
- Spring Boot 3.1.4
- Spring Security
- Docker 24.0.6
- Kubernetes (minikube)
- Maven 3.9.5
- MySQL 8.0.34
- JWT

## Setup

To run this project locally

1. Clone this repository
2. Navigate to the project root directory and install it by running the following command on the terminal:

   ```sh
   ./mvnw clean install -DskipTests
   ```

3. Setup Docker by logging into docker from the terminal.
4. Setup Kubernetes:

   ```sh
   minikube start
   eval $(minikube docker-env)
   ```

5. Setup project database in Kubernetes by navigating to the `k8s` directory and issue the following commands:

   ```sh
   cd /k8s
   kubectl apply -f db-configmap.yaml
   kubectl apply -f db-root-credentials.yaml
   kubectl apply -f db-credentials.yaml
   kubectl apply -f db-deployment.yaml
   ```

   OPTIONAL: You can run the following commands to interact with MySQL:

   ```sh
   kubectl run -it --rm --image=mysql:latest --restart=Never mysql-client -- mysql -h mysql -uroot -ptoor
   ```

   The above command will create a `mysql-client` and present a mysql prompt in the terminal like:

   ```sh
   mysql>
   ```

6. Navigate back to the root directory where the `Dockerfile` is domiciled and build the application image using docker by running:

   ```sh
   cd ..
   docker build -t <your-dockerhub-username>/springboot-k8s-mysql:latest .
   ```

7. Setup the main application by navigating again to the `k8s` folder and issue the following command in the terminal within k8s folder

   ```sh
   kubectl apply -f deployment.yaml
   ```

8. OPTIONAL: clean kubernetes workspace by running the following commands in that order:

   ```sh
   kubectl delete deployments --all
   kubectl delete services --all
   kubectl delete pvc --all
   kubectl delete pv --all
   kubectl delete secrets --all
   kubectl delete configmaps --all
   ```

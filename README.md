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

<p align="left">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,docker,kubernetes,maven,mysql" />
  </a>
</p>

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

   ![Screenshot from 2023-10-25 09-44-34](https://github.com/ywalakamar/role-based-auth/assets/4771875/b9ad8205-e402-4ed3-a9c0-168578044114)

   You can now issue an SQL command and get responses like:
   ![Screenshot from 2023-10-25 09-41-09](https://github.com/ywalakamar/role-based-auth/assets/4771875/782d5a84-c4ae-4d9e-b35e-725388b89fba)

6. Navigate back to the root directory where the `Dockerfile` is domiciled and build the application image using docker by running:

   ```sh
   cd ..
   docker build -t <your-dockerhub-username>/springboot-k8s-mysql:v1 .
   ```

7. Setup the main application by navigating again to the `k8s` folder and issue the following command in the terminal within k8s folder

   ```sh
   kubectl apply -f app-deployment.yaml
   ```

8. You can check the state of your pods, deployments and services by running the following commands in the terminal

```sh
   kubectl get pods
   kubectl get deployments
   kubectl get services
```

![Screenshot from 2023-10-25 09-48-41](https://github.com/ywalakamar/role-based-auth/assets/4771875/8d042fbf-2b47-4bd0-9262-f91b2c22d3f9)

9. OPTIONAL: clean kubernetes workspace by running the following commands in that order:

   ```sh
   kubectl delete deployments --all
   kubectl delete services --all
   kubectl delete pvc --all
   kubectl delete pv --all
   kubectl delete secrets --all
   kubectl delete configmaps --all
   ```

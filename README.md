# Document Management System 

A Web application written in Java usings Spring Boot. It exposes relevant REST services for the frontend which can be found on this link: https://github.com/Igor-Jovic/DocumentManagementSystem-Frontend.   

### Prerequisites

Java, IDE of choice, git, maven, bower, MySql

## Getting Started

In order to get a working copy on your machine open terminal and execute this commands: <br />
1. git clone https://github.com/Igor-Jovic/DocumentManagementSystem-Backend <br />
2. cd DocumentManagementSystem-Backend <br />
3. ./workspace-init.sh <br />

Now, make sure MySql server is running and local credentials are updated in application.properties file. 
To generate database schema go to application.properties and set ddl property to create:
  spring.jpa.hibernate.ddl-auto=create
At last, resolve Maven dependencies and run DocumentManagementSystemApp.java as a Java application. 


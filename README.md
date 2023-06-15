# ildar_mock_server

This is a tutorial project that is a service. It consists of an nginx server that serves static content one at a time. The paths and data of the mock server are different (proxying to a different port). The mock server itself is written in Java. For this project, REST Assured Autotests are written in Java + Wiremock. A pre-built server is deployed using Docker.

## Content

| Folder/file            | Description                                                       | 
|:----------------------|:---------------------------------------------------------------|
| .idea                 | IntelliJ IDEA IDE folder with special preference files |
| **docker_files_mock** | Project files for Docker deployment              |
| out                   | Folder containing a separate jar file with a mock server         |
| src                   | Source code (mock server + autotests)                         |
| target                | Maven Directory                                               |
| mock_server_ildar.iml | IntelliJ IDEA Module Library                                |
| pom.xml               | Main Maven file that describes the project                        |
| README.md             | Current project description file                               |

## System requirements

**To start a project:**

- Docker version 4.20.1+

**To check the performance of the project:**

- Google Chrome Version 114+
- IntelliJ IDEA Community Edition 2023.1+
- Postman v10.15.0+

**To work with the project's source code, we used:**

- IntelliJ IDEA Community Edition 2023.1+
- JDK version 20+
- Maven 4.0.0+

## Instructions for starting the service

1. Download the *docker_files_mock* folder to your local computer.
2. Launch Terminal
3. Go to the *docker_files_mock* folder (e.g. `cd /d F:\Other\docker_files_mock`)
4. Run the image build:
   `docker build -t ildarmock .`
5. Run Container:
   `docker run -d -p 8080:80 ildarmock`

## Manual health check (after service startup)

:white_check_mark: 1. Use your browser to go to the following links:
____

- http://localhost:8080/static/index.html

*Expected result:*    
![index](https://i.ibb.co/VNX7nfL/index.png "index")

____

- http://localhost:8080/static/image.jpg

*Expected result:*    
![image](https://i.ibb.co/Zx596HT/image.png "image")


____

- http://localhost:8080/static/text.txt

*Expected result:*    
![text](https://i.ibb.co/F0JWbxn/text.png "text")


____

:white_check_mark: 2. Submit the following requests to Postman:

____

- GET http://localhost:8080/mock/get_employee

*Expected result:*    
Body:

```    
{    
    "id": "101",    
    "fio": "Ivanov Ivan Ivanovich",    
    "position": "QA engineer",    
    "number": "1024"    
}    
```

____

- DELETE http://localhost:8080/mock/delete_employee?number=fd544d68-1cca-40aa-9cad-a72b30cc634d  
  Params:     
  `number` fd544d68-1cca-40aa-9cad-a72b30cc634d      

*Expected result:*    
Body:

```    
{    
    "id": "",    
    "status": "DELETED"    
}    
```

Where the value of the "number" field is taken from the POST request.

____

- POST http://localhost:8080/mock/create_employee    
  Body:

```    
{    
    "fio": "Alliamov Ildar Zinniatullaevich",    
    "position": "QA Engineer"    
} 
```  

*Expected result:*

```    
{    
    "fio": "Alliamov Ildar Zinniatullaevich",    
    "position": "QA Engineer",    
    "number": "fd544d68-1cca-40aa-9cad-a72b30cc634d"    
}    
```

Where the value of the "number" field is a randomly generated uuid.

## Checking the health using autotests (after starting the service)

1. Download the source folders of the project (everything except *docker_files_mock*, *README.md*) to your local machine.
2. Open the project in IntelliJ IDEA
3. Right click on the java folder along the /src/test/java path in the project (left pane)
4. Click `Run All Tests`.
5. Verify that the autotests pass successfully

:white_check_mark: *Successful execution of autotests:*

![Successful Autotests](https://i.ibb.co/fMYg671/Tests.png "Tests")


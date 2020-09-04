# ildar_mock

Это учебный проект, представляющий из себя сервис. Он состоит из nginx-сервера, выдающего статический контент по одному пути и данные mock-сервера - по другому (проксирование на иной порт). Сам mock-сервер написан на Java. Для данного проекта составлены REST Assured автотесты, написанные на Java + Wiremock. Разворачивание готового сервера происходит с помощью Docker.

## Содержимое

| Папка/файл | Описание | 
|:----|:----|
| .idea | Папка IDE IntelliJ IDEA, содержащая специальные файлы настроек |
| **docker_files_mock** | Файлы проекта для разворачивания с помощью Docker |
| out | Папка, где хранится отдельный Jar файл с mock-сервером |
| src | Исходный код (mock-сервер + автотесты) |
| target | Директория Maven |
| mock_server_ildar.iml | Библиотека модуля IntelliJ IDEA |
| pom.xml | Основной Maven файл, описывающий проект |
| README.md | Текущий файл с описанием проекта  |

## Системные требования

**Для запуска проекта:**    
- Docker version 2.3.0.4+    

**Для проверки работоспособности проекта:**
- Google Chrome Version 80+ 
- IntelliJ IDEA Community Edition 2020.2+     
- Postman v7.31.1+

**Для работы с исходным кодом проекта использовались:**    
- IntelliJ IDEA Community Edition 2020.2+    
- JDK version 1.5+    
- Maven 3.1+    

## Инструкция по запуску сервиса

1. Скачать папку *docker_files_mock* на локальный компьютер
2. Запустить терминал
3. Перейти в папку *docker_files_mock* (например, `cd /d  F:\Other\docker_files_mock`)
4. Запустить сборку образа: 
`docker build -t ildarmock .`
5. Запустить контейнер: 
`docker run -d -p 8080:80 ildarmock`

## Проверка работоспособности вручную (после запуска сервиса)

:white_check_mark: 1. Перейти по следующим ссылкам с помощью браузера:    
____
- http://localhost:8080/static/index.html    

*Ожидаемый результат:*    
![index](https://i.ibb.co/VNX7nfL/index.png "index")
    
____

- http://localhost:8080/static/image.jpg    

*Ожидаемый результат:*    
![image](https://i.ibb.co/Zx596HT/image.png "image")

    
____

- http://localhost:8080/static/text.txt    

*Ожидаемый результат:*    
![text](https://i.ibb.co/F0JWbxn/text.png "text")

    
____

:white_check_mark: 2. Отправить в Postman следующие запросы:    
    
____

- GET http://localhost:8080/mock/get_employee    

*Ожидаемый результат:*    
Body:     
```    
{    
    "id": "101",    
    "fio": "Иванов Иван Иванович",    
    "position": "Специалист по тестированию",    
    "number": "1024"    
}    
```
   
____

- DELETE http://localhost:8080/mock/delete_employee?id=1011212    
Params:     
`KEY` id      
`VALUE` 1011212    

*Ожидаемый результат:*    
Body:     
```    
{    
    "id": "1011212",    
    "status": "DELETED"    
}    
```
    
____
    
- POST http://localhost:8080/mock/create_employee    
Body:     
```    
{    
    "fio": "Аллямов Ильдар Зиннятуллаевич",    
    "position": "Специалист по тестированию"    
} 
```  
    
*Ожидаемый результат:*    
```    
{    
    "fio": "Аллямов Ильдар Зиннятуллаевич",    
    "position": "Специалист по тестированию",    
    "number": "963451a8-0c1b-4c52-9923-a0e75265f926"    
}    
```
Где значение поля "number" - случайно сгенерированный uuid.    

## Проверка работоспособности с помощью автотестов (после запуска сервиса)

1. Скачать папки с исходным кодом проекта (всё, кроме *docker_files_mock*, *README.md*) на локальный компьютер
2. Открыть проект в IntelliJ IDEA
3. Нажать ПКМ на папке java по пути /src/test/java в Project (окно слева)
4. Нажать `Run 'All Tests'`
5. Убедиться в успешном прохождении автотестов

:white_check_mark: *Успешное выполнение автотестов:*    
    
![Успешные автотесты](https://i.ibb.co/fMYg671/Tests.png "Tests")


# Logger Spring AOP Example

## Назначение проекта
Проект служит для демонстрации логирования приложения с помощью Spring-AOP и Log4J2.

## Стек
- `Spring Boot`
- `Spring Data Jpa`
- `Spring-AOP`
- `PostgreSQL`
- `Log4J2`
- `Swagger`
- `Docker Compose`

### Описание сервиса
Сервис предоставляет api для работы с сущностями `User` и `Order`.
Позволяет выполнять базовые `CRUD` операции в `PostgreSQL`.
По умолчанию по адресу  `http://localhost:8080/swagger-ui.html` доступна документация.

### Структура базы данных:

![db_structure](/src/main/resources/static/image/relationShema.png)

### Логирование
1) При выполнении запроса по любой доступной ссылке, запросу присваивается случайный UUID идентификатор который 
упрощает навигацию по логам и поиск неисправностей, при возникновении ошибки идентификатор запроса прикрепляется
к ответу сервера.
![error_response_example](/src/main/resources/static/image/errorResponse.png)
2) В консоль выводятся параметры запроса, ответ и время выполнения и сгенерированный идентификатор запроса.
![normal_log_example](/src/main/resources/static/image/normalOperation.png)
3) При возникновении ошибки в ходе выполнения запроса, в лог выдается сообщение об ошибке и сгенерированный идентификатор запроса
![exception_log_example](/src/main/resources/static/image/exception.png)
### Запуск

Запустить:
```
docker-compose up
```
Порты занимаемые по умолчанию:
- 8080 
- 5432


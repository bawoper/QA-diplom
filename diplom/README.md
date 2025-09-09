
# Дипломный проект по профессии «Тестировщик»


## Отчет по итогам тестирования
[https://github.com/bawoper/QA-diplom/issues/12#issue-3399587398](https://github.com/bawoper/QA-diplom/issues/12)
## План автоматизации 
https://github.com/bawoper/QA-diplom/blob/main/diplom/Docs/plan.md
## Allure report 
https://github.com/bawoper/QA-diplom/blob/main/diplom/Docs/Allure%20report.png

### Инструкция по запуску


Создать Gradle проект в нужной папке.

С помощью команды git init создать локальный репозиторий.

С помощью команды git clone https://https://github.com/bawoper/QA-diplom  клонировать удаленный.

Запустить Docker Desktop.

С помощью команды docker-compose up --build создать контейнеры и запустить их.

С помощью команды java -jar artifacts/aqa-shop.jar запустить тестируемое приложение.

Запустить тесты с помощью команды ./gradlew clean test

***Для тестирования с проверкой БД.***

**Запустить приложение:**


Для запуска приложения с базой данных mysql выполнить команду:

java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar

Для запуска приложения с базой данных postgres выполнить команду:

java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar

**Запустить тесты:**

Для запуска тестов с базой данных mysql выполнить команду:

gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app

Для запуска тестов с базой данных postgres выполнить команду:

gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app

**Сформировать отчеты командой:**

gradlew allureReport

**Открыть отчеты в браузере командой:**

gradlew allureServe

# Create a Web API and database with Spring
[![web](https://img.shields.io/static/v1?logo=heroku&message=Online&label=Heroku&color=430098)](https://chinook-assignment-database.herokuapp.com/home)

This project was an assignment with the end-goal being to create and deploy a REST Web API and PostgreSQL databse with spring in Heroku.

## Background
For this assignment we were required to make an app using Spring in Java with different dependencies mentioned in "Built With".
The database is build from the ground up using PostgreSQL and implements seeder data on application start. We used Java to create a REST API and documented it with Swagger and Postman.


## How To Use

### API Endpoints - SwaggerIO
When the application loads up you will see the swagger interface. Here you can test the different endpoints, and we would like to give some pointers to get the correct responses.

#### PUT Endpoints
For every PUT endpoint which points to updating a certain franchise, movie or actor. <br />
Please leave the relation arrays for example: "actors": [  "String" ] EMPTY as following: "actors": [] <br />
In the same request body you need to set the "franchise" to NULL unless you know any of the other franchises described in the database.

#### POST Endpoints
For every POST endpoint which points to creating a certain franchise, movie or actor. <br />
Please follow the same guideline for as stated for the PUT Endpoints and leave relation arrays empty.

### API Endpoints - Postman


## Built With
[IntelliJ IDEA](https://www.jetbrains.com/idea/)

[Spring Framework](https://spring.io/)
 - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

[Springdoc OpenAPI](https://springdoc.org/)

[Swagger](https://swagger.io/docs/specification/about/)

[Postman](https://www.postman.com/)

[PostgreSQL](https://www.postgresql.org/)

[Docker](https://www.docker.com/)

## Credits
[Iljaas Dhonre](https://github.com/iljaasdhonre)

[Richie Schuurman](https://github.com/RichieSchuurman)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)

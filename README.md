# Grupo - 10 / LEIC 41 - D
- Rodrigo Meneses - 50542
- Rafael Nicolau - 50546
- Mário Carvalho - 50561

## Phase 1

### Introduction

This document contains the relevant design and implementation aspects of LS project's first phase.

## Modeling the database

#### Conceptual model

The following diagram holds the Entity-Relationship model for the information managed by the system.

![Conceptual Model](assets/ModeloEA.png)

We highlight the following aspects:
-  Our module does not use a different entity for developer since all that it does can be done with a simple attribute.
-  Since a game can have a multitude of genres we opted to create that entity (genre) to simplify the code.

The conceptual model has the following restrictions:
- Player's email and token as AK.
- Game's name as AK.
- GamingSession's date can´t be inserted with a date newer than the current date.
- Capacity must be higher than 0.

#### Physical Model

The physical model of the database is available in [link to the SQL script with the schema definition](src/main/sql/createSchema.sql).

We highlight the following aspects of this model:
- Gaming Session state is defined as true if the date is newer than the current date.

## Software organization

#### Open-API Specification

[Open-API Specification YAML file](docs/API-docs%201.0.yaml)

In our Open-API specification, we highlight the following aspects:
- All the possible routes you can do as a user
- The correct way to send requests to our server
- Possible errors you could have sending a request and fixes
- Auxiliary images to show an example request

#### Request Details

To help visualize our implementation of the request schemes we made this diagram
![Conceptual Model](assets/requestDetails.png)

All requests are received in the server file that divides all requests by its own URL path to the corresponding API section. All those sections correlate to the corresponding entity that is being used at the moment.
For example, if I want to create a new player it would send that request to the player's API file.

In the API section we extract all the information that the user sent us and by giving it to the Services section.
The information that we send to services could be in authorization(bearer token), body, query, path...
It's in this section where we send all the HTTP responses and catch even the responses.
We can then return a response according to the return of the services

In the Services section where we turn all the information received by the API to the corresponding object by using.
It's also here where we check if the user is authenticated with a bearer token that fits with a player in our database.
Also, we check here if the parameters that are sent are valid according to the request made.
We also send most of our Exceptions here so that the API can catch them and turn them into HTTP error responses.
It's here where we apply all the logic and send the objects to the Data section.

In the Data section we modify the data according to the corresponding request unless the request is invalid in some sense that could end up causing some cause of exceptions for example if u try to create a player with the same email it will cause an exception(because the email is unique)

We also created an inline function in Response.json(body: T) so that the content type would always be JSON in the responses

[Describe how a request goes through the different elements of your solution]

[Describe the relevant classes/functions used internally in a request]

[Describe how and where request parameters are validated]

### Server --> API --> Services --> Data


#### Connection Management

To make sure that everything is stable we created all the sections independently to one another.
What we mean is that to create the API class you need to send the as an argument class of services that is going to be connected and to create the Services class you need to send the db that is going to be affected by the requests.
We create the db that is going to be used in the server file and then we connect to all the sections that are going to be used in these lines "val db = DataMem() /n val api = API(Services(db))"  then in all the possible routes we call the corresponding functions to this api object.

[Describe how connections are created, used, and disposed, namely its relation with transaction scopes].

#### Data Access

To help with visualizing our module we created this image that resumes our interface for Data Acess.
![Conceptual Model](assets/DataAcess.png)

To help in Data access we created an Interface Storage that contains each Interface storage  (players, gamingSessions, games).
Now as we can see in this image this Interface is used to help define each class in memory or Postgres. That has its own player, gaming sessions, and game classes associated with it.
This way we can confirm that each DB uses all the functions defined in the Interface Storage.
[Describe any created classes to help on data access].

[Identify any non-trivial used SQL statements].

#### Error Handling/Processing

For error handling, we opted to create an inline function that uses try and catch for each function in the API section and whenever there is an error of some kind we send out an exception that is translated to a response (via our function httpException(e)) with the corresponding HTTP error status for each different exception.
For some errors, we even created new exceptions to correlate to a different HTTP error status.

#### Critical Evaluation

- Enumerate the functionality that is not concluded and the identified defects.
- Identify improvements to be made in the next phase.


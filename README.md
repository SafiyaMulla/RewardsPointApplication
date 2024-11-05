# Rewards Points Application

## Overview

This application is a rewards points system that allows retailers to calculate reward points for their customers based on their purchases. Customers earn points based on transaction amounts with specific condition. 

## Features

- **Calculate Monthly Rewards**: Retrieve total reward points earned by a customer for each month.
- **Calculate Yearly Rewards**: Retrieve total reward points earned by a customer for a specified year.
- **RESTful API**: Expose endpoints for accessing rewards information.

## Tech Stack

- **Backend**: Spring Boot
- **Database**: MongoDB
- **Testing**: JUnit, Mockito
- **Build Tool**: Maven

## Requirements

- Java 08 or higher
- Maven
- MongoDB

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/SafiyaMulla/RewardsPointApplication.git
   cd RewardsPointApplication
2. Build the project:   
   mvn clean install
3. Run the application:
   mvn spring-boot-run
     
## Endpoints

- ** 1. Get Monthly points**

- **Request** :
http://localhost:8080/api/rewards/monthly?customerId=103&year=2024

- **Response** :

  Status Code: `200 OK`
  
  Response Body: (Success):

```
{
    "MARCH": 0,
    "MAY": 0, 
    "OCTOBER": 0,
    "NOVEMBER": 90,
    "SEPTEMBER": 0,
    "APRIL": 0,
    "JULY": 0,
    "JANUARY": 0,
    "AUGUST": 0,
    "JUNE": 0,
    "FEBRUARY": 0,
    "DECEMBER": 0
}
```
- ** 2. Get Total points**

- **Request** :
http://localhost:8080/api/rewards/yearly?customerId=103&year=2024

- **Response** :

  Status Code: `200 OK`
  
  Response Body: (Success):

```
Total Rewards: 90
```

## Sample Data
This application includes sample data for demonstration purposes. You can find sample data in 'sample.json' file.

## Author
Safiya Mulla.

## License


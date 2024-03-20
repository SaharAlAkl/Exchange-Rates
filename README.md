This project is about testing RestAPIs automation.
Serenity BDD is implemented for test reporting in index.html format (this is returned in terminal)

Given the API: https://open.er-api.com/v6/latest/USD returns the USD rates against multiple currency.


Main points in the project:
---------------------------
1- Create a test framework using Java language framework. using a BDD approach.
2- Test Cases to cover the acceptance criteria below:
# API call is successful and returns valid price.
# Check the status code and status returned by the API response.
    - API could return muliple statuses like SUCCESS, FAILURE etc. Make sure this is catered for.
# Fetch the USD price against the AED and make sure the prices are in range on 3.6 – 3.7
# Make sure API response time is not less than 3 seconds then current Ɵme in second.
    - Timestamp is returned in the API response.
# Verify that 162 currency pairs are returned by the API.
# Make sure API response matches the Json schema
    - Generate a schema from the API response.


Dependencies:
------------
- Java 8
- Maven
- Serenity BDD
- RestAssured
- JSON Schema Validator


Building the project:
---------------------
``` 
mvn clean install
```


Running the tests:
------------------
``` 
mvn clean verify
```


Project Structure:
------------------
`
├── src
│   ├── test
│       └── java
│           └── SDETAssessment.java
│       └── schema
│           └── schema.json
├── pom.xml                                
└── README.md
`
# Fraud-Rule-Engine-Service
 A system that processes categorised transaction events and flags potential fraud.

# Fraud Rule Engine Service

A Spring Boot microservice that evaluates incoming transactions in real-time and flags them as fraud or not.

Built for the Capitec Technical Assessment - demonstrates clean rule-based design, API testing, containerisation, and API documentation.

## How It Works

The service exposes a single REST endpoint. Each transaction is passed through a chain of 4 independent fraud rules. If any rule triggers, the transaction is flagged as fraud with a reason. Otherwise, it is not flagged.

### Current Rules

- LargeAmountRule - Blocks / flags any transaction where amount is greater than 20000
- OddHourRule - Flags any transaction between 12am and 4am (hour 0 to 4)
- ForeignLocationRule - Flags any transaction where location is outside South Africa (SA)
- FrequencyRule - Flags if a customer does more than 3 transactions in the last hour (transactionsInLastHour greater than 3)

Rules are decoupled - each rule implements the FraudRule interface, so new rules can be added without changing existing code.

### API Contract

Endpoint: POST /api/fraud/check

Request Body:
```json
{
"id": "TXN-001",
"customerId": "CUST-001",
"amount": 25000,
"location": "USA",
"hour": 2,
"transactionsInLastHour": 5
}

Response Body:

{
"id": "TXN-001",
"customerId": "CUST-001",
"amount": 25000,
"location": "USA",
"hour": 2,
"transactionsInLastHour": 5,
"isFlagged": true,
"reason": "Large amount over 20000; Odd hour transaction; Foreign location; High frequency: more than 3 transactions in last hour; "
}

Field descriptions:
- id - Unique identifier for the transaction
- customerId - Unique identifier for the customer
- amount - Transaction amount
- location - Transaction location, e.g. SA, USA
- hour - Hour of transaction, 0 to 23
- transactionsInLastHour - How many transactions the customer did in the last hour
- isFlagged - true if any rule triggered, false if clean
- reason - Why the transaction was flagged, empty if clean

Tech Stack

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Web
- Docker / Rancher Desktop
- Postman for manual API testing
- Swagger / OpenAPI for API documentation
- JUnit 5 for unit tests

Project Structure

Fraud-Rule-Engine-Service/
├── src/main/java/com/bank/fraud/
│ ├── domain/ Transaction.java
│ ├── rules/ LargeAmountRule, OddHourRule, ForeignLocationRule, FrequencyRule
│ ├── service/ FraudDetectionService.java
│ ├── controller/ FraudController.java
│ └── FraudRuleEngineApplication.java
├── src/test/
├── postman/collections/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md

How to Build, Run and Test

1. Prerequisites

- Java 17 or higher
- Maven
- Docker + Rancher Desktop (for container run)
- Postman (optional, for manual testing)

Check versions:

java -version
mvn -version
docker --version

2. Build the Project

mvn clean install

This compiles the code and runs all unit tests.

3. Run Locally (Without Docker)

mvn spring-boot:run

App will start on: http://localhost:8080

Verify it is running:

curl -X POST http://localhost:8080/api/fraud/check -H "Content-Type: application/json" -d '{"id":"TXN-002","customerId":"CUST-001","amount":5000,"location":"SA","hour":10,"transactionsInLastHour":1}'

Expected: isFlagged false

4. Run with Docker (Rancher Desktop)

Make sure Rancher Desktop is running and set to dockerd (moby) container runtime.

Build the image:

docker build -t fraud-rule-engine-service.

Run the container:

docker run -p 8080:8080 fraud-rule-engine-service

Or using Docker Compose:

docker-compose up --build

App will be available at: http://localhost:8080

Stop the container:

docker-compose down

5. Run Tests

Run all unit tests:

mvn test

Tests cover:
- LargeAmountRule flags greater than 20000, passes 20000 and below
- OddHourRule flags hour 0 to 4, passes hour 5 to 23
- ForeignLocationRule flags location outside SA, passes SA
- FrequencyRule flags transactionsInLastHour greater than 3, passes 3 and below
- FraudDetectionService returns correct isFlagged and reason

API Testing

Option A: Postman

A ready-made collection is included in this repo:

File: postman/collections/Fraud-Rule-Engine-Service.postman_collection.json

How to use:
1. Open Postman - Import - Select the file above
2. Make sure the app is running on http://localhost:8080
3. Run the requests in order:
- 1 - Clean Transaction - Sends R5000, SA, 10am, 1 transaction - expect isFlagged false
- 2 - Large Amount - Sends R25000 - expect isFlagged true
- 3 - Odd Hour - Sends 2am - expect isFlagged true
- 4 - Foreign Location - Sends USA - expect isFlagged true
- 5 - High Frequency - Sends transactionsInLastHour 5 - expect isFlagged true

Option B: Swagger / OpenAPI

Swagger UI is available when the app is running. No Postman needed.

Open in your browser:

http://localhost:8080/swagger-ui/index.html

From Swagger you can:
- See all endpoints and schemas
- Click Try it out - Enter a JSON body - Execute
- See the live response without writing code

Option C: cURL

Clean transaction (not flagged):

curl -X POST http://localhost:8080/api/fraud/check -H "Content-Type: application/json" -d '{"id":"TXN-002","customerId":"CUST-001","amount":5000,"location":"SA","hour":10,"transactionsInLastHour":1}'

Fraud transaction - triggers all 4 rules:

curl -X POST http://localhost:8080/api/fraud/check -H "Content-Type: application/json" -d '{"id":"TXN-001","customerId":"CUST-001","amount":25000,"location":"USA","hour":2,"transactionsInLastHour":5}'

Design Decisions and Assumptions

- Rule-based design: Each rule implements a common FraudRule interface, so new rules can be added without modifying FraudDetectionService.
- Thresholds are hardcoded: Amount threshold (20000), odd hours (0-4), SA check, and frequency limit (3/hour) are hardcoded for the assessment. In production these would be externalised to http://application.yml or a config service.
- Frequency is passed in: transactionsInLastHour is supplied in the request for simplicity. In production this would be calculated from a database or Redis by counting the customers recent transactions.
- No authentication: The endpoint is open for assessment purposes. In production it would sit behind an API Gateway with auth.

Future Improvements

- Externalise thresholds to config
- Calculate frequency from Redis / database instead of request input
- Add more rules (e.g. Blacklist, Card-not-present, Velocity)
- Add integration tests with Testcontainers
- Add CI/CD pipeline (GitHub Actions)
- Add structured logging and metrics

Author

Nolwazi Mrobongwane

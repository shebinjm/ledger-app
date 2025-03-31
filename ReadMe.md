# Ledger App

Spring Boot App for managing accounts and transactions. It allows creating accounts, depositing and withdrawing funds, checking balances, and viewing transaction history.

## How to Run

### Requirements
- Java 17 or later
- Maven

### Steps to Run
```bash
git clone https://github.com/shebinjm/ledger-app.git
cd ledger-app
mvn clean install
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## API Examples

### Create Account
```bash
curl -X POST http://localhost:8080/api/v1/accounts      -H "Content-Type: application/json"      -d '{"id": "account1", "name": "Sam"}'
```

### Get All Accounts
```bash
curl http://localhost:8080/api/v1/accounts
```

### Delete Account
```bash
curl -X DELETE http://localhost:8080/api/v1/accounts/account1
```

### Deposit
```bash
curl -X POST "http://localhost:8080/api/v1/ledger/deposit/account1?amount=200"
```

### Withdraw
```bash
curl -X POST "http://localhost:8080/api/v1/ledger/withdraw/account1?amount=100"
```

### Get Balance
```bash
curl http://localhost:8080/api/v1/ledger/balance/account1
```

### Get Transaction History
```bash
curl http://localhost:8080/api/v1/ledger/transactions/history/account1
```


## Running Tests
To run all tests:
```bash
mvn test
```
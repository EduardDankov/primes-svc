# Primes Service
Server side of Primes messenger.

## About

**Primes** is a private messenger developed as a personal project. Its goal is to provide a secure way to communicate between individuals. The project contains of 3 main modules:\
- **User module**. Responsible for user management and authentication.
- **Chat module**. Helps to create chats with other users.
- **Message module**. Handles message creation inside chats.

## Build

To build the application, follow these steps:

1. Ensure Java SDK 21 or later is installed.
```shell
java -version
javac -version
```

2. Clone the repository on local machine.
```shell
git clone https://github.com/EduardDankov/primes-svc.git
```

3. Navigate to the project's root directory.
```shell
cd primes-svc
```

4. Build the application using Gradle. This step also executes unit tests.
```shell
./gradlew clean build
```

5. Prepare a run script, which applies required environment variables:
- `APPLICATION_NAME` - the name of the application in logs
- `APPLICATION_PORT` - the port which application will use
- `DB_NAME` - database name
- `DB_PASSWORD` - database password
- `DB_URL` - database URL
- `DB_USER` - database user

6. Run the application.
```shell
java -jar /path/to/executable/primes.jar
```


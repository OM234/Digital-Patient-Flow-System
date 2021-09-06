# Digital Patient Flow System (a.k.a DigiHealth)
## By Osman Momoh

## What this project does

This is a program for recording patient information, including demographics, vital signs, assessments, and medications.

## Why I made this

As a healthcare professional and computer science student, I was looking for fun personal projects that combine my interests.

## How I made this

- This project is written in Java and MySQL. 
- Build tool is Gradle.
- DB is run with docker
- UI is done with JavaFX library. 
- Architectures used are MVC and 3-tier architectures.

## How to use it

- Make sure to have docker-compose installed
- Navigate to the latest folder (e.g. Iteration V)
- Run `docker-compose up -d` this will trigger the db to start at `localhost:3307` with `username: 'user'` and `password: 'password'`. The db is initialized with a schema located at /sql/CreateTables.sql
- Run gradle command `gradlew dbPopulate:run` this will populate the tables. Only run this on first run
- Run the application with `gradlew application:run`

- Stop the db with `docker-compose down`

## Final notes

This program is at a beta phase. It runs well, but more features would be necessary before it could be properly implemented. 

## Program Screenshots

![Login Screen](/Iteration%20IV/README%20images/LoginScreen.png)
![](/Iteration%20IV/README%20images/MainScreen.png)
![](/Iteration%20IV/README%20images/MedicationsView.png)
![](/Iteration%20IV/README%20images/NewMedication.png)
![](/Iteration%20IV/README%20images/NewPatientNote.png)
![](/Iteration%20IV/README%20images/NewPatientOrUnit.png)
![](/Iteration%20IV/README%20images/PatientNotes.png)
![](/Iteration%20IV/README%20images/PatientSummary.png)


# IAC-event-tracker-service

This service represents the Restful API to track all events emitted or sent by the SDK application. Picked Java as the tech stack with Maven as the build automation tool and Spring Boot framework.

This service exposes three endpoints outline below:

- POST /event
    -  This endpoint gets an event object in the form of a Json payload with the following fields:
    
    
     {
      "name": "session_start",
      "type": "sessionEvent",
      "version": "1.2",
      "deviceType": "Galaxy 10",
      "deviceFamily": "Samsung Galaxy",
      "os": "Android",
      "country": "USA",
      "city": "NYC",
      "carrier": "Verizon"
     }
      
I took the liberty to store all events in an in-memory database (H2) for the sake of being thorough. See section below with instructions on how to access H2 online console.

- GET /event/{id}
    - Retrieve corresponding event data matching the `id` sent as a URL variable.
    
    
    curl -X GET http://localhost:8080/event/someId -H 'cache-control: no-cache'


- GET /event/
    - Retrieve all events data in DB. For brevity, this endpoint doesn't provide any filtering, sorting, and pagination.
    
    
    curl -X GET http://localhost:8080/event -H 'cache-control: no-cache'
 
  
##### Run application maven command (CLI):
-- mvn clean package -DskipTests && java -jar target/iac-event-service-1.0-SNAPSHOT.jar

Command above with package the application and run it, It launches Tomcat server which is accessible at localhost:8080


##### Curl command to track/create event (sample command):
    curl -X POST http://localhost:8080/event \
      -H 'Content-Type: application/json' \
      -H 'cache-control: no-cache' \
      -d '{
        "name": "session_start",
        "type": "sessionEvent",
        "version": "1.2",
        "deviceType": "Galaxy 10",
        "deviceFamily": "Samsung Galaxy",
        "os": "Android",
        "country": "USA",
        "city": "NYC",
        "carrier": "Verizon"
      }

#### H2 Console access:
To view all event data stored in the in-memory database H2, follow these steps:
- Login to H2 console at http://localhost:8080/h2-console/
- Username = iac
- Select `EVENT` table and click `Run` button
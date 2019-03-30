# IAC-event-tracker-service

##### Run application maven command:
-- mvn clean package && java -jar target/iac-event-service-0.0.1-SNAPSHOT.jar


##### Curl command to track/create event:
curl -X POST \
  http://localhost:8080/event \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 0b4fa3cb-bc6f-472f-8a0f-66a45b3a7274' \
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
}'
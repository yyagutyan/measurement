# Docker Compose Setup for Central and Warehouse Applications

This repository contains Docker Compose configuration to run the Central and Warehouse applications with Kafka and Zookeeper.

## Prerequisites

- Docker installed on your machine
- Docker Compose installed on your machine

## Steps to Build and Run

### Run docker compose
   ```bash
   docker-compose up
   ```

 ### Run Central Application
 ```bash
 cd central/
./gradlew bootRun
```


### Run Warehouse Application
 ```bash
 cd warehouse/
./gradlew bootRun
```

### Temperature with alert
 ```bash
 echo -n 't1,45' | nc -u localhost 3344  
```

### Humidity with alert
 ```bash
 echo -n 'h1,55' | nc -u localhost 3345  
```
   

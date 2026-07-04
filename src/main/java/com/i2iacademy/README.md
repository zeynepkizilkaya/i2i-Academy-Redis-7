# Redis Cache Demo

A simple Java application demonstrating how to use Redis as an in-memory data
store. The project sets up Redis and RedisInsight using Docker, then uses the
Jedis client to insert and retrieve 10,000 `Person` objects.

## Tech Stack
- Java 21
- Maven
- Jedis 5.1.3 (Redis client)
- Jackson (JSON serialization)
- Docker & Docker Compose
- Redis + RedisInsight

## Project Structure
```
src/main/java/com/i2iacademy/
├── Person.java   # Simple data model (id, name, age)
└── Main.java     # Connects to Redis, inserts and retrieves 10,000 records
```

## Prerequisites
- Docker Desktop
- JDK 21+
- Maven

## How to Run

### 1. Start Redis and RedisInsight
```bash
docker-compose up -d
```

This starts:
- **Redis** on port `6379`
- **RedisInsight** (GUI) on port `5540`

### 2. Connect RedisInsight to Redis
Open `http://localhost:5540` in your browser and add a new database using:
```
redis://default@redis:6379
```

### 3. Run the Java application
```bash
mvn clean package
java -jar target/redis-cache-demo-1.0.0.jar
```

Or simply run `Main.java` directly from your IDE.

## What the Application Does
1. Connects to the local Redis instance.
2. Creates 10,000 dummy `Person` objects and stores each one in Redis as a
   JSON string, using the key format `person:{id}`.
3. Reads all 10,000 records back from Redis and prints the first 5 to the
   console to verify correctness.
4. Prints the total number of keys stored in Redis (`DBSIZE`) and the time
   taken for both the insert and read operations.

## Sample Output
```
Redis connection: PONG
10000 records inserted. Time: 5166 ms
Retrieved: Person{id=1, name='Person1', age=21}
Retrieved: Person{id=2, name='Person2', age=22}
Retrieved: Person{id=3, name='Person3', age=23}
Retrieved: Person{id=4, name='Person4', age=24}
Retrieved: Person{id=5, name='Person5', age=25}
10000 records retrieved. Time: 4372 ms
Total keys in Redis: 10000
```

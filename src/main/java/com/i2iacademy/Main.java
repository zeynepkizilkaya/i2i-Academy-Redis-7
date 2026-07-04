package com.i2iacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

public class Main {

    private static final int TOTAL_RECORDS = 10_000;
    private static final String KEY_PREFIX = "person:";

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (Jedis jedis = new Jedis("localhost", 6379)) {

            System.out.println("Redis connection: " + jedis.ping());

            long insertStart = System.currentTimeMillis();
            for (int i = 1; i <= TOTAL_RECORDS; i++) {
                Person person = new Person(i, "Person" + i, 20 + (i % 50));
                String json = mapper.writeValueAsString(person);
                jedis.set(KEY_PREFIX + i, json);
            }
            long insertEnd = System.currentTimeMillis();
            System.out.println(TOTAL_RECORDS + " records inserted. Time: " + (insertEnd - insertStart) + " ms");

            long readStart = System.currentTimeMillis();
            for (int i = 1; i <= TOTAL_RECORDS; i++) {
                String json = jedis.get(KEY_PREFIX + i);
                Person person = mapper.readValue(json, Person.class);
                if (i <= 5) {
                    System.out.println("Retrieved: " + person);
                }
            }
            long readEnd = System.currentTimeMillis();
            System.out.println(TOTAL_RECORDS + " records retrieved. Time: " + (readEnd - readStart) + " ms");

            System.out.println("Total keys in Redis: " + jedis.dbSize());
        }
    }
}
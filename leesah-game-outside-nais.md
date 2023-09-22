# Running a Leesah Game with participants running outside the NAIS platform

## Quizmaster nais.yml 

**env**
```bash
- name: KAFKA_BROKERS
value: "nav-integration-test-kafka-nav-integration-test.aivencloud.com:26484"
- name: KAFKA_TRUSTSTORE_PATH
value: "/var/run/trust/client.truststore.jks"
- name: KAFKA_KEYSTORE_PATH
value: "/var/run/key/client.keystore.p12"
- name: KAFKA_CREDSTORE_PASSWORD
value: "whatever"
```

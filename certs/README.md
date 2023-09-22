# Credentials for LEESAH

## Using AVN

```bash
# Log in
avn user login <you@example.com>

# Create certs
avn service user-kafka-java-creds nav-integration-test-kafka --username leesah-quiz-master -d .
```

## Create k8s secret

```bash
kubectl apply secret generic leesah-certs --from-file=keystore.p12=client.keystore.p12 --from-file=truststore.jks=client.truststore.jks
```

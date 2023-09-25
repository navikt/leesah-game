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
kubectl --context prod-gcp --namespace leesah-quiz create secret generic leesah-certs --from-file=keystore.p12=client.keystore.p12 --from-file=truststore.jks=client.truststore.jks
```

Hvis hemmeligheten finnes (som den nok gjør), så må den slettes først.

```bash
kubectl --context prod-gcp --namespace leesah-quiz delete secret leesah-certs
```

Og så må appene rulleres (vi bare dreper alt i namespace leesah-quiz).

```bash
kubectl --context prod-gcp --namespace leesah-quiz delete pods --all
```

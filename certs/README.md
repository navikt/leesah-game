# Credentials for LEESAH

Det er kun medlemmer av NAIS som har tilgang til Aiven console som kan lage sertifikater.
For å få et nytt sertifikat så kan man ta kontakt med en i NAIS og peke de på dette repoet.
Enkleste er kanskje å ta kontakt med Kyrre eller Morten som har kjennskap til dette prosjektet.

Skal man opprette nye topics så anbefaler vi at man bruker et Python-script i [nais/kafkarator](https://github.com/nais/kafkarator/blob/master/scripts/leesah_quiz_creds.py).
Denne vil opprette topics, og ACL, i tilegg vil den lage en Yaml-fil med sertifikater, topics, og egen student-bruker.
Yaml-filen blir brukt av [navikt/leesah-game-cert-server](https://github.com/navikt/leesah-game-cert-server).

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

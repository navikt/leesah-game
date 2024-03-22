# Credentials for LEESAH

Det er kun medlemmer av NAIS som har tilgang til Aiven console som kan lage sertifikater.
For å få et nytt sertifikat så kan man ta kontakt med en i NAIS og peke de på dette repoet.
Enkleste er kanskje å ta kontakt med Kyrre eller Morten som har kjennskap til dette prosjektet.

Skal man opprette nye topics så anbefaler vi at man bruker et Python-script i [nais/kafkarator](https://github.com/nais/kafkarator/blob/master/scripts/leesah_quiz_creds.py).
Denne vil opprette topics, og ACL, i tilegg vil den lage en Yaml-fil med sertifikater, topics, og egen student-bruker.
Yaml-filen blir brukt av [navikt/leesah-game-cert-server](https://github.com/navikt/leesah-game-cert-server).

## Guide for operatorer i NAIS

(til nestemann: lag et script som samler sammen alt i én kommando 😇)

### Steg 0. Skru på Kafka servicen i Aiven Console

```shell
avn user login <you@example.com>
avn project switch nav-integration-test
avn service update nav-integration-test-kafka --power-on
```

### Steg 1. Kjør script fra `nais/kafkarator` for å opprette topics og klientsertifikater for `leesah-game-cert-server`

Scriptet ligger her: <https://github.com/nais/kafkarator/blob/master/scripts/leesah_quiz_creds.py>.

Denne outputter en YAML-fil med prefiks `leesah-quiz-cert`:

```shell
# python leesah_quiz_creds.py oslomet 5
python leesah_quiz_creds.py $EVENT_NAME $NUMBER_OF_TOPICS
...
Packet saved to /var/folders/4c/4f248mj91qv4pj5dnlynm5yr0000gn/T/leesah-quiz-cert6uo2vvi7.yaml
```

Filen må renames til `student-certs.yaml`:

```shell
mv <path/to/leesah-quiz-cert.yaml> student-certs.yaml
```

Og deretter zippes til `leesah-creds.zip`:

```shell
zip leesah-creds.zip <path/to/student-certs.yaml>
```

### Steg 2. Tilgjengeliggjør sertifikater for `leesah-game-cert-server`

```shell
kubectl delete secret creds-server-student-certs \
  --context dev-gcp \
  --namespace leesah-quiz

kubectl create secret generic creds-server-student-certs \
  --context dev-gcp \
  --namespace leesah-quiz \
  --from-file=leesah-creds.zip
```

### Steg 3. Lag sertifikater for `quizmaster` og `quizboard`

```shell
avn service user-kafka-java-creds nav-integration-test-kafka \
  --username leesah-quiz-master \
  -d .
```

### Steg 4. Tilgjengeliggjør sertifikater for `quizmaster` og `quizboard`

```shell
kubectl delete secret leesah-certs \
  --context prod-gcp \
  --namespace leesah-quiz

kubectl create secret generic leesah-certs \
  --context prod-gcp \
  --namespace leesah-quiz \
  --from-file=keystore.p12=client.keystore.p12 \
  --from-file=truststore.jks=client.truststore.jks
```

### Steg 5. Restart alle appene

```shell
kubectl rollout restart deploy \
  --context dev-gcp \
  --namespace leesah-quiz 
kubectl rollout restart deploy \
  --context prod-gcp \
  --namespace leesah-quiz
```

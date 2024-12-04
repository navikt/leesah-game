---
title: 'Nais Leesah'
---

## Nais Leesah

Din utfordring er å implementere en QuizParticipant som svarer på alle spørsmålene som 
publiseres av Quizmaster 🧙. Du står fritt til å utvikle applikasjonen din slik du vil, 
men dette startprosjektet kommer med nyttig grunnstruktur som hjelper deg raskt å komme i gang med 
det morsomme; å svare på spørsmål og å vinne quizen! 🎉

### Velg ditt språk

- [Kotlin](https://github.com/navikt/leesah-game-template)
- [Go](https://github.com/navikt/leesah-game-template-go)

1. Klikk på knappen _Use this template_ som er plassert øverst i repoet
2. Opprett et nytt offentlig repository fra malen med ditt lagnavn og navikt-organisasjonen som eier
3. I ditt nye repository legger du til teamet _leesah-quiz_ med adming-tilgang for repoet
4. Gå deretter til [NAIS Console](https://console.nav.cloud.nais.io/) og autoriser repoet ditt ved å klikke på _leesah-quiz_ under _My Teams_, deretter Repositories, finn ditt repo og klikk _Authorize_  
   - Hvis repoet ditt ikke er på listen, kan du prøve å trigge synkroniseringen manuelt via _Synchronize team_ under _Settings_
5. Klon ditt repository til din lokale maskin
   - `git clone https://github.com/navikt/<DITT_REPOSITORY_NAVN>.git`
6. Fortsett med guiden nedenfor

### Deploy

#### nais.yaml
Du må deploye appen din for å svare på spørsmål og spille spillet, og til det trenger du å legge inn ditt team navn i `nais.yaml`.

```yaml
apiVersion: nais.io/v1alpha1
kind: Application
metadata:
  name: <YOUR_TEAM_NAME> # ENDRE DETTE! Dette vil være navnet på applikasjonen din
  namespace: leesah-quiz
  labels:
    team: leesah-quiz
spec:
  {%- raw -%}
  image: {{image}}
  {% endraw %}
  replicas:
    max: 1
    min: 1
  kafka:
    pool: nav-dev
  env:
    - name: QUIZ_TOPIC
      value: leesah-quiz.leesah-rapid-v2
```

- Husk å endre navnet på linje 4 til ditt teamnavn med små bokstaver og bindestrek istedenfor mellomrom.

#### deploy.yaml
Du må også konfigurere en GitHub workflow-fil. Lim inn følgende kode i  `.github/workflows/deploy.yaml`-filen
- Du kan gå til [docs.nav.cloud.nais.io](https://doc.nav.cloud.nais.io/how-to-guides/github-action/) for å lese en oppdatert guide for å sette opp workflow for deploy til Nais.

```yaml
on: [push]

jobs:
  build_and_push:
    permissions:
      contents: "read"
      id-token: "write"
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '21'
      - name: test and build
        run: gradle build
      - uses: nais/docker-build-push@v0
        id: docker-push
        with:
          team: leesah-quiz
          {%- raw %}
          project_id: ${{ vars.NAIS_MANAGEMENT_PROJECT_ID }}
          identity_provider: ${{ secrets.NAIS_WORKLOAD_IDENTITY_PROVIDER }}
          {% endraw %}
      - name: Deploy
        uses: nais/deploy/actions/deploy@v2
        env:
          CLUSTER: dev-gcp
          RESOURCE: nais.yaml
          {%- raw %}
          IMAGE: ${{ steps.docker-push.outputs.image }}
          {% endraw %}
```

Hvis du vil trigge en workflow manuelt, kan du legge til `workflow_dispatch` til `on`-arrayet.

```yaml
on: [push, workflow_dispatch]
```

Du kan også fremskynde deployment ved å avbryte nåværende kjøringer når du deployerer en ny versjon:

```yaml
concurrency:
  {%- raw -%}
  group: ${{ github.workflow }}-${{ github.ref }}
  {% endraw %}
  cancel-in-progress: true
```

### Logger️

Gå til [logs.adeo.no](https://logs.adeo.no/app/discover#/?_g=(filters:!(),refreshInterval:(pause:!t,value:60000),time:(from:now-90d%2Fd,to:now))&_a=(columns:!(level,message,envclass,application,pod),filters:!(),index:'96e648c0-980a-11e9-830a-e17bbd64b4db',interval:auto,query:(language:kuery,query:'application:%20%22<YOUR_TEAM_NAME>%22%20and%20%22QUESTION%22'),sort:!(!('@timestamp',desc)))) for å se applikasjonsloggene dine i Kibana.
Når du er "inne" i Kibana, må du endre `<YOUR TEAM NAME>` til ditt teamnavn.

#### Nyttige kubectl-kommandoer

* Se navn og status på pods for appen din:
    * `kubectl get pod -n leesah-quiz -l app=<APP_NAME>`
    * Hvis du ønsker å kontinuerlig spore statusen til pods for appen din, kan du legge til flagget `-w`
  

* Se logger for pods for appen din:
    * For å se logger for alle pods: `kubectl logs -n leesah-quiz -l app=<APP_NAME>`
    * For å se logger for en spesifikk pod: `kubectl logs -n leesah-quiz <POD_NAME>`
    * Du kan finne navnene på dine pods med den forrige kommandoen
    * Hvis du ønsker å kontinuerlig spore logger, kan du legge til flagget `-f`

### Utvikle din quiz-deltaker 🤖

Koden du trenger å endre ligger i `main.go`/`QuizApplication.kt`.

Fra kommandolinjen i prosjektets rotmappe kjører du:

#### For å bygge appen lokalt

Kotlin:
```bash
./gradlew clean build
```

GO:
```bash
go build .
```

### For å kjøre appen lokalt (kun med GO)
```bash
go run .
```

### Første oppgave

Svar på lagspørsmålet med en hex-farge (6 tegn) i `Answer()` og deploy applikasjonen til NAIS! PS: husk '#', eksempel: #FFFFFF

Lykke til! Husk å stille spørsmål! ❤️
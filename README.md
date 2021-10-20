# 🏞️ Life is a Stream of Events | the game

## Description

TODO


## Setup


### Hele sulamitten

Prosjektet bruker Java 16. En praktisk måte å installere det på er med [`sdkman`](https://sdkman.io/):
- Installer `sdkman`
- Installer Java 16 med sdkman: `sdk install java 16.0.2-tem`

Start opp Docker Desktop og sørg for at det kjører.

Bygg hele prosjektet i rotmappen med:

`./gradlew clean build`

Sett opp hele sulamitten i docker med:

`docker compose up`

**Sende meldinger på Kafka**

For å produsere (test)meldinger til kafka-topicet kan man laste ned kafka:

`brew install kafka`

og deretter åpne et "producer-shell" mot topicet med

`kafka-console-producer --topic quiz-rapid --bootstrap-server localhost:29092`

Meldinger som skrives inn i dette shellet blir sendt på topicet og forhåpentligvis mottatt av appen.

### Frontend

Frontenden er plassert i en egen mappe under `/quizboard`. For lokal frontend-utvikling er det raskere å hoste frontend i utviklingsmodus med:

`npm install`
`npm run dev`

Merk at dette er en helt frikoblet versjon av frontend, så all testdata må mockes.

## Development

**Modules**

- **Quizmaster**
  
  Handles the flow of the game. Reads and writes on the game topic

- **Quizboard** (Leaderboard)

  Handles showing the state of the game as a webpage

### TODO

- [x] Start new game
- [x] Accept team registration
- [x] Issue challenges
  - [x] Challenge 1 ("\<number\> + \<number\>")

### Data Model

TODO





<p align="center">
<img style="height:40em;" src="/leesah.png">
</p>


# 🏞️ Life is a Stream of Events | the game

## Beskrivelse

Leesah-game er et hendelsedrevet applikasjonsutviklingspill laget for å utfordre spilleren til å utvikle en applikasjon som kan håndtere et diverst utvalg av utfordrende hendelser som den mottar på eller utenfor NAIS-plattformen.

## Eksempel leaderboardet som vises under spillet

<p align="center">
<img style="height:20em;" src="/leesah-game-board.png">
</p>



## Setup


### Build

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

NB! `kafka-console-producer` tolker hver linje som en separat melding og publiserer disse deretter, slik at hvis du skal sende
strukturert/nøstet json over flere linjer bør disse gjøres om til én linje før du sender meldingen.

**Localhost url**

`Admin panel: localhost:8000`

`Leader board: localhost:8081`


### Frontend

Frontenden er plassert i en egen mappe `/quizboard/frontend`. For lokal frontend-utvikling er det raskest å hoste frontend i utviklingsmodus med:

`npm install`

`npm run dev`

Merk at dette er en helt frikoblet versjon av frontend, så all testdata må mockes.

## Utvikling

**Moduler**

- **Quizmaster**
  
  Håndterer flyten i spillet. Leser og skriver på topicet.

- **Quizboard** (Leaderboard)

  Håndterer visning av tilstanden til spillet på en nettside. 


### TODO

- [x] Start nytt spill
- [x] Aksepter team registrering
- [x] Aktiver utfordringer i Quizmaster Admin 
  - [x] Utfordring 1 "Arithmetic" ("\<number\> + \<number\>")

### Data Modell

TODO


### Testdata

**Team registration answer**
`{"messageId": "b29175a7-059a-4a46-b274-94sd9f165473", "questionId": "b29175a7-059a-4a46-b274-947a9f165473", "type": "ANSWER", "category": "team-registration", "teamName": "", "answer": "coolteam", "created": "2022-11-22T16:36:59.155512"}`

**Arithmetic answer**
`{"messageId": "b30175a7-059a-4a46-b274-947a9f165473", "questionId": "cc07eb49-3454-4bdf-91f2-475d6e9d855e", "type": "ANSWER", "teamName": "coolteam", "category": "arithmetic", "answer": "7", "created": "2022-11-22T16:36:59.155512"}`

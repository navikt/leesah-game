# NAV

Denne oppgaven handler om å svare på noen helt vanlige quiz-spørsmål, altså ikke noe fancy programmering her. For å løse
oppgaven må appen svare på spørsmålet i `question`-feltet med riktig svar.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "176cc8af-3f37-4659-9dac-29d3d5537ebe",
  "question": "Hvilken ytelse fra NAV har syke arbeidere i Norge som oftest rett på?",
  "category": "NAV"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "messageId": "b8d1522f-d9b1-4d14-867a-f72607c1680f",
  "questionId": "176cc8af-3f37-4659-9dac-29d3d5537ebe",
  "category": "NAV",
  "teamName": "l33t team",
  "answer": "sykepenger"
}
```

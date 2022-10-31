# transactions

Denne oppgaven handler om å holde styr på innskudd og utrekk. En viktig oppgave både her og ellers i livet.
Svar med korrekt sammenstilt saldo for hver oppgave-hendelse appen mottar.


**Eksempel oppgave-hendelse**

```json lines

{
  "type": "QUESTION",
  "messageId": "bc22a3d2-ff4b-477c-8b67-52f3888f5848",
  "question": "UTTREKK 6764",
  "category": "transactions"
}

{
  "type": "QUESTION",
  "messageId": "6748a649-83f8-4953-8154-cd7f57e0d9fb",
  "question": "UTTREKK 8421",
  "category": "transactions"
}

```

**Eksempel svar**

```json lines

{
  "type": "ANSWER",
  "messageId": "07e8ca72-ff90-4c68-ad6e-476e7becc5ac",
  "questionId": "bc22a3d2-ff4b-477c-8b67-52f3888f5848",
  "category": "transactions",
  "teamName": "l33t team",
  "answer": "-6764"
}

{
  "type": "ANSWER",
  "messageId": "07e8ca72-ff90-4c68-ad6e-476e7becc5ac",
  "questionId": "6748a649-83f8-4953-8154-cd7f57e0d9fb",
  "category": "transactions",
  "teamName": "l33t team",
  "answer": "-15185"
}

```

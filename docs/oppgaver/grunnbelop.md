# grunnbelop

Grunnbeløpet blir bruk til å beregne mange av NAVs utbetalinger. Satsen blir oppdatert 1. mai hvert år og blir bestemt
etter trygdeoppgjøret. [LovData](https://lovdata.no/dokument/SF/forskrift/2022-05-20-881)

I denne oppgaven skal du svare på datoen i oppgaven med riktig grunnbeløp.

### Hint

Det finnes et åpent API for Grunnbeløp: [https://g.nav.no/](https://g.nav.no/) 😻

**Eksempel oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "3cf7a835-cd5e-4a40-baf9-844119f52ac6",
  "question": "grunnbelop dato: 1967-07-14",
  "category": "grunnbelop",
  "created": "2022-11-07T14:51:43.737955137"
}
```

**Eksempel svar**

```json
{
  "type": "ANSWER",
  "questionId": "3cf7a835-cd5e-4a40-baf9-844119f52ac6",
  "category": "grunnbelop",
  "teamName": "l33t team",
  "answer": "5400",
  "messageId": "55927c13-891f-446f-a579-3801ef6e444c",
  "created": "2022-11-07T14:53:27.581147"
}
```

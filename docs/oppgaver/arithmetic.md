# arithmetic

Aritmetikk oppgaver handler om å løse matteoppgaven som ligger i `question` feltet i oppgave-hendelsen.

Feltet er bygd opp slik: `"<integer> <operator> <integer>"`hvor operatoren kan være +,-,* eller /.

Oppgaven svares med riktig svar i heltall (integer).


**Eksempel oppgave-hendelse**
```json
{
  "type": "QUESTION",
  "messageId": "96acb8f4-4b1e-4f0a-9d54-fcdda1223d9d",
  "question": "49 - 62",
  "category": "arithmetic"
}
```

**Eksempel svar**
```json
{
  "type": "ANSWER",
  "messageId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "96acb8f4-4b1e-4f0a-9d54-fcdda1223d9d",
  "category": "team-registration",
  "teamName": "l33t team",
  "answer": "-13"
}
```

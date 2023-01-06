# team-registration

Første oppgave alle team må løse er "team-registration"-oppgaven. Oppgaven løses ved å publisere et svar med en farge i form av hex-kode som skal representere deres team, i tillegg til et teamnavn.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "question": "Registrér et nytt teamnavn og en hex-farge",
  "category": "team-registration",
  "created": "2022-11-07T14:53:27.581147"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "team-registration",
  "teamName": "l33t team",
  "answer": "ff2255",
  "messageId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "created": "2022-11-07T14:53:27.581147"
}
```

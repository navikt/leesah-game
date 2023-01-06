# base64

Base64 oppgaven handler om å svare tilbake med "noe" som forståelig tekst. Om du ikke er kjent med
base64 encoding så er det nok lurt å google og lese litt på hva det er for noe.

**Husk:** Se på `question`-feltet til oppgave-hendelsen.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "question": "echo UHl0aG9u",
  "category": "base64",
  "created": "2022-11-07T14:53:27.581147"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "questionId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "category": "base64",
  "teamName": "l33t team",
  "answer": "VUhsMGFHOXU=",
  "messageId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "created": "2022-11-07T14:53:27.581147"
}
```

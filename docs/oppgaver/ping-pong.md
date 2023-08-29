# ping-pong

Oppgaven er å svare på alle ping oppgave-meldinger.

**Eksempel oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "9e679608-fb1c-482c-9ced-00a3593bb684",
  "question": "ping",
  "category": "ping-pong",
  "created": "2022-11-07T14:53:27.581147"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "messageId": "2d4c8698-3fc1-4a8a-acd1-f428eea303aa",
  "questionId": "9e679608-fb1c-482c-9ced-00a3593bb684",
  "category": "ping-pong",
  "teamName": "l33t team",
  "answer": "pong",
  "created": "2022-11-07T14:53:27.581147"
}
```

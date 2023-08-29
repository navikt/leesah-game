# min-max

Oppgaven spør deg om det høyeste eller laveste tallet i en liste. Dersom den spør etter høyeste, skal du returnere det
høyeste tallet, dersom det spør etter det laveste skal du returnere det laveste tallet.

**Eksempel oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "9e679608-fb1c-482c-9ced-00a3593bb684",
  "question": "HOYESTE i [25, 66, 12, 3, 53, 54, 94, 67, 23, 55, 41, 30, 40, 50, 60, 70, 80, 10, 11, 1]",
  "category": "min-max",
  "created": "2022-11-17T15:14:27.581147"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "messageId": "2d4c8698-3fc1-4a8a-acd1-f428eea303aa",
  "questionId": "9e679608-fb1c-482c-9ced-00a3593bb684",
  "category": "min-max",
  "teamName": "l33t team",
  "answer": "94",
  "created": "2022-11-07T14:53:27.581147"
}
```

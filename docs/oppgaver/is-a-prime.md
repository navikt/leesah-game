# is-a-prime

Denne oppgaven handler om å finne ut om et tall er primtall eller ikke. Svar med `true` eller `false` avhengig av om
tallet inne i `question`-feltet i oppgave-hendelsen er et primtall eller ikke.

**Eksempel på oppgave-hendelse**

```json
 {
  "type": "QUESTION",
  "messageId": "444b1103-b8f5-4efc-8182-d71b7c2c9e5e",
  "question": "is it a prime number? 7",
  "category": "is-a-prime"
}
```

**Eksempelsvar**

```json
 {
  "type": "ANSWER",
  "messageId": "2d4c8698-3fc1-4a8a-acd1-f428eea303aa",
  "questionId": "444b1103-b8f5-4efc-8182-d71b7c2c9e5e",
  "category": "is-a-prime",
  "teamName": "l33t team",
  "answer": "true"
}
```

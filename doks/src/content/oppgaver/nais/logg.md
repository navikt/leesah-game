---
title: 'logg'
---

## Logg

Denne oppgaven ber deg om å utforske loggene til Quizmaster.
NAV sine logger finner du i Kibana som ligger på [logs.adeo.no](https://logs.adeo.no).
Oppgaven svares med nøkkelordet tildelt din app.

**Eksempel på oppgave-hendelse**

```json

{
  "type": "SPØRSMÅL",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategori": "logg",
  "spørsmål": "[NAIS Oppgave] Quizmaster har logget en hemmelig nøkkel for deg i sine logger, klarer du å finne denne nøkkelen og sende den tilbake?",
  "svarformat": "nøkkelord i String"
}
```

**Eksempelsvar**

``` json
{
  "type": "SVAR",
  "svarId": "db99ca84-07d4-4720-8841-c80786ed8cfc",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategori": "logg",
  "lagnavn": "l33t team",
  "svar": "harepus",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

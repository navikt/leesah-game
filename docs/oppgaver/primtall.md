# Primtall

Denne oppgaven handler om å finne ut om et tall er primtall eller ikke.
Et primtall er et naturlig tall større enn 1, som bare er delelig med seg selv og 1.
Svar med `true` eller `false` avhengig av om tallet inne i `spørsmål`-feltet i oppgave-hendelsen er et primtall eller ikke.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "444b1103-b8f5-4efc-8182-d71b7c2c9e5e",
  "kategorinavn": "primtall",
  "spørsmål": "7",
  "svarformat": "true eller false som String"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "2d4c8698-3fc1-4a8a-acd1-f428eea303aa",
  "spørsmålId": "444b1103-b8f5-4efc-8182-d71b7c2c9e5e",
  "kategorinavn": "primtall",
  "lagnavn": "l33t team",
  "svar": "true",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

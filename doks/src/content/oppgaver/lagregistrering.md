---
title: Lagregistrering
---

## Lagregistrering

Første oppgave alle team må løse er "lagregistrering"-oppgaven.
Oppgaven løses ved å publisere et svar med en farge i form av hexkode som skal representere deres team i `svar`, i tillegg til et lagnavn i `lagnavn`.
Lagnavnet må være det samme gjennom hele spillet, og skal ikke endres.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "spørsmål": "Velg en hexkode med 6 tegn for å representere ditt team. Eksempel: #FFFFFF",
  "kategori": "lagregistrering",
  "svarformat": "Hexkode i string"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "spørsmålId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "kategori": "lagregistrering",
  "lagnavn": "l33t team",
  "svar": "#ff2255",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

Når lagregistrering er godkjent vil teamet bli lagt til på leaderboardet.

![Leaderboard etter lagregistrering er godkjent](../assets/lagregistrering-leaderboard.png)

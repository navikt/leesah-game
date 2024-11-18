---
title: 'team-registration'
---

# Team-registration

Første oppgave alle team må løse er "team-registration"-oppgaven.
Oppgaven løses ved å publisere et svar med en farge i form av hex-kode som skal representere deres team i `svar`, i tillegg til et lagnavn i `lagnavn`.
Lagnavnet må være det samme gjennom hele spillet, og skal ikke endres.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "spørsmål": "Velg en hex-kode med 6 tegn for å representere ditt team. Eksempel: #FFFFFF",
  "kategori": "team-registration",
  "svarformat": "Hex kode i string"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "spørsmålId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "kategori": "team-registration",
  "lagnavn": "l33t team",
  "svar": "#ff2255",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

Når team-registration er godkjent vil teamet bli lagt til på leaderboardet.

<img src="../../assets/team-registration-leaderboard.png" alt="Leaderboard etter team-registration er godkjent">
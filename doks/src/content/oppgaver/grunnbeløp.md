---
title: 'grunnbeløp'
---

# Grunnbeløp

Grunnbeløpet blir bruk til å beregne mange av NAVs utbetalinger.
Satsen blir oppdatert 1. mai hvert år og blir bestemt etter trygdeoppgjøret.
Du kan lese mer om det på [nav.no/grunnbelopet](https://www.nav.no/grunnbelopet)
Det finnes også et åpent API for Grunnbeløp på [g.nav.no/](https://g.nav.no/)! 😻

I denne oppgaven skal du svare på datoen i oppgaven med riktig grunnbeløp.

**Eksempel oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "3cf7a835-cd5e-4a40-baf9-844119f52ac6",
  "kategori": "grunnbeløp",
  "spørsmål": "Grunnbeløp for dato: 1967-07-14",
  "svarformat": "Tall i String"
}
```

**Eksempel svar**

```json
{
  "type": "SVAR",
  "svarId": "55927c13-891f-446f-a579-3801ef6e444c",
  "spørsmålId": "3cf7a835-cd5e-4a40-baf9-844119f52ac6",
  "kategori": "grunnbeløp",
  "lagnavn": "l33t team",
  "svar": "5400",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

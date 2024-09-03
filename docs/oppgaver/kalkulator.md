# Kalkulator

Denne oppgaven ber deg finne ut hvor mye en bruker kan få i arbeidsavklaringspenger (AAP).
NAV tilbyr en kalkulator som gjør det enkelt å finne ut hva man har krav på, den finner du under [nav.no/aap/kalkulator](https://www.nav.no/aap/kalkulator).
Svar med korrekt sum i kroner for hver oppgave.

**Eksempel på oppgave-hendelse**

```json

{
  "type": "SPØRSMÅL",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategorinavn": "kalkulator",
  "spørsmål": "På https://www.nav.no/aap/kalkulator kan du beregne omtrent hvor mye du får i AAP. Du er 69 år og ble syk i 2014. Du har ingen inntekt, har ikke AAP, eller en jobb. Barn har du heller ikke. Hva kan du omtrent få i året?",
  "svarformat": "Int som string"
}
```

**Eksempelsvar**

``` json
{
  "type": "SVAR",
  "svarId": "db99ca84-07d4-4720-8841-c80786ed8cfc",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategorinavn": "kalkulator",
  "lagnavn": "l33t team",
  "svar": "253141",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

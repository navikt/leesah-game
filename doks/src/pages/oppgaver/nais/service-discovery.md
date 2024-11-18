---
title: 'service-discovery'
---

# Service discovery

Det er ikke kun [ingress](ingress.md) som kan brukes for å kommunisere mellom applikasjoner.
Spesielt intern kommunikasjon er det ingen grunn til å gå via internett, da er det bedre å gå internt via [service discovery](https://docs.nais.io/workloads/application/explanations/expose/#service-discovery).

Denne oppgaven ber deg om å åpne opp i din apps [access policy](https://docs.nais.io/workloads/application/reference/application-spec/#accesspolicy), slik at QuizMaster kan kalle på din app via service discovery.
Vi forventer å få tilbake 200 OK, ellers står du fritt til utsmykking av din nettside.
Oppgaven svares med navn på appen.

**Eksempel på oppgave-hendelse**

```json

{
  "type": "SPØRSMÅL",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategori": "service-discovery",
  "spørsmål": "[NAIS Oppgave] Quizmaster ønsker å snakke med appen din via noe som kalles for Service Discovery, men den blir stoppet av access policy hos deg. Gi quizmaster tilgang og svar med appnavnet ditt. Quizmaster forventer at appen din svarer med 200 ok.",
  "svarformat": "String"
}
```

**Eksempelsvar**

``` json
{
  "type": "SVAR",
  "svarId": "db99ca84-07d4-4720-8841-c80786ed8cfc",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategori": "service-discovery",
  "lagnavn": "l33t team",
  "svar": "leet",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

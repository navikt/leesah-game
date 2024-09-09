# Ingress

Det er ikke kun [service discovery](service-discovery.md) som kan brukes for å kommunisere mellom applikasjoner.
Hvis en ekstern app skal nå din app må du gjøre den tilgjengelig på internett, og da kan du bruke en [ingress](https://docs.nais.io/workloads/reference/environments/).

Denne oppgaven ber deg om å opprette en ingress som Quizmaster kan snakke med via internett.
Vi forventer å få tilbake 200 OK, ellers står du fritt til utsmykking av din nettside.
Oppgaven svares med adressen vi kan nå tjenesten din på.

**Eksempel på oppgave-hendelse**

```json

{
  "type": "SPØRSMÅL",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategorinavn": "ingress",
  "spørsmål": "[NAIS Oppgave] Lag en NAIS ingress for appen din i formatet: <app navn>.intern.dev.nav.no. Send oss din nye ingress som svar, vi forventer at den svarer med 200 ok.)",
  "svarformat": "ingress i string"
}
```

**Eksempelsvar**

``` json
{
  "type": "SVAR",
  "svarId": "db99ca84-07d4-4720-8841-c80786ed8cfc",
  "spørsmålId": "a662cd19-0eea-42f6-9850-a65ea419b7d9",
  "kategorinavn": "ingress",
  "lagnavn": "l33t team",
  "svar": "leet.intern.dev.nav.no",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

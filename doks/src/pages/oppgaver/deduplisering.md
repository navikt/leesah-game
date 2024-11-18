---
title: 'deduplisering'
---

# Deduplisering

## Bakgrunn

Denne oppgaven har som mål å gjøre deg litt mer kjent med et viktig konsept innen distribuerte, hendelsedrevne
systemer, nemlig dupliserte hendelser.
Det er sjeldent at et distribuert system kan garantere "once-and-only-once-delivery" av meldinger.
Man må velge mellom "at-most-once-delivery" eller "at-least-once-delivery", som oftest velger man da "at-least-once-delivery".
Derfor er det viktig å bygge systemer som håndterer at samme melding kan komme flere ganger.

Interessant artikkel om problemet med [exactly-once-delivery](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/).

## Oppgaven

I denne oppgaven vil Quizmaster sende ut samme melding flere ganger, og det er din applikasjons oppgave å svare på meldingen kun én gang.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "kategori": "deduplisering",
  "spørsmål": "Svar på kun ett spørsmål i denne kategorien med en <Du lurer ikke meg!>.",
  "svarformat": "String"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "2e47641c-4023-4893-8187-9de2445b45c0",
  "spørsmålId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "kategori": "deduplisering",
  "lagnavn": "l33t team",
  "svar": "Du lurer ikke meg!",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

### Om du svarer på meldingen flere ganger

Du vil se at du har fått underkjent løsningen din på leaderboardet, som betyr at oppgaven blir låst som underkjent til du nullstiller den.

<img src="../../assets/deduplisering-feilet.png" style="width: 25%;padding: 1em" alt="Deduplisering feilet, vist i Leaderboardet">

For å nullstille oppgaven for ditt team må du sende følgende streng som svar på oppgaven:

`Du lurte meg :(`

**Eksempel på nullstilling**

```json
{
  "type": "SVAR",
  "svarId": "22c9bd00-bb38-4996-8f24-528dbbeb8b95",
  "spørsmålId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "kategori": "deduplisering",
  "lagnavn": "l33t team",
  "svar": "Du lurte meg :(",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

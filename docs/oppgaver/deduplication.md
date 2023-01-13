# deduplication

## Bakgrunn

Denne oppgaven har som mål å gjøre deg litt mer kjent med et viktig konsept innen distribuerte, hendelsedrevne
systemer, nemlig dupliserte hendelser.
Det er sjeldent at et distribuert system kan garantere "once-and-only-once-delivery" av meldinger. Enten må man velge
"at-most-once-delivery" eller "at-least-once-delivery", som oftest velger man da "at-least-once-delivery" og derfor er det
viktig å bygge systemer som håndterer at samme melding kan komme flere ganger.

[Interessant artikkel om problemet med _
exactly-once-delivery_](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/)

## Oppgaven

I denne oppgaven vil Quizmaster sende ut samme melding flere ganger, og det er din applikasjons oppgave å svare på meldingen kun
én gang. Det finnes flere måter å jukse seg til poeng her, men oppfordringen er å løse problemet på en ordentlig måte.

### Om du mislykkes, altså sender svar mer enn en gang:

Du vil se at du har fått underkjent løsningen din på leaderboardet, som betyr at oppgaven blir låst som underkjent til du
nullstiller den.

<img src="/leesah-game/assets/deduplication-failed.png" style="width: 25%;padding: 1em" alt="deduplication failed showed on leaderboard">

### Nullstille oppgaven

For å nullstille oppgaven for ditt team må du sende følgende streng som svar på oppgaven: `you duped me!`

### Eksempler

**Eksempel på nullstilling**

```json
{
  "type": "ANSWER",
  "messageId": "22c9bd00-bb38-4996-8f24-528dbbeb8b95",
  "questionId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "category": "deduplication",
  "teamName": "l33t team",
  "answer": "you duped me!",
  "created": "2022-11-07T14:53:27.581147"
}
```

**Eksempel på oppgave-hendelse**

```json
{
  "type": "QUESTION",
  "messageId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "question": "answer this question only once with an <you wont dupe me!>",
  "category": "deduplication",
  "created": "2022-11-07T14:53:27.581147"
}
```

**Eksempelsvar**

```json
{
  "type": "ANSWER",
  "messageId": "2e47641c-4023-4893-8187-9de2445b45c0",
  "questionId": "0c8408aa-d592-47fa-aaef-eb8ee90993e5",
  "category": "deduplication",
  "teamName": "l33t team",
  "answer": "you wont dupe me!",
  "created": "2022-11-07T14:53:27.581147"
}
```

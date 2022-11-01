# deduplication

## Bakgrunn
Denne oppgaven har som mål å gjøre deg litt mer kjent med et viktig konsept innen distribuerte hendelse-baserte systemer, altså dupliserte hendelser.
Det er skjeldent et distribuert system kan garantere "once-and-only-once-delivery" av meldinger. Enten må man velge 
"at-most-once-delivery" eller "at-least-once-delivery", som oftest velger man da "at-least-once-delivery" og er det 
viktig å bygge systemer som håndterer at samme melding kan komme flere ganger.

[Interessant artikkel om problemet med *exactly-once-delivery*](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/)

## Oppgaven

I denne oppgaven vil Quizmaster sende ut samme melding flere ganger, det er appen din sin oppgave å bare svare meldingen
en gang. Det finst sikkert flere måter man kan jukse seg til poeng her, men oppfordringen er å løse problemet på en ordentlig måte.

**Om du mislykkes og sender svar mer en en gang**

Da vil du se at du har fått underkjent løsningen din på leaderboardet 
![deduplication failed](/assets/deduplication-failed.png)
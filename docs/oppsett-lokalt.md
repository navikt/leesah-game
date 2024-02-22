# Lokal Leesah

Lokal Leesah er versjonen vi kjører med studenter som en del av rekruteringsarbeidet til NAV.
Når man spiller den lokale varianten har vi bygd et eget bibliotek man importere inn i koden sin.
Så det eneste man trenger er sertifikater for å snakke med Kafka, og biblioteket for ditt språk.

## Kom i gang

### Hent Kafkasertifikat

Gå til [leesah-game-cert.ekstern.dev.nav.no/certs](https://leesah-game-cert.ekstern.dev.nav.no/certs), brukernavn og passord finner du på en tavle eller slide.
Pakk ut zip-filen i katalogen `certs/` i katalogen du kjører spillet i lokalt.

Du kan også peke på sertifikatet med miljøvariabelen ``.

```shell
export QUIZ_CERT=/path/to/student-certs.yaml
```

### Quiz topic

Som regel kommer sertifikatet med riktig topic ut av boksen, men av og til må denne settes eksplisitt, da kan du bruke miljøvariabelen `QUIZ_TOPIC`.

```shell
export QUIZ_TOPIC=leesah-quiz-fagtorsdag-1
```

### Biblioteker

Vi har laget biblioteker i flere forskjellige språk, så her er det bare å velge det du liker best.
Hvert bibliotek vil ha sin egen dokumentasjon for hvordan man kommer i gang.

- [Python 🐍](https://github.com/navikt/leesah-game-python)
- [Go](https://github.com/navikt/go-leesah)
- [Typescript](https://github.com/navikt/leesah-game-starter-ts)

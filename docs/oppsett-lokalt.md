# Lokal Leesah

Lokal Leesah er versjonen vi kjører med studenter som en del av rekrutteringsarbeidet til NAV.
Når man spiller den lokale varianten har vi bygd et eget bibliotek man importerer inn i koden sin,
slik at man kun trenger sertifikater for å snakke med Kafka, og biblioteket for ditt språk.

## Kom i gang

### Hent Kafkasertifikat

Gå til [leesah.io/certs](https://leesah.io/certs), brukernavn og passord finner du på en tavle eller slide.
Pakk ut zip-filen, som vil gi deg en `leesah-certs.yaml` som biblioteket du har valgt trenger.

Du kan også peke på sertifikatet med miljøvariabelen `QUIZ_CERTS`.

```shell
export QUIZ_CERTS=/path/to/leesah-certs.yaml
```

### Quiz topic

Som regel kommer sertifikatet med riktig topic ut av boksen, men av og til må denne settes eksplisitt, da kan du bruke miljøvariabelen `QUIZ_TOPIC`.

```shell
export QUIZ_TOPIC=leesah-quiz-galtvort-1
```

### Biblioteker

Vi har laget biblioteker i flere forskjellige språk, så her er det bare å velge det du liker best.
Hvert bibliotek vil ha sin egen dokumentasjon for hvordan man kommer i gang.

- [Python 🐍](https://github.com/navikt/leesah-game-python): `python3 -m pip install leesah-game`
- [Go](https://github.com/navikt/go-leesah): `go get github.com/navikt/go-leesah`
- [JavaScript](https://github.com/navikt/leesah-game): `npm install @navikt/leesah-game`

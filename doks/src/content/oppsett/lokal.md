---
title: 'Lokal Leesah'
---

## Lokal Leesah

Lokal Leesah er versjonen vi kjører med studenter som en del av rekrutteringsarbeidet til NAV.
Når man spiller den lokale varianten har vi bygd et eget bibliotek man importerer inn i koden sin,
slik at man kun trenger sertifikater for å snakke med Kafka, og biblioteket for ditt språk.

### Kom i gang

#### Hent Kafkasertifikat

Sertifikater for å koble seg på Kafka ligger tilgjengelig på [leesah.io/certs](https://leesah.io/certs), brukernavn er `leesah-game`, og
passord får du utdelt.

Du kan også bruke kommandoen nedenfor:

```shell
curl -u leesah-game:<passord> -o leesah-certs.zip https://leesah.io/certs && unzip leesah-certs.zip
```

Du vil nå ende opp med filen leesah-certs.yaml i leesah-game-katalogen du lagde tidligere.

#### Biblioteker

Vi har laget biblioteker i flere forskjellige språk, så her er det bare å velge det du liker best.
Hvert bibliotek vil ha sin egen dokumentasjon for hvordan man kommer i gang.

- [Python 🐍](https://pypi.org/project/leesah-game/): `python3 -m pip install leesah-game`
- [Go](https://pkg.go.dev/github.com/navikt/go-leesah): `go get github.com/navikt/go-leesah`
- [JavaScript](https://www.npmjs.com/package/@navikt/leesah-game): `npm install @navikt/leesah-game`


---
title: 'ordsøk'
---

## Ordsøk

I ordsøkoppgaven skal du finne frem til en rekke skjulte ord i en matrisen med bokstaver.
Ordene kan skrives horisontalt, vertikalt, diagonalt, og baklengs!
Du trenger ikke å finne alle ordene med en gang, så man kan utvide svaret sitt med nye ord etter hvert som de blir funnet.

Svaret skal være en kommaseparert liste av alle ordene du har funnet.

Ordsøkeksempelet nedenfor er `\nnsl\noaw\nvlv`, og er da enklest å lese hvis du gjør det om til en matrise.

```
nsp
oaw
alv
```

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "spørsmål": "Finn ord relatert til Nav:\nnsl\noaw\nvlv",
  "svarformat": "Kommaseparert liste med ord (ord1, ord2, ...)",
  "kategori": "ordsøk"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "spørsmålId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "kategori": "ordsøk",
  "lagnavn": "l33t team",
  "svar": "nav,aap",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

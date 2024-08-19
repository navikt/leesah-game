# base64

Base64 oppgaven handler om å svare tilbake med "noe" som forståelig tekst.
Om du ikke er kjent med base64 encoding så anbefaler vi [Wikipedia/base64](https://en.wikipedia.org/wiki/Base64)-artikkelen før man hiver seg løs på oppgaven.

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "spørsmål": "TEVFU0FI",
  "svarformat": "Dekryptert string",
  "kategorinavn": "base64"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "spørsmålId": "608df513-96e1-4c4e-af26-ec81823aed1d",
  "kategorinavn": "base64",
  "lagnavn": "l33t team",
  "svar": "LEESAH",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

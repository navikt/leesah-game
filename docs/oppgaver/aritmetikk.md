# Aritmetikk

Aritmetikkoppgaver handler om å løse matteoppgaven som ligger i `spørsmål` feltet i oppgave-hendelsen.
Feltet er bygd opp slik: `"<integer> <operator> <integer>"`hvor operatoren kan være `+`, `-`, `*` eller `/`.
Oppgaven svares med riktig svar i heltall (integer).

**Eksempel på oppgave-hendelse**

```json
{
  "type": "SPØRMÅL",
  "spørsmålId": "96acb8f4-4b1e-4f0a-9d54-fcdda1223d9d",
  "kategori": "aritmetikk",
  "spørsmål": "49 - 62",
  "svarformat": "Svaret må rundes til int, men sender som en string"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "spørsmålId": "96acb8f4-4b1e-4f0a-9d54-fcdda1223d9d",
  "kategori": "aritmetikk",
  "lagnavn": "l33t team",
  "svar": "-13",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

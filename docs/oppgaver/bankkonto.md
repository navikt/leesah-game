# Bankkonto

Denne oppgaven handler om å holde styr på innskudd og utrekk; en viktig oppgave både her og ellers i livet.
Svar med korrekt sammenstilt saldo for hver oppgave-hendelse applikasjonen mottar.

**Eksempel på oppgave-hendelse**

```json

{
  "type": "SPØRSMÅL",
  "spørsmålId": "bc22a3d2-ff4b-477c-8b67-52f3888f5848",
  "kategorinavn": "bankkonto",
  "spørsmål": "UTTREKK 6764",
  "svarformat": "Tall i String"
}
```

``` json
{
  "type": "SPØRSMÅL",
  "spørsmålId": "6748a649-83f8-4953-8154-cd7f57e0d9fb",
  "kategorinavn": "bankkonto",
  "spørsmål": "UTTREKK 8421",
  "svarformat": "Tall i String"
}
```

**Eksempelsvar**

```json
{
  "type": "SVAR",
  "svarId": "07e8ca72-ff90-4c68-ad6e-476e7becc5ac",
  "spørsmålId": "bc22a3d2-ff4b-477c-8b67-52f3888f5848",
  "kategorinavn": "bankkonto",
  "lagnavn": "l33t team",
  "svar": "-6764",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

``` json
{
  "type": "SVAR",
  "svarId": "07e8ca72-ff90-4c68-ad6e-476e7becc5ac",
  "spørsmålId": "6748a649-83f8-4953-8154-cd7f57e0d9fb",
  "kategorinavn": "bankkonto",
  "lagnavn": "l33t team",
  "svar": "-15185",
  "opprettet": "2022-11-07T14:53:27.581147"
}
```

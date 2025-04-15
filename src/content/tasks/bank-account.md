---
title: bank-account
---

## Bank account

This task is all about managing deposits and withdrawals; a important task both here and elsewhere in life.
Answer with the correct consolidated balance for each question event your application receives.

**Example of questions**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "bank-account",
  "question": "WITHDRAWAL: 6764",
  "answerFormat": "Number as string"
}
```

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "bank-account",
  "question": "DEPOSIT: 8421",
  "answerFormat": "Number as string"
}
```

**Example answers**

```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "bank-account",
  "teamName": "l33t team",
  "answer": "-6764"
}
```

```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "bank-account",
  "teamName": "l33t team",
  "answer": "1657"
}
```

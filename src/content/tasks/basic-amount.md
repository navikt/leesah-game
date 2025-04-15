---
title: basic-amount
---

## Basic-amount

National Insurance scheme basic amount (often written as "G") is used to calculate many of Nav's payments. 
The rate is updated each year at 1. may and is decided the social security settlement.
You can read [more about it in the following article](https://www.skatteetaten.no/en/rates/national-insurance-scheme-basic-amount/). 
There is also a open API for the basic amount at [g.nav.no](g.nav.no)! 😻

In this task the goal is to answer the correct basic amount at the given date.

**Example of a question**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "basic-amount",
  "question": "Basic amount for date 1967-07-14",
  "answerFormat": "Number as string"
}
```

**Example of answer**

```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "basic-amount",
  "teamName": "l33t team",
  "answer": "5400"
}
```

---
title: 'Prime number'
---

## Prime number

This task is about determining whether a number is prime or not.
A prime number is a natural number greater than 1 that cannot be formed by multiplying two smaller natural numbers.
In other words, a prime number is only divisible by itself and 1.
Respond with `true` or `false` depending on whether the number inside the `question` field in the task event is a prime number or not.

**Example of a question**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "prime-number",
  "question": "7",
  "answerFormat": "true or false as String"
}
```

**Example of answer**

```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "prime-number",
  "teamName": "l33t team",
  "answer": "true",
  "created": "2022-11-07T14:53:27.581147"
}
```

---
title: 'arithmetic'
---

## Arithmetic

The arithmetic task is all about solving math problems which can be found in the `question` field of the event. The field is built on the following format `"<integer> <operator> <integer>"`, where the operator could include the following: `+`, `-`, `*` or `/`. The answer has to be the correct integer.

**Example of a task event**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "arithmetic",
  "question": "49 - 42",
  "answerFormat": "Answer must be rounded to a integer, but sent as an string"
}
```

**Example of answer**
```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "arithmetic",
  "teamName": "l33t team",
  "answer": "-13",
  "created": "2022-11-07T14:53:27.581147"
}
```
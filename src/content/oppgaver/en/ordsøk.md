---
title: word-search
---

## Word-search

The goal of the word search task is to find a range of hidden words in a matrix of characters. Words can be found horizontal, vertical, diagonally and backwards! You do not have to find all words at once and are able to expand your answer as you find new words.

Answer the task as a comma separated list of all the found words.

The following word search example is `\nnsl\noaw\nvlv` and its easier to read if converted to a matrix.

```
nsp
oaw
alv
```

**Example of a task event**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "word-search",
  "question": "Find words related to Nav: \nnsl\noaw\nvlv",
  "answerFormat": "Comma separated list of words"
}
```

**Example answer**

```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "word-search",
  "teamName": "l33t team",
  "answer": "nav,aap",
  "created": "2022-11-07T14:53:27.581147"
}
```
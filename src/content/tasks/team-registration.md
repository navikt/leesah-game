---
title: team-registration
---

## Team-registration

The first task each team has to solve is the "team-registration" task. 
The task is solved by publishing an answering a hex code color which will represent your team in `answer`, in addition to a team name in `teamName`. 
Your team name must be the same throughout the whole game and cannot be changed.

**Example of a question**

```json
{
  "type": "QUESTION",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "team-registration",
  "question": "Choose a hex code of at least 6 characters to represent your team. Example: #FFFFFF",
  "answerFormat": "Hex code as string"
}
```

**Example of answer**
```json
{
  "type": "ANSWER",
  "answerId": "d36b4273-0571-42f2-b3bd-7b7987de43b0",
  "questionId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "category": "team-registration",
  "teamName": "l33t team",
  "answer": "#ff2255"
}
```

When team-registration is approved your team will be displayed at the leaderboard.

![Leaderboard after team-registration is approved](../../assets/lagregistrering-leaderboard.png)

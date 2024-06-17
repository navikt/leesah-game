# Nais Leesah

## Kom i gang 📝

### Velg ditt språk

- [Kotlin](https://github.com/navikt/leesah-game-template)
- [Go](https://github.com/navikt/leesah-game-template-go)

1. Click the _Use this template_ button located at the top of the repo
2. Create a new public repository from the template with your team name with the navikt organisation as owner.
3. Clone your repository to your local machine
    - `git clone https://github.com/navikt/<YOUR_REPOSITORY_NAME>-leesah-quiz.git`

### Nais & deploy
You will have to deploy your app to answer questions and play the game, and therefore you will need a `nais.yaml` file in root.

```yaml
apiVersion: nais.io/v1alpha1
kind: Application
metadata:
  name: <YOUR_TEAM_NAME> # CHANGE THIS! This will be the name of your application
  namespace: leesah-quiz
  labels:
    team: leesah-quiz
spec:
  image: {{image}}
  replicas:
    max: 1
    min: 1
  kafka:
    pool: nav-dev
  env:
    - name: QUIZ_TOPIC
      value: <CHANGE_ME>
```

- Remember to change the name on line 4 to your team name with lowercase letters.
- You also need to change topic value on the last line, he should be on the slide/whiteboard/blackboard.
- You also need to create a GitHub workflow-file. Start by creating folders `.github/workflows` on root.
Then go to [docs.nav.cloud.nais.io](https://doc.nav.cloud.nais.io/how-to-guides/github-action/) to read an updated guide for setting up the workflow for deploying to Nais.
    - Remember to change the team name on line 19 and the cluster on line 25 


If you would like to manually trigger a workflow you can add `workflow_dispatch` to the `on` array.

```yaml
on: [push, workflow_dispatch]
```

You can also speed up deployment by aborint current runs when deploying a new version:

```yaml
concurrency:
  group: ${{ github.workflow }}-${{ github.ref }}
  cancel-in-progress: true
```

## Observability

Use the following command to observe the running status of your app

### Logs in Kibana

Go to [logs.adeo.no](https://logs.adeo.no/app/discover#/?_g=(filters:!(),refreshInterval:(pause:!t,value:60000),time:(from:now-90d%2Fd,to:now))&_a=(columns:!(level,message,envclass,application,pod),filters:!(),index:'96e648c0-980a-11e9-830a-e17bbd64b4db',interval:auto,query:(language:kuery,query:'application:%20%22<YOUR_TEAM_NAME>%22%20and%20%22QUESTION%22'),sort:!(!('@timestamp',desc)))) to see your application logs in Kibana.
When "inside" Kibana you need to change `<YOUR TEAM NAME>` to your team name.

### Useful kubectl-commands

* See name and status of pods of your app:
    * `kubectl get pod -n leesah-quiz -l app=<APP_NAME>`
    * if you wish to continuously trace the status of pods of your app, you can add the flag `-w`
* View logs of pods of your app:
    * Too see logs for all your pods: `kubectl logs -n leesah-quiz -l app=<APP_NAME>` 
    * To see logs for one specifig pod: `kubectl logs -n leesah-quiz <POD_NAME>`
    * You can find name(s) of your pod(s) with the previous command
    * If you wish to continuously trace logs, you can add the flag `-f`

## Developing your quiz participant 🤖

Your challenge is to implement a QuizParticipant that answers all the questions that are published by the quizmaster 🧙.

The code you need to modify is all located in `main.go`/`QuizApplication.kt`.

From the command-line in the project root run:

**To build the app locally**

Kotlin
```bash
./gradlew clean build
```

GO
```bash
go build .
```

**To run the app locally (only with GO)**

GO
```bash
go run .
```

### First task

Answer the team question with a hex-color (6 characters) in `Answer()` and deploy the application to NAIS!

Good luck! Remember to ask questions! ❤️

import React, {useState} from "react"
import {useInterval} from "./poller"
import {Backend, QuizStatsDto, restBackend, CategoriesDto, PendingAnswer} from "./restBackend"
import {Environment} from "./environment"
import {testBackend} from "./hardcodedBackend"
import "./index.css"
import {Card, LabelCard} from "./Card";

const backend: Backend = Environment.isDevelopment
    ? testBackend()
    : restBackend(false)

function QuizStats() {
    const nullBoard: QuizStatsDto = {status: "INACTIVE", categories: []}
    const [quizStats, setQuizStats] = useState(nullBoard)

    useInterval(() => {
        const update = async () => {
            const response = backend.quizStats()
            setQuizStats(await response)
        }
        update().then((r) => console.log("Updating..."))
    }, 1000)

    return (
        <>
            <Card className={"horizontalContainer"}>
                <h2>Quiz status:</h2>
                <h2 className={quizStats.status === "ACTIVE" ? "activeFont" : ""}>{quizStats.status}</h2>
                {quizStats.status === "ACTIVE" ?
                    <button onClick={() => backend.quiz("start")}>Stop</button> :
                    <button onClick={() => backend.quiz("stop")}>Start</button>}
            </Card>

            <CategoryView categories={quizStats.categories}/>
        </>

    )
}

const CategoryView = (props: { categories: CategoriesDto[] }) =>
    (<div className={"horizontalContainer"}>
        {props.categories.map(cat =>
            (<Card className={"verticalContainer"}>
                <h3>{cat.name}</h3>
                <CategoryDetail label={"status"} value={cat.status}/>
                <CategoryDetail label={"maxCount"} value={cat.maxCount}/>
                <CategoryDetail label={"questionCount"} value={cat.questionCount}/>
                <CategoryDetail label={"answerCount"} value={cat.answerCount}/>
                <CategoryDetail label={"correctAnswerCount"} value={cat.correctAnswerCount}/>
                {cat.pendingAnswers.length > 0 &&<PendingAnswers category={cat.name} answers={cat.pendingAnswers}/>}
                <button onClick={() => backend.toggle(cat.name)}>Activate</button>
            </Card>))}
    </div>)

const CategoryDetail = (props: { label: string, value: string | number }) =>
    (<div className={"categoryView"}>
        <LabelCard text={props.label + ":"}/>
        <p className={props.value === "ACTIVE" ? "activeFont" : ""}>{props.value}</p>
    </div>)

const PendingAnswers = (props: { category: string, answers: PendingAnswer[] }) =>
    (<div>
        <p>answers pending:</p>
        <div className={"categoryView"}>
            { props.answers.map((answer) =>
                (   <>
                    <button onClick={() => backend.accept(props.category, answer.answerId)}>{answer.teamName}</button>
                    <a style={{marginLeft: "1em"}} href={answer.answer}>{answer.answer}</a>
                    </>
                ))
            }
        </div>
    </div>)


const App = () => {
    // fetch("http://localhost:8081/teams")
    //     .then(response => console.log(response))

    return (
        <>
            <h1> Quizmaster Admin</h1>
            <QuizStats/>
        </>
    )
}

export default App

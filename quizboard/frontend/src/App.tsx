import React, { useState, useEffect, useRef } from "react"
import { useInterval } from "./poller"
import { Backend, BoardDto, restBackend, TeamResultDto } from "./restBackend"
import { Environment } from "./environment"
import { testBackend } from "./hardcodedBackend"
import { Category } from "../../backend/src/main/kotlin/no/nav/quizboard/Category.kt"
import { ReactComponent as FailureIcon } from "./ikoner/Error.svg"
import { ReactComponent as PendingIcon } from "./ikoner/Warning.svg"
import { ReactComponent as OkIcon } from "./ikoner/Success.svg"
import "./index.css"

const backend: Backend = Environment.isDevelopment
    ? testBackend()
    : restBackend(true)

function Leaderboard() {
    const nullBoard: BoardDto = { board: [] }
    const [board, setBoard] = useState(nullBoard)

    useInterval(() => {
        // Your custom logic here

        const update = async () => {
            const response = backend.board()
            setBoard(await response)
        }
        update().then((r) => console.log("Updating..."))
    }, 2000)

    // const icon = (status) => {
    //     console.log(status)
    //     if (status === "FAILURE") {
    //         return <FailureIcon />
    //     } else if (status === "PENDING") {
    //         return <PendingIcon />
    //     } else if (status === "OK") {
    //         return <OkIcon />
    //     }
    // }

    return (
        <div className="leaderboard">
            {board.board.map((team) => (
                <div className="leaderboard__team">
                    <p>Teamnavn: {team.name}</p>
                    <p>Score: {team.score}</p>
                    {team.categoryResult.map((cat) => (
                        <span>
                            {cat.name} = {cat.status}
                            {/*{cat.name} = {icon(cat.status)}*/}
                        </span>
                    ))}
                </div>
            ))}
        </div>
    )
}

const App = () => {
    // fetch("http://localhost:8081/teams")
    //     .then(response => console.log(response))

    return (
        <>
            <h1> Life is a stream of events | LEADERBOARD</h1>
            <Leaderboard />
        </>
    )
}

export default App

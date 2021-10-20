import React, {useState, useEffect, useRef} from 'react';
import {useInterval} from "./poller";
import {BoardDto, restBackend, TeamResultDto} from "./restBackend";

function Leaderboard() {

    const nullBoard: BoardDto = {board: []}
    let [board, setBoard] = useState(nullBoard);

    useInterval(() => {
        // Your custom logic here

        const update = async () => {
            const response = restBackend(true).board();
            setBoard(await response)
        }
        update().then(r => console.log("updating..."))

    }, 2000);

    return <div>
        {
            board.board.map( team => (
                <p> {team.name} {team.score} {team.categoryResult.map( cat => (<span>{cat.name}={cat.status}</span>)) } </p>
            ))
        }
    </div>;
}

const App = () => {

    // fetch("http://localhost:8081/teams")
    //     .then(response => console.log(response))

    return (
        <div>
            <header>
                <h1> Life is a stream of events | LEADERBOARD </h1>
                <Leaderboard/>
            </header>
        </div>
    )
}

export default App

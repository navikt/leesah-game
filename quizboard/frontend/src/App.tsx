import React from "react"
import "./index.less"
import Leaderboard from "./Leaderboard/Leaderboard";

const App = () => {
    // fetch("http://localhost:8081/teams")
    //     .then(response => console.log(response))

    return (
        <div className="app">
            <h1 className="header">Leaderboard</h1>
            <h2 className="sub-header">Life is a stream of events</h2>
            <Leaderboard />
        </div>
    )
}

export default App

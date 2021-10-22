import { Backend, BoardDto, restBackend } from '../restBackend';
import React, { useState } from 'react';
import { useInterval } from '../poller';
import ErrorIkon from '../ikoner/Error.svg';
import WarningIkon from '../ikoner/Warning.svg';
import OkIkon from '../ikoner/Success.svg';
import { Environment } from '../environment';
import { testBackend } from '../hardcodedBackend';
import './Leaderboard.less';

const backend: Backend = Environment.isDevelopment
    ? testBackend()
    : restBackend(true);

export default function Leaderboard() {
    const nullBoard: BoardDto = { board: [] };
    const [board, setBoard] = useState(nullBoard);

    useInterval(() => {
        // Your custom logic here

        const update = async () => {
            const response = backend.board();
            setBoard(await response);
        };
        update().then(() => console.log('Updating...'));
    }, 2000);


    const icon = (status: String) => {
        if (status === 'FAILURE') {
            return <img src={ErrorIkon} alt='Error icon' />;
        } else if (status === 'PENDING') {
            return <img src={WarningIkon} alt='Warning icon' />;
        } else if (status === 'OK') {
            return <img src={OkIkon} alt='Ok icon' />;
        }
    };

    return (
        <div className='leaderboard'>
            {board.board.map((team: any, index: number) => (
                <div key={index} className='leaderboard__team'>
                    <div className='leaderboard__info'>
                        <h3 className='leaderboard__teamname'>{team.name}</h3>
                        <p className='leaderboard__score'>Total score: {team.score}</p>
                    </div>
                    {team.categoryResult.map((category: any, index: number) => (
                        <div key={index} className='leaderboard__category'>
                            <p>{category.name}</p>
                            {icon(category.status)}
                            <p>{category.okCount}</p>
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
}

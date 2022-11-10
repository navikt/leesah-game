import { Backend, BoardDto, restBackend, TeamResultDto } from '../restBackend';
import React, { useEffect, useState } from 'react';
import { useInterval } from '../poller';
import ErrorIkon from '../ikoner/Error.svg';
import WarningIkon from '../ikoner/Warning.svg';
import OkIkon from '../ikoner/Success.svg';
import { Environment } from '../environment';
import { testBackend } from '../hardcodedBackend';
import './Leaderboard.less';
import classNames from 'classnames';

const backend: Backend = Environment.isDevelopment
    ? testBackend()
    : restBackend(false);

export default function Leaderboard() {
    const nullBoard: BoardDto = { board: [] };
    const [board, setBoard] = useState(nullBoard);
    const [score, setScore] = useState(0);
    const totalListe: { teamName: string; score: number; }[] = [];

    useInterval(() => {
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


    const updateScore = (teamName: string, score: number) => {
        totalListe.push({ teamName, score })

        //scoreListe.map((score) => {


        //    if (score.teamName === teamnavn) {
        //        isUpdated(totalScore)
        //    }
        //})
    //console.log(totalListe);
    }

    const isUpdated = (teamName: string, score: number) => {


        totalListe.map((team) => {
            if(team.teamName === teamName) {
                console.log(team.teamName);
                console.log("team", team.score)
                console.log(score)
                if (team.score < score) {
                    console.log('TEST');
                }
            }
        })
        return false
    }

    return (
        <div className='leaderboard'>
            {board.board.sort((a: TeamResultDto, b: TeamResultDto) => a.score < b.score ? 1 : -1)
                .map((team: any, index: number) => (
                    <>
                    {updateScore(team.name, team.score)}
                    <div key={index} className='leaderboard__wrapper'>
                        <p className='leaderboard__number'>{index}.</p>
                        <div className={classNames('leaderboard__team', isUpdated(team.navn, team.score) ? 'ani' : '' )}>

                            <div className='leaderboard__info'>
                                <h3 className='leaderboard__teamname'>{team.name}</h3>
                                <p className='leaderboard__score'>Total score: {team.score}</p>
                            </div>
                            {team.categoryResult.map((category: any, index: number) => (
                                <div key={index} className='leaderboard__category'>
                                    <p className='leaderboard__category__name'>{category.name}</p>
                                    <div className='leaderboard__category__status'>
                                        {icon(category.status)}
                                        <p>{category.okCount}</p>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                    </>
                ))}
        </div>
    );
}

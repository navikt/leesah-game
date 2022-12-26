import React, {useEffect, useState} from 'react';
import ErrorIkon from '../ikoner/Error.svg';
import WarningIkon from '../ikoner/Warning.svg';
import OkIkon from '../ikoner/Success.svg';
import './Leaderboard.less';
import {BoardDto, TeamResultDto} from "../types";
import {hentBoard} from "../backend";

export default function Leaderboard() {
    const nullBoard: BoardDto = {board: []};
    const [board, setBoard] = useState(nullBoard);

    useEffect(hentBoard(setBoard), [])

    const icon = (status: String) => {
        if (status === 'FAILURE') {
            return <img src={ErrorIkon} alt='Error icon'/>;
        } else if (status === 'PENDING') {
            return <img src={WarningIkon} alt='Warning icon'/>;
        } else if (status === 'OK') {
            return <img src={OkIkon} alt='Ok icon'/>;
        }
    };

    function hexToRgb(hex: string) {
        const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? {
            r: parseInt(result[1], 16),
            g: parseInt(result[2], 16),
            b: parseInt(result[3], 16)
        } : null;
    }

    function colorPicker(hex: string) {
        const red = hexToRgb(hex)?.r!;
        const green = hexToRgb(hex)?.g!;
        const blue = hexToRgb(hex)?.b!;

        const brightness = ((red * 299) + (green * 587) + (blue * 114)) / 1000;

        if (brightness < 127.5) {
            return 'white';
        }
        return 'black';
    }

    return (
        <div className='leaderboard'>
            {board.board.sort((a: TeamResultDto, b: TeamResultDto) => a.score < b.score ? 1 : -1)
                .map((team: any, index: number) => (
                    <div key={index} className='leaderboard__wrapper'>
                        <p className='leaderboard__number'>{index}.</p>
                        <div className='leaderboard__team'>
                            <div className='leaderboard__info'
                                 style={{backgroundColor: `#${team.hex}`, color: colorPicker(team.hex)}}>
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
                ))}
        </div>
    );
}

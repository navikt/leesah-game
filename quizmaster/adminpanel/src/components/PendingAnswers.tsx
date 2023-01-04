import { PendingAnswer } from '../restBackend';
import React from 'react';

const PendingAnswers = (props: { category: string; answers: PendingAnswer[] }, backend: any) => (
    <>
        <p>answers pending:</p>
        <div className={'category-view'}>
            {props.answers.map(answer => (
                <>
                    <button onClick={() => backend.accept(props.category, answer.answerId)}>{answer.teamName}</button>
                    <a style={{ marginLeft: '1em' }} href={answer.answer}>
                        {answer.answer}
                    </a>
                </>
            ))}
        </div>
    </>
);

export default PendingAnswers;

import { Backend, PendingAnswer } from '../restBackend';
import React from 'react';

interface PendingAnswersProps {
    category: string;
    answers: PendingAnswer[];
    backend: Backend;
}
const PendingAnswers = ({ category, answers, backend }: PendingAnswersProps) => (
    <>
        <p>answers pending:</p>
        <div className="category-view">
            {answers.map((answer, index) => (
                <div key={index}>
                    <button onClick={() => backend.accept(category, answer.answerId)}>{answer.teamName}</button>
                    <a style={{ marginLeft: '1em' }} href={answer.answer}>
                        {answer.answer}
                    </a>
                </div>
            ))}
        </div>
    </>
);

export default PendingAnswers;

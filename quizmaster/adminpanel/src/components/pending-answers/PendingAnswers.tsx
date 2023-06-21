import {Backend, PendingAnswer} from '../../restBackend';
import React from 'react';
import './PendingAnswers.scss'

interface PendingAnswersProps {
    category: string;
    answers: PendingAnswer[];
    backend: Backend;
}

const PendingAnswers = ({category, answers, backend}: PendingAnswersProps) => (
    <div className="pending-answer-container">
        <p>Answers pending:</p>
        {answers.map((answer, index) => (
            <div className="pending-answer" key={index}>
                <button onClick={() => backend.accept(category, answer.answerId)}>
                    {answer.teamName}
                </button>
                <a href={answer.answer} target="_blank">
                    Check link
                </a>
            </div>
        ))}
    </div>
);

export default PendingAnswers;

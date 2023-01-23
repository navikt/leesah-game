import React, { useState } from 'react';
import { Backend, QuizStatsDto, restBackend } from './restBackend';
import { Environment } from './environment';
import { testBackend } from './hardcodedBackend';
import './index.scss';
import CategoryView from './components/category-view/CategoryView';
import { useInterval } from './poller';

const App = () => {
    const backend: Backend = Environment.isDevelopment ? testBackend() : restBackend(false);
    const nullBoard: QuizStatsDto = { topicName: '', status: 'INACTIVE', categories: [] };
    const [quizStats, setQuizStats] = useState(nullBoard);

    useInterval(() => {
        const update = async () => {
            const response = backend.quizStats();
            setQuizStats(await response);
        };
        update().then(r => console.log('Updating...'));
    }, 1000);

    return (
        <>
            <h1>Quizmaster Admin</h1>
            <div className="header-card">
                <h2>Quiz status:</h2>
                <h2 className={quizStats.status === 'ACTIVE' ? 'active-font' : ''}>{quizStats.status}</h2>
                {quizStats.status === 'ACTIVE' ? (
                    <button onClick={() => backend.quiz('start')}>Stop</button>
                ) : (
                    <button onClick={() => backend.quiz('stop')}>Start</button>
                )}
                <p>Topic name: {quizStats.topicName}</p>
            </div>
            <CategoryView categories={quizStats.categories} backend={backend} />
        </>
    );
};

export default App;

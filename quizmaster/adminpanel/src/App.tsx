import React, { useState } from 'react';
import { Backend, QuizStatsDto, restBackend } from './restBackend';
import { Environment } from './environment';
import { testBackend } from './hardcodedBackend';
import './index.scss';
import CategoryView from './components/category-view/CategoryView';
import { useInterval } from './poller';
import { ToggleNais } from './components/toggle/ToggleNais';
import { useToggle } from './hooks/useToggle';

const App = () => {
    const backend: Backend = Environment.isDevelopment ? testBackend() : restBackend(false);
    const nullBoard: QuizStatsDto = { status: 'INACTIVE', categories: [] };
    const [quizStats, setQuizStats] = useState(nullBoard);
    const [nais, setNais] = useToggle(false); //TODO legg til variabel for å lese om vi bruker NAIS eller ikke (f.eks ved å lese hvilket topic vi bruker?)

    useInterval(() => {
        const update = async () => {
            const response = backend.quizStats();
            setQuizStats(await response);
        };
        update().then(r => console.log('Updating...'));
    }, 1000);

    const toggleCategories = () => {
        if (nais) {
            return quizStats.categories;
        } else
            return quizStats.categories.filter(
                category =>
                    category.name !== 'make-ingress' &&
                    category.name !== 'check-app-log' &&
                    category.name !== 'make-grafana-board' &&
                    category.name !== 'make-alert' &&
                    category.name !== 'setup-wonderwall'
            );
    };

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
                <ToggleNais setToggle={setNais} />
            </div>
            <CategoryView categories={toggleCategories()} backend={backend} />
        </>
    );
};

export default App;

import { CategoriesDto } from '../../restBackend';
import React from 'react';
import CategoryDetail from '../category-detail/CategoryDetail';
import PendingAnswers from '../PendingAnswers';
import './CategoryView.scss';
// @ts-ignore
import OkIkon from '../../ikoner/Success.svg';

const CategoryView = (props: { categories: CategoriesDto[]; backend: any }) => {
    const questionValue = (maxCount: number, questionCount: number) => {
        return maxCount === questionCount ? (
            <>
                {questionCount} <img src={OkIkon} alt="Ok icon" className="icon__ok" />
            </>
        ) : (
            questionCount
        );
    };

    return (
        <div className="container">
            {props.categories.map(category => (
                <div className="card">
                    <h3>{category.name}</h3>
                    <CategoryDetail label="status" value={category.status} />
                    <CategoryDetail label="maxCount" value={category.maxCount} />
                    <CategoryDetail
                        label="questionCount"
                        value={questionValue(category.maxCount, category.questionCount)}
                    />
                    <CategoryDetail label="answerCount" value={category.answerCount} />
                    <CategoryDetail label="correctAnswerCount" value={category.correctAnswerCount} />
                    {category.pendingAnswers.length > 0 && (
                        <PendingAnswers category={category.name} answers={category.pendingAnswers} />
                    )}
                    <button onClick={() => props.backend.toggle(category.name)}>
                        {category.status === 'ACTIVE' ? 'Disable' : 'Activate'}
                    </button>
                </div>
            ))}
        </div>
    );
};

export default CategoryView;

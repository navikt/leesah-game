import {Backend, CategoriesDto} from '../../restBackend';
import React from 'react';
import CategoryDetail from '../category-detail/CategoryDetail';
import PendingAnswers from '../pending-answers/PendingAnswers';
import './CategoryView.scss';
// @ts-ignore
import OkIkon from '../../ikoner/Success.svg';

interface CategoryViewProps {
    categories: CategoriesDto[];
    backend: Backend;
}

const CategoryView = ({categories, backend}: CategoryViewProps) => {
    const done = (maxCount: number, questionCount: number, status: string) => {
        return maxCount === questionCount && status === 'INACTIVE';
    };

    return (
        <div className="container">
            {categories.map((category, index) => (
                <div className="card" key={index}>
                    <div className="card-header">
                        <h3>{category.name}</h3>
                        {done(category.maxCount, category.questionCount, category.status) ? (
                            <>
                                <img src={OkIkon} alt="Ok icon" className="icon__ok"/>
                            </>
                        ) : null}
                    </div>
                    <CategoryDetail label="status">
                        {done(category.maxCount, category.questionCount, category.status) ? 'DONE' : category.status}
                    </CategoryDetail>
                    <CategoryDetail label="maxCount">{category.maxCount}</CategoryDetail>
                    <CategoryDetail label="questionCount">{category.questionCount}</CategoryDetail>
                    <CategoryDetail label="answerCount">{category.answerCount}</CategoryDetail>
                    <CategoryDetail label="correctAnswerCount">{category.correctAnswerCount}</CategoryDetail>
                    {category.pendingAnswers.length > 0 && (
                        <PendingAnswers category={category.name} answers={category.pendingAnswers} backend={backend}/>
                    )}
                    <button onClick={() => backend.toggle(category.name)}>
                        {category.status === 'ACTIVE'
                            ? 'Disable'
                            : done(category.maxCount, category.questionCount, category.status)
                                ? 'Reactivate'
                                : 'Activate'}
                    </button>
                </div>
            ))}
        </div>
    );
};

export default CategoryView;

import { Backend, CategoriesDto } from '../../restBackend';
import React from 'react';
import CategoryDetail from '../category-detail/CategoryDetail';
import PendingAnswers from '../PendingAnswers';
import './CategoryView.scss';
// @ts-ignore
import OkIkon from '../../ikoner/Success.svg';

interface CategoryViewProps {
    categories: CategoriesDto[];
    backend: Backend;
}
const CategoryView = ({ categories, backend }: CategoryViewProps) => {
    const done = (maxCount: number, questionCount: number, status: string) => {
        return maxCount === questionCount && status === 'INACTIVE';
    };

    return (
        <div className="container">
            {categories.map((category, index) => (
                <div className="card" key={index}>
                    <h3>
                        {done(category.maxCount, category.questionCount, category.status) ? (
                            <>
                                {category.name} <img src={OkIkon} alt="Ok icon" className="icon__ok" />
                            </>
                        ) : (
                            category.name
                        )}
                    </h3>
                    <CategoryDetail label="status">
                        {done(category.maxCount, category.questionCount, category.status) ? 'DONE' : category.status}
                    </CategoryDetail>
                    <CategoryDetail label="maxCount">{category.maxCount}</CategoryDetail>
                    <CategoryDetail label="questionCount">{category.questionCount}</CategoryDetail>
                    <CategoryDetail label="answerCount">{category.answerCount}</CategoryDetail>
                    <CategoryDetail label="correctAnswerCount">{category.correctAnswerCount}</CategoryDetail>
                    {category.pendingAnswers.length > 0 && (
                        <PendingAnswers category={category.name} answers={category.pendingAnswers} backend={backend} />
                    )}
                    <button onClick={() => backend.toggle(category.name)}>
                        {category.status === 'ACTIVE' ? 'Disable' : 'Activate'}
                    </button>
                </div>
            ))}
        </div>
    );
};

export default CategoryView;

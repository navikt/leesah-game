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
                    <CategoryDetail
                        label="status"
                        value={
                            done(category.maxCount, category.questionCount, category.status) ? 'DONE' : category.status
                        }
                    />
                    <CategoryDetail label="maxCount" value={category.maxCount} />
                    <CategoryDetail label="questionCount" value={category.questionCount} />
                    <CategoryDetail label="answerCount" value={category.answerCount} />
                    <CategoryDetail label="correctAnswerCount" value={category.correctAnswerCount} />
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

import React, { ReactNode } from 'react';
import './CategoryDetail.scss';

interface CategoryDetailProps {
    label: string;
    value: ReactNode;
}

const CategoryDetail = ({ label, value }: CategoryDetailProps) => (
    <div className="category-view">
        <p className="status-label">{label + ':'} </p>
        <p className={value === 'ACTIVE' ? 'active-font' : ''}>{value}</p>
    </div>
);

export default CategoryDetail;

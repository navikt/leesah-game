import React, { ReactNode } from 'react';
import './CategoryDetail.scss';

interface CategoryDetailProps {
    label: string;
    children: ReactNode;
}

const CategoryDetail = ({ label, children }: CategoryDetailProps) => (
    <div className="category-view">
        <p className="status-label">{label + ':'} </p>
        <p className={children === 'ACTIVE' ? 'active-font' : ''}>{children}</p>
    </div>
);

export default CategoryDetail;

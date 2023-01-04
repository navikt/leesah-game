import React from 'react';
import './CategoryDetail.scss';

const CategoryDetail = (props: { label: string; value: any }) => (
    <div className="category-view">
        <p className="status-label">{props.label + ':'} </p>
        <p className={props.value === 'ACTIVE' ? 'active-font' : ''}>{props.value}</p>
    </div>
);

export default CategoryDetail;

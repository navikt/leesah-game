import React from "react";

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {}

export const Card: React.FC<CardProps> = ({ className, children, ...rest }) => (
    <div className={"card " + className} {...rest}>
        {children}
    </div>
);
Card.displayName="Card"

export const ContentCard: React.FC<CardProps> = ({ className, children, ...rest }) => (
    <div className={"contentCard " + className} {...rest}>
        {children}
    </div>
);
Card.displayName="ContentCard"

export const HeaderCard: React.FC<CardProps> = ({ className, children, ...rest }) => (
    <div className={"headerCard " + className} {...rest}>
        {children}
    </div>
);
HeaderCard.displayName="HeaderCard"

export const LabelCard: React.FC<{text: string }> = ({ text }) => (
    <div className={"labelCard "}>
        {text}
    </div>
);
LabelCard.displayName="LabelCard"

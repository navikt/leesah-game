import React, { ReactNode } from "react";
import "./CategoryDetail.scss";
import { BodyShort } from "@navikt/ds-react";

interface CategoryDetailProps {
  label: string;
  children: ReactNode;
}

const CategoryDetail = ({ label, children }: CategoryDetailProps) => (
  <div className="category-view">
    <p className="status-label">{label + ":"} </p>
    <BodyShort
      size="small"
      className={children === "ACTIVE" ? "active-font" : ""}
    >
      {children}
    </BodyShort>
  </div>
);

export default CategoryDetail;

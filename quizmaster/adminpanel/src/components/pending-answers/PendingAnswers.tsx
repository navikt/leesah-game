import { Backend, PendingAnswer } from "../../api/restBackend";
import React from "react";
import "./PendingAnswers.scss";
import { BodyShort, Button, Heading } from "@navikt/ds-react";
import { CheckmarkCircleIcon } from "@navikt/aksel-icons";

interface PendingAnswersProps {
  category: string;
  answers: PendingAnswer[];
  backend: Backend;
}

const PendingAnswers = ({
  category,
  answers,
  backend,
}: PendingAnswersProps) => (
  <div className="pending-answer-container">
    <Heading size="xsmall" level="3">
      Answers pending:
    </Heading>
    {answers.map((answer, index) => (
      <div className="pending-answer" key={index}>
        <BodyShort className="teamname-header">{answer.teamName}</BodyShort>
        <div className="pending-answer__check">
          <a href={answer.answer} target="_blank">
            <BodyShort className="pending-answer__text">Check link</BodyShort>
          </a>
          <Button
            variant="secondary"
            size="small"
            onClick={() => backend.acceptAnswer(category, answer.answerId)}
          >
            <CheckmarkCircleIcon title="a11y-title" fontSize="1.5rem" />
          </Button>
        </div>
      </div>
    ))}
  </div>
);

export default PendingAnswers;

import { ToggleNais } from "../toggle/ToggleNais";
import { Backend } from "../../api/restBackend";
import "./HeaderCard.scss";
import { DeleteTeam } from "../delete-team/DeleteTeam";
import { Button, Heading } from "@navikt/ds-react";

interface HeaderCardProps {
  quizStatsStatus: string;
  backend: Backend;
  setNais: () => void;
  nais: boolean;
}

export const HeaderCard = ({
  quizStatsStatus,
  backend,
  setNais,
  nais,
}: HeaderCardProps) => {
  return (
    <div className="header-card">
      <div className="header-card__left">
        <Heading size="small" level="2">
          Quiz status:
        </Heading>
        <Heading
          size="small"
          level="2"
          className={quizStatsStatus === "ACTIVE" ? "active-font" : ""}
        >
          {quizStatsStatus}
        </Heading>
        {quizStatsStatus === "ACTIVE" ? (
          <Button variant="secondary" onClick={() => backend.quiz("start")}>
            Stop
          </Button>
        ) : (
          <Button variant="secondary" onClick={() => backend.quiz("stop")}>
            Start
          </Button>
        )}
        <ToggleNais setToggle={setNais} toggle={nais} />
      </div>

      <div className="header-card__right">
        <DeleteTeam backend={backend} />
      </div>
    </div>
  );
};

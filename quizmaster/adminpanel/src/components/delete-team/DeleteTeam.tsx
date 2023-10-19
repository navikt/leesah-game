import { BodyShort, Button, Modal, TextField } from "@navikt/ds-react";
import "../header-card/HeaderCard.scss";
import { Backend } from "../../api/restBackend";
import { useState } from "react";

interface Props {
  backend: Backend;
}

export const DeleteTeam = ({ backend }: Props) => {
  const [teamname, setTeamname] = useState("");
  const [showModal, setShowModal] = useState(false);

  return (
    <>
      <TextField
        label="Hvilket team vil du slette?"
        className="delete-team__textfield"
        onChange={(value) => setTeamname(value?.target.value)}
      />
      <Button variant="danger" onClick={() => setShowModal(true)}>
        Slett
      </Button>

      <Modal open={showModal}>
        <Modal.Body>
          <BodyShort>
            Er du sikker på at du vil slette teamet {teamname}?
          </BodyShort>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="tertiary" onClick={() => setShowModal(false)}>
            Avbryt
          </Button>
          <Button
            variant="danger"
            onClick={() => {
              backend.deleteTeam(teamname);
              //hvis den returnerer 200 at teamet har blitt godkjent
              setShowModal(false);
              location.reload();
            }}
          >
            Slett
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

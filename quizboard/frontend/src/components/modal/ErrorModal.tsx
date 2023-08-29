import { Alert, Modal } from '@navikt/ds-react';
import { Environment } from '../../environment';
import React, { useState } from 'react';
import { useInterval } from '../../hooks/useInterval';
import { isBackendReady } from '../../backend_mock';

export const ErrorModal = () => {
  const [backendGood, setbackendGood] = useState(true);

  useInterval(async () => {
    const update = async () => {
      setbackendGood(await isBackendReady());
    };
    update().then(() => console.log('Updating...'));
  }, 10000);

  return (
    <Modal
      open={!Environment.isDevelopment && !backendGood}
      onClose={() => backendGood}
      header={{ heading: '', closeButton: false }}
    >
      <Modal.Body>
        <Alert variant="error">ERROR: Backend is not ready or not alive</Alert>
      </Modal.Body>
    </Modal>
  );
};

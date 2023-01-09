import { Alert, Modal } from '@navikt/ds-react';
import { Environment } from '../../environment';
import React, { useState } from 'react';
import { useInterval } from '../../hooks/useInterval';
import { isBackendReady } from '../../backend';

export const ErrorModal = () => {
  const [backendGood, setbackendGood] = useState(false);

  useInterval(async () => {
    const update = async () => {
      const ready = isBackendReady();
      setbackendGood(await ready);
    };
    update().then(r => console.log('Updating...'));
  }, 10000);

  return (
    <Modal open={Environment.isDevelopment && !backendGood} onClose={() => backendGood} closeButton={false}>
      <Modal.Content>
        <Alert variant="error">ERROR: Backend is not ready or not alive</Alert>
      </Modal.Content>
    </Modal>
  );
};

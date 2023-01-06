import React, {useEffect, useState} from 'react';
import './index.less';
import { Heading, Ingress } from '@navikt/ds-react';
import LeaderboardTable from './Leaderboard/LeaderboardTable';
import {hentBoard, isBackendAlive, isBackendReady} from "./backend";
import {useInterval} from "./poller";

const App = () => {
    const [backendGood, setbackendGood] = useState(true);

    useInterval(async () => {
        const update = async () => {
            const ready = isBackendReady()
            setbackendGood(await ready);
        };
        update().then(r=>console.log('Updating...'));
    }, 10000);

  return (
    <div className="app">
      <Heading size="xlarge" level="1" className="header">
        Leaderboard
      </Heading>
      <Ingress className="sub-header">
        Livet er en strøm av{' '}
        <span className="strikethrough-line">
          <span className="strikethrough-header">hendelser</span>
        </span>{' '}
        spørsmål
      </Ingress>
        {!backendGood && <span className={"errortext"}>
            ERROR: Backend is not ready or not alive
        </span>}
      <LeaderboardTable />
    </div>
  );
};

export default App;

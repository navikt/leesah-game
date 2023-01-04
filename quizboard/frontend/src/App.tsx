import React from 'react';
import './index.less';
import { Heading, Ingress } from '@navikt/ds-react';
import LeaderboardTable from './Leaderboard/LeaderboardTable';

const App = () => {
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
      <LeaderboardTable />
    </div>
  );
};

export default App;

import React from 'react';
import './index.less';
import { Heading, Ingress } from '@navikt/ds-react';
import LeaderboardTable from './components/leaderboard/LeaderboardTable';
import { ErrorModal } from './components/modal/ErrorModal';

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
        spørsmål!
      </Ingress>
      <LeaderboardTable />
      <ErrorModal />
    </div>
  );
};

export default App;

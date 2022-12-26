import React, { useEffect, useState } from 'react';
import ErrorIkon from '../ikoner/Error.svg';
import WarningIkon from '../ikoner/Warning.svg';
import OkIkon from '../ikoner/Success.svg';
import './LeaderboardTable.less';
import { BoardDto } from '../types';
import { hentBoard } from '../backend';
import { Table } from '@navikt/ds-react';
import '@navikt/ds-css';
import { useLocalStorageState } from '../Hooks/useLocalStorageState';

export default function LeaderboardTable() {
  const nullBoard: BoardDto = { board: [] };
  const [board, setBoard] = useState(nullBoard);
  const [sort, setSort] = useLocalStorageState('sortState', { orderBy: 'MOTTATT', direction: 'ascending' });

  useEffect(hentBoard(setBoard), []);

  //Todo fikse sorteringen, skal alltid være sortert på score
  useEffect(() => {
    setSort({
      orderBy: 'score',
      direction: 'ascending',
    });
  }, []);

  const icon = (status: String) => {
    if (status === 'FAILURE') {
      return <img src={ErrorIkon} alt="Error icon" className="icon_FAILURE" />;
    } else if (status === 'PENDING') {
      return <img src={WarningIkon} alt="Warning icon" className="icon_PENDING" />;
    } else if (status === 'OK') {
      return <img src={OkIkon} alt="Ok icon" className="icon_OK" />;
    }
  };

  function hexToRgb(hex: string) {
    const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result
      ? {
          r: parseInt(result[1], 16),
          g: parseInt(result[2], 16),
          b: parseInt(result[3], 16),
        }
      : null;
  }

  function colorPicker(hex: string) {
    const red = hexToRgb(hex)?.r!;
    const green = hexToRgb(hex)?.g!;
    const blue = hexToRgb(hex)?.b!;

    const brightness = (red * 299 + green * 587 + blue * 114) / 1000;

    if (brightness < 127.5) {
      return 'white';
    }
    return 'black';
  }

  return (
    <Table
      size="small"
      zebraStripes
      className="leaderboard"
      sort={sort}
      // onSortChange={() => {
      //   setSort({
      //     orderBy: 'score',
      //     direction: sort.direction === 'ascending' ? 'descending' : 'ascending',
      //   });
      // }}
    >
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell scope="col" className="leaderboard__plassering">
            {}
          </Table.HeaderCell>
          <Table.HeaderCell scope="col" className="leaderboard__teamnavn">
            Teamnavn
          </Table.HeaderCell>
          <Table.ColumnHeader scope="col" className="leaderboard__score" sortKey="score">
            Score
          </Table.ColumnHeader>
          {board.board[0]?.categoryResult.map((category: any, index: number) => (
            <Table.HeaderCell key={index} scope="col" className="leaderboard__kategori">
              {category.name}
            </Table.HeaderCell>
          ))}
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {board.board.map((team: any, index: number) => (
          <Table.Row key={index}>
            <Table.HeaderCell scope="row" className="leaderboard__plassering">
              {index}.
            </Table.HeaderCell>
            <Table.HeaderCell
              scope="row"
              className="leaderboard__teamnavn"
              style={{ backgroundColor: `#${team.hex}`, color: colorPicker(team.hex) }}
            >
              {team.name}
            </Table.HeaderCell>
            <Table.DataCell
              className="leaderboard__score"
              style={{ backgroundColor: `#${team.hex}`, color: colorPicker(team.hex) }}
            >
              {team.score}
            </Table.DataCell>
            {team.categoryResult.map((category: any, index: number) => (
              <Table.DataCell key={index} className="leaderboard__icon">
                {icon(category.status)}
              </Table.DataCell>
            ))}
          </Table.Row>
        ))}
      </Table.Body>
    </Table>
  );
}

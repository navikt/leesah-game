import React, { useEffect, useState } from 'react';
import ErrorIkon from '../../ikoner/Error.svg';
import WarningIkon from '../../ikoner/Warning.svg';
import OkIkon from '../../ikoner/Success.svg';
import './LeaderboardTable.scss';
import { BoardDto } from '../../types';
import { hentBoard } from '../../backend_mock';
import { BodyShort, Table } from '@navikt/ds-react';
import '@navikt/ds-css';
import { useLocalStorageState } from '../../hooks/useLocalStorageState';
import { kebabCase } from '../../utils';

export default function LeaderboardTable() {
  const nullBoard: BoardDto = { board: [] };
  const [board, setBoard] = useState(nullBoard);
  const [sort, setSort] = useLocalStorageState('sortState', { orderBy: 'score', direction: 'descending' });
  let sortData = board.board;
  let position = 0;

  useEffect(hentBoard(setBoard), []);

  useEffect(() => {
    setSort({
      orderBy: 'score',
      direction: 'descending',
    });
  }, [sortData]);

  const icon = (status: String, index: number) => {
    if (status === 'FAILURE') {
      return <img key={index} src={ErrorIkon} alt="Error icon" className="icon__failure" />;
    } else if (status === 'PENDING') {
      return <img key={index} src={WarningIkon} alt="Warning icon" className="icon__pending" />;
    } else if (status === 'OK') {
      return <img key={index} src={OkIkon} alt="Ok icon" className="icon__ok" />;
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
    const hexUtenHash = () => {
      if (hex.includes('#')) {
        return hex.replace('#', '');
      } else {
        return hex;
      }
    };

    const red = hexToRgb(hexUtenHash())?.r!;
    const green = hexToRgb(hexUtenHash())?.g!;
    const blue = hexToRgb(hexUtenHash())?.b!;

    const brightness = (red * 299 + green * 587 + blue * 114) / 1000;
    if (brightness < 127.5) {
      return 'white';
    }
    return 'black';
  }

  sortData = sortData.slice().sort((a, b) => {
    if (sort) {
      const comparator = ({ a, b, orderBy }: { a: any; b: any; orderBy: any }) => {
        if (b[orderBy] < a[orderBy] || b[orderBy] === undefined) {
          return -1;
        }
        if (b[orderBy] > a[orderBy]) {
          return 1;
        }
        return 0;
      };

      return sort.direction === 'descending'
        ? comparator({ a: a, b: b, orderBy: sort.orderBy })
        : comparator({ a: b, b: a, orderBy: sort.orderBy });
    }
    return 1;
  });

  return board.board.length === 0 ? (
    <></>
  ) : (
    <Table size="small" zebraStripes className="leaderboard" sort={sort}>
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell scope="col" className="leaderboard__header">
            🏆
          </Table.HeaderCell>
          <Table.HeaderCell scope="col" className="leaderboard__header">
            Team
          </Table.HeaderCell>
          <Table.ColumnHeader scope="col" className="leaderboard__header" sortKey="score">
            Score
          </Table.ColumnHeader>
          {board.board[0]?.categoryResult.map((category: any) => (
            <Table.ColumnHeader
              key={category.name}
              scope="col"
              className="leaderboard__header__kategori leaderboard__header"
            >
              {category.name}
            </Table.ColumnHeader>
          ))}
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {sortData.map((team: any, index: number) => (
          <Table.Row key={team.name}>
            <Table.HeaderCell scope="row" className="leaderboard__plassering">
              {index === 0 ? position : team.score === sortData[index - 1].score ? position : (position = index)}.
            </Table.HeaderCell>
            <Table.HeaderCell
              scope="row"
              className="leaderboard__teamnavn"
              style={{
                backgroundColor: `#${team.hex.includes('#') ? team.hex.replace('#', '') : team.hex}`,
                color: colorPicker(team.hex),
              }}
            >
              {team.name}
            </Table.HeaderCell>
            <Table.DataCell
              className="leaderboard__score"
              style={{
                backgroundColor: `#${team.hex.includes('#') ? team.hex.replace('#', '') : team.hex}`,
                color: colorPicker(team.hex),
              }}
            >
              {team.score}
            </Table.DataCell>
            {team.categoryResult.map((category: any, index: number) => {
              return (
                <Table.DataCell key={kebabCase(team.name) + index}>
                  <div className="leaderboard__kategori">
                    {icon(category.status, index)}
                    <BodyShort>
                      {category.okCount} / {team.categoryCount[category.name]}
                    </BodyShort>
                  </div>
                </Table.DataCell>
              );
            })}
          </Table.Row>
        ))}
      </Table.Body>
    </Table>
  );
}

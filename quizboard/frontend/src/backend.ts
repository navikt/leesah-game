import { BoardDto } from './types';
import { Environment } from './environment';

const testData: BoardDto = {
  board: [
    {
      name: 'Pølsedalt',
      score: 60,
      hex: '000000',
      categoryCount: { 'team-registration': 1, arithmetic: 5, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 5,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Team Vera',
      score: 10,
      hex: 'FFFFFF',
      categoryCount: { 'team-registration': 1, arithmetic: 0, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'FAILURE',
          okCount: 0,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Team Anne',
      score: 145,
      hex: '1430CA',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'FAILURE',
          okCount: 5,
        },
      ],
    },
    {
      name: 'HEUHEUHUEE',
      score: 90,
      hex: '1410CA',
      categoryCount: { 'team-registration': 1, arithmetic: 5, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'O jul med din glede',
      score: 110,
      hex: 'C13555',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Speidern',
      score: 110,
      hex: 'CA14B7',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'FAILURE',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Pipeleke',
      score: 110,
      hex: 'FF018F',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Tullete teamnavn',
      score: 110,
      hex: '285557',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'FAILURE',
          okCount: 5,
        },
      ],
    },
    {
      name: 'MamaaaaaOOOOOOOO',
      score: 110,
      hex: '292444',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'FAILURE',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Elton John',
      score: 110,
      hex: '195228',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Spice Girls',
      score: 110,
      hex: 'FFFF00',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 4 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Viva Forever',
      score: 110,
      hex: 'FF00FF',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 5 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'OK',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Spice Up Your Life',
      score: 110,
      hex: 'FF1122',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 5 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'OK',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Norefjell blir superkult',
      score: 110,
      hex: '14CA5C',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'ØAHGJANØKGJNDHAERHSDFHSFJ',
      score: 110,
      hex: 'CA6F14',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Flåklypa Grand Prix',
      score: 110,
      hex: '9014CA',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Støvsuger',
      score: 110,
      hex: '00FF11',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Lønahorgi',
      score: 110,
      hex: 'FF0099',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Prokrastinering',
      score: 110,
      hex: 'F875CA',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Juletre',
      score: 110,
      hex: '0000F8',
      categoryCount: { 'team-registration': 1, arithmetic: 4, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 4,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Birra🍻',
      score: 110,
      hex: '00FFFF',
      categoryCount: { 'team-registration': 1, arithmetic: 2, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 2,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
    {
      name: 'Sommerferie',
      score: 110,
      hex: '234789',
      categoryCount: { 'team-registration': 1, arithmetic: 1, 'min-max': 6 },
      categoryResult: [
        {
          name: 'team-registration',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'arithmetic',
          status: 'OK',
          okCount: 1,
        },
        {
          name: 'min-max',
          status: 'PENDING',
          okCount: 5,
        },
      ],
    },
  ],
};

const baseurl = Environment.isDevelopment ? 'http://localhost:8081' : '';

export async function isBackendAlive() {
  return fetch(`${baseurl}/alive`)
    .then(res => res.ok)
    .catch(() => false);
}

export async function isBackendReady() {
  return fetch(`${baseurl}/ready`)
    .then(res => res.ok)
    .catch(() => false);
}

export function hentBoard(setBoard: (value: ((prevState: BoardDto) => BoardDto) | BoardDto) => void) {
  return () => {
    if (Environment.isDevelopment) {
      setBoard(testData);
      return;
    }

    const eventSource = new EventSource(`${baseurl}/board`);

    eventSource.addEventListener('message', e => {
      setBoard(JSON.parse(e.data));
    });
    eventSource.addEventListener('error', e => {
      console.error('Received error event:');
    });

    return () => {
      return eventSource.close();
    };
  };
}

import { Backend, BoardDto, errorWhenBadResponse, wrapNetworkErrors } from './restBackend';


export const testBackend = (): Backend => {
    return {
        board(): Promise<BoardDto> {
            return Promise.resolve(testData);
        },
    };
};

let testData = {
    'board': [{
        'name': 'Team Solo',
        'score': 800,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }],
    }, {
        'name': 'Team Vera',
        'score': 100,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'FAILURE',
            'okCount': 0,
        }],
    }, {
        'name': 'Team Anne',
        'score': 1100,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }],
};

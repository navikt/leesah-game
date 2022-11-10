import { Backend, BoardDto } from './restBackend';

export const testBackend = (): Backend => {
    return {
        board(): Promise<BoardDto> {
            return Promise.resolve(testData);
        },
    };
};

let testData = {
    'board': [{
        'name': 'Team SoloPolo Pølsedalt',
        'score': 60,
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
        'score': 10,
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
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'HEUHEUHUEE',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'Norefjell blir superkult',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'ØAHGJANØKGJNDHAERHSDFHSFJFHJLYAETKJLKHFITDUR',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'Flåklypa Grand Prix',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'HEI SONDRE OG ULRIK',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'Team Berit',
        'score': 120,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }],
    }, {
        'name': 'Team Sara',
        'score': 110,
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

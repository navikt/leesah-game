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
        'name': 'Pølsedalt',
        'score': 60,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }],
    },
        {
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
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
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }],
    }, {
        'name': 'SoloPolo',
        'score': 140,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }],
    }, {
        'name': 'Test',
        'score': 110,
        'categoryResult': [{
            'name': 'team-registration',
            'status': 'OK',
            'okCount': 1,
        }, {
            'name': 'arithmetic',
            'status': 'OK',
            'okCount': 10,
        }, {
            'name': 'arithmetic',
            'status': 'PENDING',
            'okCount': 5,
        }],
    }],
};

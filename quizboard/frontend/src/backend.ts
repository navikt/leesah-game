import {BoardDto} from "./types";
import {Environment} from "./environment";

const testData: BoardDto = {
    'board': [{
        'name': 'Team SoloPolo Pølsedalt',
        'score': 60,
        'hex': '000000',
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
        'hex': '079E40',
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
        'hex': '1430CA',
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
        'hex': 'CA14CA',
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
        'hex': '14CA5C',
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
        'hex': 'CA6F14',
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
        'hex': '9014CA',
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
        'hex': 'CA14B7',
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
        'score': 140,
        'hex': '14CA1A',
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
        'hex': '0BEEDD',
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

const baseurl = Environment.isDevelopment ? "http://localhost:8081" : ""

export function hentBoard(setBoard: (value: (((prevState: BoardDto) => BoardDto) | BoardDto)) => void) {
    return () => {
        if (Environment.isDevelopment) {
            setBoard(testData)
            return
        }

        const eventSource = new EventSource(`${baseurl}/sse`)

        eventSource.addEventListener("message", (e) => {
            setBoard(JSON.parse(e.data))
        })
        eventSource.addEventListener("error", (e) => {
            console.error("Received error event:")
        })

        return () => {
            return eventSource.close()
        }
    };
}

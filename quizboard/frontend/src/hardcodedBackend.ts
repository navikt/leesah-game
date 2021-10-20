import {Backend, BoardDto, errorWhenBadResponse, wrapNetworkErrors} from "./restBackend";


export const testBackend = (): Backend => {
    return {
        board(): Promise<BoardDto> {
            return Promise.resolve(testData)
        }
    }
}

let testData = {
    "board": [{
        "name": "test",
        "score": 100,
        "categoryResult": [{"name": "team-registration", "status": "OK"}, {
            "name": "arithmetic",
            "status": "PENDING"
        }]
    }, {
        "name": "coolteam",
        "score": 100,
        "categoryResult": [{"name": "team-registration", "status": "OK"}, {
            "name": "arithmetic",
            "status": "FAILURE"
        }]
    }]
}
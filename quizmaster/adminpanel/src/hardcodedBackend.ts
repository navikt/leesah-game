import {Backend, CategoriesDto, errorWhenBadResponse, QuizStatsDto, wrapNetworkErrors} from "./restBackend";


export const testBackend = (): Backend => {
    return {
        quiz(stop: string): Promise<QuizStatsDto> {
            return Promise.resolve(testData)
        },
        quizStats(): Promise<QuizStatsDto> {
            return Promise.resolve(testData)
        },
        toggle(category: string): Promise<CategoriesDto> {
            return Promise.resolve(testCategoryData)
        }
    }
}

let testData = {
    "status": "ACTIVE",
    "categories": [
        {
            "name": "team-registration",
            "status": "ACTIVE",
            "maxCount": 1,
            "questionCount": 1,
            "answerCount": 0,
            "correctAnswerCount": 0
        },
        {
            "name": "arithmetic",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0
        },
        {
            "name": "ping-pong",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0
        },
        {
            "name": "event-question-1",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0
        },
        {
            "name": "nav-question-1",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0
        }
    ]
}

let testCategoryData = {
            "name": "arithmetic",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0
        }

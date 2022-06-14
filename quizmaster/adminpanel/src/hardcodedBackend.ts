import {Backend, CategoriesDto, QuizStatsDto} from "./restBackend";


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
        },
        accept(category: string, answerId: string): Promise<CategoriesDto> {
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
            "correctAnswerCount": 0,
            "pendingAnswers": []
        },
        {
            "name": "arithmetic",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": []
        },
        {
            "name": "ping-pong",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": []
        },
        {
            "name": "event-question-1",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": []
        },
        {
            "name": "nav-question-1",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": []
        },
        {
            "name": "pending-question",
            "status": "INACTIVE",
            "maxCount": 1,
            "questionCount": 1,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": [
                {"teamName": "testteam", "answerId": "1234", "answer": "https://www.google.com"},
                {"teamName": "testteam1", "answerId": "1234", "answer": "google.com"},
                {"teamName": "testteam2", "answerId": "1234", "answer": "google.com"},
                {"teamName": "testteam3", "answerId": "1234", "answer": "google.com"},
                {"teamName": "testteam4", "answerId": "1234", "answer": "google.com"},
                {"teamName": "testteam5", "answerId": "1234", "answer": "google.com"},
            ]
        }
    ]
}

let testCategoryData = {
            "name": "arithmetic",
            "status": "INACTIVE",
            "maxCount": 10,
            "questionCount": 0,
            "answerCount": 0,
            "correctAnswerCount": 0,
            "pendingAnswers": []
        }

import { Backend, CategoriesDto, QuizStatsDto } from './restBackend';

export const testBackend = (): Backend => {
    return {
        quiz(stop: string): Promise<QuizStatsDto> {
            return Promise.resolve(testData);
        },
        quizStats(): Promise<QuizStatsDto> {
            return Promise.resolve(testData);
        },
        toggle(category: string): Promise<CategoriesDto> {
            return Promise.resolve(testCategoryData);
        },
        accept(category: string, answerId: string): Promise<CategoriesDto> {
            return Promise.resolve(testCategoryData);
        },
    };
};

let testData = {
    status: 'ACTIVE',
    categories: [
        {
            name: 'team-registration',
            status: 'ACTIVE',
            maxCount: 1,
            questionCount: 1,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'arithmetic',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 10,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'NAV',
            status: 'INACTIVE',
            maxCount: 2,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'deduplication',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'transactions',
            status: 'INACTIVE',
            maxCount: 20,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'ping-pong',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'base64',
            status: 'INACTIVE',
            maxCount: 5,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'is-a-prime',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'grunnbelop',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'min-max',
            status: 'INACTIVE',
            maxCount: 10,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'make-ingress',
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'check-app-log',
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'make-grafana-board',
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'make-alert',
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'setup-wonderwall',
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 0,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
    ],
};

let testCategoryData = {
    name: 'arithmetic',
    status: 'INACTIVE',
    maxCount: 10,
    questionCount: 0,
    answerCount: 0,
    correctAnswerCount: 0,
    pendingAnswers: [],
};

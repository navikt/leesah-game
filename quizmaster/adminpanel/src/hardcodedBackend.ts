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
            status: 'INACTIVE',
            maxCount: 1,
            questionCount: 1,
            answerCount: 0,
            correctAnswerCount: 0,
            pendingAnswers: [],
        },
        {
            name: 'NAV',
            status: 'INACTIVE',
            maxCount: 2,
            questionCount: 2,
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
            name: 'arithmetic',
            status: 'ACTIVE',
            maxCount: 10,
            questionCount: 8,
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
            name: 'transactions',
            status: 'INACTIVE',
            maxCount: 20,
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
            name: 'deduplication',
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
        // {
        //     name: 'pending-question',
        //     status: 'INACTIVE',
        //     maxCount: 1,
        //     questionCount: 1,
        //     answerCount: 0,
        //     correctAnswerCount: 0,
        //     pendingAnswers: [
        //         { teamName: 'testteam', answerId: '1234', answer: 'https://www.google.com' },
        //         { teamName: 'testteam1', answerId: '1234', answer: 'google.com' },
        //         { teamName: 'testteam2', answerId: '1234', answer: 'google.com' },
        //         { teamName: 'testteam3', answerId: '1234', answer: 'google.com' },
        //         { teamName: 'testteam4', answerId: '1234', answer: 'google.com' },
        //         { teamName: 'testteam5', answerId: '1234', answer: 'google.com' },
        //     ],
        // },
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

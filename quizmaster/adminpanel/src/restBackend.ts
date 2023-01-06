export type QuizStatsDto = {
    status: string;
    categories: CategoriesDto[];
};

export type CategoriesDto = {
    name: string;
    status: string;
    maxCount: number;
    questionCount: number;
    answerCount: number;
    correctAnswerCount: number;
    pendingAnswers: PendingAnswer[];
};

export type PendingAnswer = {
    teamName: string;
    answerId: string;
    answer: string;
};

export type Backend = {
    quizStats: () => Promise<QuizStatsDto>;
    toggle: (category: string) => Promise<CategoriesDto>;
    quiz: (stop: string) => Promise<QuizStatsDto>;
    accept(answerId: string, teamName: string): Promise<CategoriesDto>;
};

export const restBackend = (development: boolean): Backend => {
    const baseUrl: string = development ? 'http://localhost:8000' : '';
    return {
        quiz(action: string): Promise<QuizStatsDto> {
            return fetch(`${baseUrl}/quiz`, {
                method: 'put',
                headers: {
                    Accept: 'application/json',
                },
                body: JSON.stringify({ action: action }),
            })
                .catch(wrapNetworkErrors)
                .then(errorWhenBadResponse)
                .then(response => response.json());
        },
        quizStats(): Promise<QuizStatsDto> {
            return fetch(`${baseUrl}/quiz-stats`, {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                },
            })
                .catch(wrapNetworkErrors)
                .then(errorWhenBadResponse)
                .then(response => response.json());
        },
        toggle(category: string): Promise<CategoriesDto> {
            return fetch(`${baseUrl}/categories/${category}`, {
                method: 'put',
                headers: {
                    Accept: 'application/json',
                },
            })
                .catch(wrapNetworkErrors)
                .then(errorWhenBadResponse)
                .then(response => response.json());
        },
        accept(category: string, teamName: string): Promise<CategoriesDto> {
            return fetch(`${baseUrl}/categories/${category}/${teamName}`, {
                method: 'put',
                headers: {
                    Accept: 'application/json',
                },
            })
                .catch(wrapNetworkErrors)
                .then(errorWhenBadResponse)
                .then(response => response.json());
        },
    };
};

export const wrapNetworkErrors = (e: Error) => {
    throw new httpError(e);
};

export class httpError extends Error {
    cause: Error;

    constructor(cause: Error) {
        super(`Failure during network request: ${cause.message}`);
        this.name = 'httpError';
        this.cause = cause;
    }
}

export const errorWhenBadResponse = async (response: Response): Promise<Response> => {
    if (!response.ok) {
        throw await fraResponse(response);
    }
    return response;
};

const fraResponse = async (response: Response): Promise<backendError> => {
    let feilDto: ErrorDto | undefined;
    try {
        feilDto = await response.json();
    } catch (e) {
        console.log('Received respons not matching ErrorDto');
    }

    return createBackendError(response.status, feilDto);
};

export const createBackendError = (status: number, errorDto?: ErrorDto) => {
    const feilId = errorDto?.error_id;
    const message = !!errorDto
        ? `Error from server: ${status}. error code: ${errorDto.error_id}. ${errorDto.description}`
        : `Error from server: ${status}`;
    return new backendError(message, status, feilId);
};

export class backendError extends Error {
    feilId: string | undefined;
    status: number;

    constructor(message: string, status: number, feilId: string | undefined) {
        super(message);
        this.feilId = feilId;
        this.name = 'backendError';
        this.status = status;
    }
}

export type ErrorDto = {
    error_id: string;
    description?: string;
    [x: string]: any;
};

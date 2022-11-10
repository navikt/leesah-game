

export type BoardDto = {
    board: TeamResultDto[]
}

export type TeamResultDto ={
    name: string
    score: number,
    hex: string,
    categoryResult: CategoryResultDto[]
}

export type CategoryResultDto ={
    name: string,
    status: string
}

export type Backend = {
    board: () => Promise<BoardDto>
}

export const restBackend = (development: boolean): Backend => {
    const baseUrl: string =
        development ? "http://localhost:8081" : "";
    return {
        board(): Promise<BoardDto> {
            return fetch(`${baseUrl}/board`, {
                method: 'get',
                headers: {
                    Accept: 'application/json',
                }
            })
                .catch(wrapNetworkErrors)
                .then(errorWhenBadResponse)
                .then(response => response.json())
        }
    }
}

export const wrapNetworkErrors = (e: Error) => {
    throw new httpError(e)
}

export class httpError extends Error {
    cause: Error

    constructor(cause: Error) {
        super(`Failure during network request: ${cause.message}`)
        this.name = 'httpError'
        this.cause = cause
    }
}

export const errorWhenBadResponse = async (response: Response): Promise<Response> => {
    if (!response.ok) {
        throw await fraResponse(response)
    }
    return response
}

const fraResponse = async (response: Response): Promise<backendError> => {
    let feilDto: ErrorDto | undefined
    try {
        feilDto = await response.json()
    } catch (e) {
        console.log('Received respons not matching ErrorDto')
    }

    return createBackendError(response.status, feilDto)
}

export const createBackendError = (status: number, errorDto?: ErrorDto) => {
    const feilId = errorDto?.error_id
    const message = !!errorDto
        ? `Error from server: ${status}. error code: ${errorDto.error_id}. ${errorDto.description}`
        : `Error from server: ${status}`
    return new backendError(message, status, feilId)
}

export class backendError extends Error {
    feilId: string | undefined
    status: number

    constructor(message: string, status: number, feilId: string | undefined) {
        super(message)
        this.feilId = feilId
        this.name = 'backendError'
        this.status = status
    }
}

export type ErrorDto = {
    error_id: string
    description?: string
    [x: string]: any
}

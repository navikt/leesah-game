export type BoardDto = {
    board: TeamResultDto[]
}

export type TeamResultDto ={
    name: string
    score: number,
    categoryResult: CategoryResultDto[]
}

export type CategoryResultDto ={
    name: string,
    status: string
    okCount: number
}


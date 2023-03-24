export type BoardDto = {
  board: TeamResultDto[];
};

export type TeamResultDto = {
  name: string;
  score: number;
  hex: string;
  categoryCount: CategoryCount;
  categoryResult: CategoryResultDto[];
}

export type CategoryResultDto = {
  name: string;
  status: string;
  okCount: number;
};

type CategoryCount = {
  [key: string]: Number
}


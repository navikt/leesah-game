export type BoardDto = {
  board: TeamResultDto[];
};

export type TeamResultDto = {
  name: string;
  score: number;
  hex: string;
  categoryResult: CategoryResultDto[];
};

export type CategoryResultDto = {
  name: string;
  status: string;
  okCount: number;
  maxCount: number;
};

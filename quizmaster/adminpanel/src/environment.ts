// @ts-ignore
const viteMode = import.meta.env.MODE;

export const Environment = {
    isDevelopment: (viteMode && viteMode === 'development') ?? false,
};

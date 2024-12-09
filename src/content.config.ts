import { defineCollection, z } from 'astro:content';

const oppsettCollection = defineCollection({
  schema: z.object({
    title: z.string(),
  })
});

const oppgaverCollection = defineCollection({
  schema: z.object({
    title: z.string(),
  })
});

export const collections = {
  oppsett: oppsettCollection,
  oppgaver: oppgaverCollection,
};

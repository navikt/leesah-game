import { defineCollection, z } from 'astro:content';

const oppgaverCollection = defineCollection({
  schema: z.object({
    title: z.string(),
  })
});

// These collection is for the English version of the content
const tasksCollection = defineCollection({
  schema: z.object({
    title: z.string(),
  })
});

export const collections = {
  oppgaver: oppgaverCollection,
  tasks: tasksCollection,
};

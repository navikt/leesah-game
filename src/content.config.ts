import { defineCollection, z } from 'astro:content';
import { glob } from 'astro/loaders';

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

// These collection is for the English version of the content
const tasksCollection = defineCollection({
  schema: z.object({
    title: z.string(),
  })
});

const setupCollection = defineCollection({
    schema: z.object({
        title: z.string(),
    })
});

const enCollections = defineCollection({
    schema: z.object({
        title: z.string(),
    })
});

export const collections = {
  oppsett: oppsettCollection,
  oppgaver: oppgaverCollection,
  tasks: tasksCollection,
  setup: setupCollection,
};

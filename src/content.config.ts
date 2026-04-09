import { defineCollection } from 'astro:content'
import { glob } from 'astro/loaders'
import { z } from 'zod'

const oppgaverCollection = defineCollection({
    loader: glob({ pattern: '**/*.md', base: './src/content/oppgaver' }),
    schema: z.object({
        title: z.string(),
    }),
})

// These collection is for the English version of the content
const tasksCollection = defineCollection({
    loader: glob({ pattern: '**/*.md', base: './src/content/tasks' }),
    schema: z.object({
        title: z.string(),
    }),
})

export const collections = {
    oppgaver: oppgaverCollection,
    tasks: tasksCollection,
}

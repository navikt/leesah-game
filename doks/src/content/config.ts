import { z, defineCollection } from 'astro:content'

const oppsettCollection = defineCollection({
    type: 'content', // v2.5.0 and later
    schema: z.object({
        title: z.string(),
    }),
})
const oppgaverCollection = defineCollection({
    type: 'content', // v2.5.0 and later
    schema: z.object({
        title: z.string(),
    }),
})

export const collections = {
    oppsett: oppsettCollection,
    oppgaver: oppgaverCollection,
}

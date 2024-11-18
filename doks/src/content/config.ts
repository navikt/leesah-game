import { z, defineCollection } from 'astro:content'

const collectionSchema = {
    type: 'content', // v2.5.0 and later
    schema: z.object({
        title: z.string(),
    }),
}

const oppsettCollection = defineCollection(collectionSchema)
const oppgaverCollection = defineCollection(collectionSchema)

export const collections = {
    oppsett: oppsettCollection,
    oppgaver: oppgaverCollection,
}

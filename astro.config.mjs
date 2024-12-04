// @ts-check
import { defineConfig } from 'astro/config';
import node from '@astrojs/node'

// https://astro.build/config
export default defineConfig({
    output: 'server',
    adapter: node({
        mode: "standalone",
    }),
    // build: {
    //     assetsPrefix: 'https://cdn.nav.no/leesah-quiz/leesah-produktside/_astro'
    // }
});

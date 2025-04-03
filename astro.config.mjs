// @ts-check
import { defineConfig } from 'astro/config';
import node from '@astrojs/node'

// https://astro.build/config
export default defineConfig({
    i18n: {
        locales: ["nb", "en"],
        defaultLocale: "nb"
    },
    output: 'server',
    adapter: node({
        mode: "standalone",
    }),
});

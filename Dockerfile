FROM node:25 AS build
WORKDIR /app

COPY astro.config.mjs tsconfig.json package.json ./
COPY public ./public
COPY src ./src

RUN yarn install
RUN yarn build

FROM cgr.dev/chainguard/node AS runtime
ENV NODE_ENV=production

WORKDIR /app

COPY --from=build /app/dist ./dist
COPY --from=build /app/node_modules/ ./node_modules/

ENV HOST=0.0.0.0

CMD ["./dist/server/entry.mjs"]

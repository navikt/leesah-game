FROM node:24 AS build
WORKDIR /app

COPY astro.config.mjs tsconfig.json package.json package-lock.json ./
COPY public ./public
COPY src ./src

RUN npm install
RUN npm run build

FROM cgr.dev/chainguard/node AS runtime
ENV NODE_ENV=production

WORKDIR /app

COPY --from=build /app/dist ./dist
COPY --from=build /app/node_modules/ ./node_modules/

ENV HOST=0.0.0.0
ENV PORT=4321
EXPOSE 4321

CMD ["./dist/server/entry.mjs"]

# 🏞️ Livet er en strøm av oppgaver

Leesah Game er et hendelsedrevet applikasjonsutviklingspill laget for å utfordre spilleren til å utvikle en applikasjon som kan håndtere et diverst utvalg av utfordrende hendelser som den mottar på eller utenfor NAIS-plattformen.

## Om repoet

Dette repoet er for dokumentasjon for både spillere og utvikling av Leesah Game.

## Våre repoer

Spillet Leesah Game består av en Quizmaster og et Leaderboard, med hver sin backend og frontend.

- [Quizmaster](https://github.com/navikt/leesah-quizmaster/)
- [Leaderboard](https://github.com/navikt/leesah-leaderboard/)

Vi har også en egen ekstern tjeneste som lar spillere hente ned sertifikat for å koble seg til Kafka.

- [Certs server](https://github.com/navikt/leesah-game-cert-server)

Biblioteker vi tilbyr:

- [Python](https://github.com/navikt/leesah-game-python)
- [Go](https://github.com/navikt/go-leesah)
- [JavaScript](https://github.com/navikt/leesah-game-javascript)

## Videreutvikling av dokumentasjon/Astro Starter Kit: Minimal

### 🚀 Project Structure

Inside of your Astro project, you'll see the following folders and files:

```text
/
├── public/
├── src/
│   └── pages/
│       └── index.astro
└── package.json
```

Astro looks for `.astro` or `.md` files in the `src/pages/` directory. Each page is exposed as a route based on its file name.

There's nothing special about `src/components/`, but that's where we like to put any Astro/React/Vue/Svelte/Preact components.

Any static assets, like images, can be placed in the `public/` directory.

### 🧞 Commands

All commands are run from the root of the project, from a terminal:

| Command                | Action                                           |
| :----------------------| :----------------------------------------------- |
| `yarn install`         | Installs dependencies                            |
| `yarn dev`             | Starts local dev server at `localhost:4321`      |
| `yarn build`           | Build your production site to `./dist/`          |
| `yarn preview`         | Preview your build locally, before deploying     |
| `yarn astro ...`       | Run CLI commands like `astro add`, `astro check` |
| `yarn astro -- --help` | Get help using the Astro CLI                     |

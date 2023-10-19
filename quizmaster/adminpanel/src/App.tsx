import React, { useState } from "react";
import { Backend, QuizStatsDto, restBackend } from "./api/restBackend";
import { Environment } from "./environment";
import { testBackend } from "./mock";
import "./index.scss";
import CategoryView from "./components/category-view/CategoryView";
import { useInterval } from "./poller";
import { useToggle } from "./hooks/useToggle";
import { HeaderCard } from "./components/header-card/HeaderCard";
import "@navikt/ds-css";

const App = () => {
  const backend: Backend = Environment.isDevelopment
    ? testBackend()
    : restBackend(false);
  const nullBoard: QuizStatsDto = { status: "INACTIVE", categories: [] };
  const [quizStats, setQuizStats] = useState(nullBoard);
  const [nais, setNais] = useToggle(false); //TODO legg til variabel for å lese om vi bruker NAIS eller ikke (f.eks ved å lese hvilket topic vi bruker?)

  useInterval(() => {
    const update = async () => {
      const response = backend.quizStats();
      setQuizStats(await response);
    };
    update().then((r) => console.log("Updating..."));
  }, 1000);

  const toggleCategories = () => {
    if (nais) {
      return quizStats.categories;
    } else
      return quizStats.categories.filter(
        (category) =>
          category.name !== "make-ingress" &&
          category.name !== "check-app-log" &&
          category.name !== "make-grafana-board" &&
          category.name !== "make-alert" &&
          category.name !== "setup-wonderwall",
      );
  };

  return (
    <div className="main-container">
      <HeaderCard
        quizStatsStatus={quizStats.status}
        backend={backend}
        setNais={setNais}
        nais={nais}
      />
      <CategoryView categories={toggleCategories()} backend={backend} />
    </div>
  );
};

export default App;

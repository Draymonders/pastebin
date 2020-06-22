import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import Home from './views/Home';
import Topics from './views/Topics';

export default function App() {
  return (
    <Router>
      <Switch>
        <Route path="/p/:topicId" >
          <Topics />
        </Route>
        <Route path="/">
          <Home />
        </Route>
      </Switch>
    </Router>
  );
};

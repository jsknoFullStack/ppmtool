import React, { Component } from "react";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import store from "./store";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import AddProject from "./components/project/AddProject";
import UpdateProject from "./components/project/UpdateProject";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/addProject" component={AddProject} />
            <Route
              exact
              path="/updateProject/:projectIdentifier"
              component={UpdateProject}
            />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;

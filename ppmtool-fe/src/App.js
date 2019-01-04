import React, { Component } from "react";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import store from "./store";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import AddProject from "./components/project/AddProject";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/projectBoard/ProjectBoard";
import AddProjectTask from "./components/projectBoard/projectTasks/AddProjectTask";
import UpdateProjectTask from "./components/projectBoard/projectTasks/UpdateProjectTask";
import Landing from "./components/layout/Landing";
import Register from "./components/userManagement/Register";
import Login from "./components/userManagement/Login";
import jwt_decode from "jwt-decode";
import setJwtToken from "./securityUtils/setJwtToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoute from "./securityUtils/SecureRoute";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJwtToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    // handle logout
    store.dispatch(logout());
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            {
              // Public Routes
            }

            <Route exact path="/" component={Landing} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />
            {
              // Private Routes
            }
            <Switch>
              <SecuredRoute exact path="/dashboard" component={Dashboard} />
              <SecuredRoute exact path="/addProject" component={AddProject} />
              <SecuredRoute
                exact
                path="/updateProject/:projectIdentifier"
                component={UpdateProject}
              />
              <SecuredRoute
                exact
                path="/projectBoard/:projectIdentifier"
                component={ProjectBoard}
              />
              <SecuredRoute
                exact
                path="/addProjectTask/:projectIdentifier"
                component={AddProjectTask}
              />
              <SecuredRoute
                exact
                path="/viewProjectTask/:projectIdentifier/:projectSequence"
                component={UpdateProjectTask}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;

import React, { Component } from "react";
import ProjectTask from "./projectTasks/ProjectTask";

class Backlog extends Component {
  createProjectTasks(tasks) {
    return tasks.map(task => <ProjectTask key={task.id} projectTask={task} />);
  }

  render() {
    const { projectTasks } = this.props;

    let todoItems = projectTasks.filter(task => task.status === "TO_DO");
    let inProgressItems = projectTasks.filter(
      task => task.status === "IN_PROGRESS"
    );
    let doneItems = projectTasks.filter(task => task.status === "DONE");

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {this.createProjectTasks(todoItems)}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {this.createProjectTasks(inProgressItems)}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {this.createProjectTasks(doneItems)}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;

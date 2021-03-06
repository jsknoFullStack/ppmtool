import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import { getBacklog } from "../../actions/backlogActions";
import PropTypes from "prop-types";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }
  componentDidMount() {
    const { projectIdentifier } = this.props.match.params;
    this.props.getBacklog(projectIdentifier);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { projectIdentifier } = this.props.match.params;
    const { projectTasks } = this.props;
    const { errors } = this.state;

    let BoardContent;

    const boardAlgorithm = (errors, projectTasks) => {
      if (projectTasks.length < 1) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else if (errors.projectIdentifier) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectIdentifier}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks on this board
            </div>
          );
        }
      } else {
        return <Backlog projectTasks={projectTasks} />;
      }
    };

    BoardContent = boardAlgorithm(errors, projectTasks);

    return (
      <div className="container">
        <Link to={`/dashboard`} className="btn btn-light">
          Back to Dashboard
        </Link>
        <Link
          to={`/addProjectTask/${projectIdentifier}`}
          className="btn btn-primary mb-3"
        >
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {BoardContent}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  projectTasks: PropTypes.array.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  projectTasks: state.backlog.projectTasks,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getBacklog }
)(ProjectBoard);

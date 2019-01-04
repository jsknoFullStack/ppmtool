import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJwtToken from "../securityUtils/setJwtToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

//export const login = (loginRequest, history) => async dispatch => {
export const login = loginRequest => async dispatch => {
  try {
    // Post => Login Request
    const res = await axios.post("/api/users/login", loginRequest);
    // Extract the token from the res.data
    const { token } = res.data;
    // Store the token in the localStorage
    localStorage.setItem("jwtToken", token);
    // Set our token in Headers ***
    setJwtToken(token);
    // Decode token on React
    const decoded = jwt_decode(token);
    // Dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
    //history.push("/dashboard");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJwtToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};

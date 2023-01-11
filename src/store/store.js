import reducers from "./reducers/reducer";
import { logger } from "redux-logger";
import { configureStore } from "@reduxjs/toolkit";

const env = process.env.NODE_ENV;
let store = null;

if (env === "production") {
  store = configureStore({
    reducer:reducers
  });
} else {
  store = configureStore({
    reducer:reducers,
    middleware:[logger]
  });
}

export default store;

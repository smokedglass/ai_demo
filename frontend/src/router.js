import React from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import App from "./App";
import Login from "./pages/login";
// import Admin from "./admin";
import Home from "./pages/home";
import PrivateRoute from "./components/PrivateRoute";
import { getToken } from "./utils/data";
import { message } from "antd";



export default class ERouter extends React.Component {
  pageGoBack() {
    let _this = this;
    _this.props.history.goback();
  }
  render() {
    return (
      <App>
        <BrowserRouter basename="/">
          <Switch>
            <Route path="/home" render={() => {
              if(getToken() == null) {
                message.error('您未登录')
                window.history.replaceState({ path: '/home' }, '', '/');
                location.reload();
              }else {
                return <Home />
              }
            }} />
            <Route path="/login" component={Login} />
            <Route exact path="/" component={Login} />
          </Switch>
        </BrowserRouter>
      </App>
    );
  }
}

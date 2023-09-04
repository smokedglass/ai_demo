import React from 'react';
import { Route, Redirect } from 'react-router-dom';
class PrivateRoute extends React.Component {
  render() {
    console.log(this.props)
    const { component, isAuthenticated, ...rest } = this.props;
    return (<Route {...rest} render={(props) => isAuthenticated ? (
          <Component {...props} />
        ) : (
          <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
        )
      }
    />
    );
  }
}

export default PrivateRoute;
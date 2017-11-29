import React, { Component } from 'react';
import {
  ViewPropTypes,
  requireNativeComponent
} from 'react-native';

class CoordinatorLayout extends Component {

  render() {
    return (
      <RNNCoordinatorLayout {...this.props} />
    );
  }
}

CoordinatorLayout.propTypes = {
  ...ViewPropTypes
};

const RNNCoordinatorLayout = requireNativeComponent('RNNCoordinatorLayout', CoordinatorLayout);

module.exports = CoordinatorLayout;

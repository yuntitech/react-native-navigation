import React, { Component } from 'react'
import {
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native'

class CoordinatorLayout extends Component {
  static propTypes = {
    ...ViewPropTypes,
  };

  render() {
    return (
      <RNNCoordinatorLayout {...this.props} />
    )
  }
}

const RNNCoordinatorLayout = requireNativeComponent('RNNCoordinatorLayout', CoordinatorLayout)

export default CoordinatorLayout
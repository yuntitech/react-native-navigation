import React, { Component } from 'react'
import {
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native'
import PropTypes, { array } from 'prop-types';

class FloatingActionButton extends Component {
  static propTypes = {
    icon: PropTypes.string,
    anchor: PropTypes.number,
    gravityTop: PropTypes.bool,
    gravityBottom: PropTypes.bool,
    gravityRight: PropTypes.bool,
    gravityLeft: PropTypes.bool,
    ...ViewPropTypes,
  };

  render() {
    return (
      <RNNFloatingActionButton {...this.props} />
    )
  }
}

const RNNFloatingActionButton = requireNativeComponent('RNNFloatingActionButton', FloatingActionButton)

export default FloatingActionButton
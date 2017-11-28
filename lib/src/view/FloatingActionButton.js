import React, { Component } from 'react'
import {
  ViewPropTypes,
  requireNativeComponent,
} from 'react-native'
import PropTypes, { array } from 'prop-types';

class FloatingActionButton extends Component {
  static propTypes = {
    gravityTop: PropTypes.bool,
    gravityBottom: PropTypes.bool,
    gravityRight: PropTypes.bool,
    gravityLeft: PropTypes.bool,
    hidden: PropTypes.bool,
    backgroundColor: PropTypes.string,
    icon: PropTypes.string,
    elevation: PropTypes.number,
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
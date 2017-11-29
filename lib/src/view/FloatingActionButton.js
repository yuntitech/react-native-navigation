import React, { Component } from 'react';
import {
  ViewPropTypes,
  requireNativeComponent
} from 'react-native';
import PropTypes from 'prop-types';

class FloatingActionButton extends Component {
  render() {
    return (
      <RNNFloatingActionButton {...this.props} />
    );
  }
}

FloatingActionButton.propTypes = {
  gravityTop: PropTypes.bool,
  gravityBottom: PropTypes.bool,
  gravityRight: PropTypes.bool,
  gravityLeft: PropTypes.bool,
  hidden: PropTypes.bool,
  backgroundColor: PropTypes.string,
  icon: PropTypes.string,
  elevation: PropTypes.number,
  ...ViewPropTypes
};

const RNNFloatingActionButton = requireNativeComponent('RNNFloatingActionButton', FloatingActionButton);

module.exports = FloatingActionButton;

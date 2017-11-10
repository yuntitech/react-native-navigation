import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

class MyLinearLayout extends React.Component {

  render() {
    return (
      <RCTMyLinearLayout {...this.props} />
    );
  }
}

MyLinearLayout.propTypes = {
  orientation: PropTypes.oneOf(['vertical', 'horizontal']),
  ...View.propTypes
}

var RCTMyLinearLayout = requireNativeComponent('RCTMyLinearLayout', MyLinearLayout);

module.exports = MyLinearLayout;
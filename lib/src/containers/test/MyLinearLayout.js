import PropTypes from 'prop-types';
import React, { Component } from 'react';
import _ from 'lodash';
import { requireNativeComponent, ScrollView } from 'react-native';

class MyNestedScrollView extends React.Component {

  render() {
    return (
      <RCTMyNestedScrollView {...this.props} />
    );
  }
}

MyNestedScrollView.propTypes = {
  ...ScrollView.propTypes
}

var RCTMyNestedScrollView = requireNativeComponent('RCTMyNestedScrollView', MyNestedScrollView);

module.exports = MyNestedScrollView;

/*
 {React.Children.toArray(this.props.children)}
*/
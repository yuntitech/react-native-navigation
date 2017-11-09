import PropTypes from 'prop-types';
import { requireNativeComponent, Image } from 'react-native';

var iface = {
  name: 'MyImageView',
  propTypes: {
    ...Image.propTypes
  },
};

module.exports = requireNativeComponent('RCTMyImageView', iface);
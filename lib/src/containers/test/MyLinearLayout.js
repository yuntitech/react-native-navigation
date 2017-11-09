import PropTypes from 'prop-types';
import { requireNativeComponent, View } from 'react-native';

var iface = {
  name: 'MyLinearLayout',
  propTypes: {
    orientation: PropTypes.oneOf(['vertical', 'horizontal']),
    ...View.propTypes
  },
};

module.exports = requireNativeComponent('RCTMyLinearLayout', iface);
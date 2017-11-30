const React = require('react');
const {
  ViewPropTypes,
  requireNativeComponent
} = require('react-native');

class CoordinatorLayout extends React.Component {

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

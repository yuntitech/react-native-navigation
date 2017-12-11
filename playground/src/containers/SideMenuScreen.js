const React = require('react');
const { Component } = require('react');

const { View, Text, Button } = require('react-native');

const Navigation = require('react-native-navigation');
const testIDs = require('../testIDs');

class SideMenuScreen extends Component {

  render() {
    return (
      <View style={styles.root} testID={this.props.testID}>
        <Text testID={testIDs.SIDE_BAR}>Side Bar</Text>
      </View>
    );
  }
}
module.exports = SideMenuScreen;

const styles = {
  root: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5fcff'
  }
};


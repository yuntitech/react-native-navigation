const React = require('react');
const { Component } = require('react');

const { View, Text, Button, TouchableOpacity} = require('react-native');

const Navigation = require('react-native-navigation');

import MyLinearLayout from '../../../lib/src/containers/test/MyLinearLayout'

const BUTTON_ONE = 'buttonOne';
const BUTTON_TWO = 'buttonTwo';
const BUTTON_LEFT = 'buttonLeft';

class OptionsScreen extends Component {

  static get navigationOptions() {
    return {
      title: 'Static Title',
      topBarTextColor: 'black',
      topBarTextFontSize: 16,
      topBarTextFontFamily: 'HelveticaNeue-Italic',
      rightButtons: [{
        id: BUTTON_ONE,
        testID: BUTTON_ONE,
        title: 'One',
        buttonColor: 'red'
      }],
      leftButtons: [{
        id: BUTTON_LEFT,
        testID: BUTTON_LEFT,
        title: 'Left',
        buttonColor: 'purple'
      }]
    };
  }

  constructor(props) {
    super(props);
    this.onClickDynamicOptions = this.onClickDynamicOptions.bind(this);
    this.onClickShowTopBar = this.onClickShowTopBar.bind(this);
    this.onClickHideTopBar = this.onClickHideTopBar.bind(this);
    this.onClickScrollViewScreen = this.onClickScrollViewScreen.bind(this);
  }

  render() {
    return (
      <MyLinearLayout style={styles.root} orientation='vertical'>
        <Text style={styles.h1}>{`Options Screen`}</Text>
        <Button style={styles.button} title="Dynamic Options" onPress={this.onClickDynamicOptions} />
        <Button style={styles.button} title="Show Top Bar" onPress={this.onClickShowTopBar} />
        <Button style={styles.button} title="Hide Top Bar" onPress={this.onClickHideTopBar} />
        <Button style={styles.button} title="scrollView Screen" onPress={this.onClickScrollViewScreen} />
        <Button style={styles.button} title="Show alert" onPress={this.onClickAlert} />
        <Text style={styles.footer}>{`this.props.containerId = ${this.props.containerId}`}</Text>
      </MyLinearLayout>
    );
  }

  onNavigationButtonPressed(id) {
    if (id === BUTTON_ONE) {
      Navigation.setOptions(this.props.containerId, {
        rightButtons: [{
          id: BUTTON_TWO,
          testID: BUTTON_TWO,
          title: 'Two',
          // icon: require('../../img/navicon_add.png'),
          // disableIconTint: true,
          // disabled: true
          buttonColor: 'green',
          buttonFontSize: 28,
          buttonFontWeight: '800'
        }]
      });
    } else if (id === BUTTON_TWO) {
      Navigation.setOptions(this.props.containerId, {
        rightButtons: [{
          id: BUTTON_ONE,
          testID: BUTTON_ONE,
          title: 'One',
          buttonColor: 'red'
        }]
      });
    } else if (id === BUTTON_LEFT) {
      Navigation.pop(this.props.containerId);
    }
  }

  onClickDynamicOptions() {
    Navigation.setOptions(this.props.containerId, {
      title: 'Dynamic Title',
      topBarTextColor: '#00FFFF',
      topBarButtonColor: 'red',
      topBarTextFontSize: 20,
      topBarTextFontFamily: 'HelveticaNeue-CondensedBold'
    });
  }

  onClickScrollViewScreen() {
    Navigation.push(this.props.containerId, {
      name: 'navigation.playground.ScrollViewScreen'
    });
  }

  onClickShowTopBar() {
    Navigation.setOptions(this.props.containerId, {
      topBarHidden: false,
      animateTopBarHide: true
    });
  }

  onClickHideTopBar() {
    Navigation.setOptions(this.props.containerId, {
      topBarHidden: true,
      animateTopBarHide: true
    });
  }

  onClickAlert() {
    Navigation.showOverlay('alert', {
      text: 'test!'
    });
  }
}

const styles = {
  root: {
    flex: 1,
    alignContent: 'center',
    alignItems: 'center'
  },
  button: {
    height: 50
  },
  h1: {
    fontSize: 24,
    textAlign: 'center',
    margin: 10
  },
  h2: {
    fontSize: 12,
    textAlign: 'center',
    margin: 10
  },
  footer: {
    fontSize: 10,
    color: '#888',
    marginTop: 10
  }
};

module.exports = OptionsScreen;

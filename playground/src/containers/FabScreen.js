const React = require('react');
const { Component } = require('react');

const { View, Text, Button, TouchableOpacity, FlatList } = require('react-native');

const Navigation = require('react-native-navigation');

import CoordinatorLayout from '../../../lib/src/view/CoordinatorLayout';
import FloatingActionButton from '../../../lib/src/view/FloatingActionButton';

class FabScreen extends Component {

  constructor(props) {
    super(props);
    this.onMoveClick = this.onMoveClick.bind(this);
    this.state = {
      right: true
    }
  }

  getData() {
    const result = [];
    for (index = 0; index < 140; index++) {
      result.push({ key: 'Item # ' + index });
    }
    return result;
  }

  render() {

    return (
      // <View style={{ flex: 1 }}>
        // <TouchableOpacity onPress={this.onMoveClick} >
          // <Text style={{ backgroundColor: 'red' }}>test</Text>
        // </TouchableOpacity>
        <CoordinatorLayout>
          <FlatList
            data={this.getData()}
            keyExtractor={(item, index) => { return "niga " + index }}
            renderItem={({ item }) => <Text>{item.key}</Text>}
          />
          <FloatingActionButton
            icon='md-navigate'
            elevation={20}
            gravityBottom
            gravityRight={this.state.right}
            gravityLeft={!this.state.right}
            backgroundColor='red'
          />
        </CoordinatorLayout>
      // </View>
    );
  }

  static get navigationOptions() {
    return {
      title: 'Fab test',
      topBarTextColor: 'black',
      topBarTextFontSize: 16,
      topBarTextFontFamily: 'HelveticaNeue-Italic'
    };
  }

  onMoveClick() {
    this.setState(previousState => {
      return { right: !previousState.right }
    });

  }
}

module.exports = FabScreen;
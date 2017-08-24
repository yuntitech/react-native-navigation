import React from 'react';
import {StyleSheet, ScrollView, Image, Alert} from 'react-native';
import Row from '../../components/Row';

export default class ImmersiveModeScreen extends React.Component {
  static navigatorStyle = {
    drawUnderStatusBar: true,
    drawUnderNavBar: true,
    navBarTransparent: true,
    navBarTranslucent: false,
    navBarButtonColor: 'black',
    navBarTextColor: 'black',
    statusBarTextColorScheme: 'dark',
    statusBarColor: 'transparent',
    secondaryStatusBarColor: '#002b4c',
    navBarBackgroundColor: '#003a66',
    secondaryNavBarBackgroundColor: 'transparent',
    navBarColorAnimationOffset: 250 // TODO: implement
  };

  constructor(props) {
    super(props);
  }

  alert = (text) => {
    Alert.alert('Alert!', text);
  };

  render() {
    return (
      <ScrollView style={styles.container}>
        <Image
          style={styles.image}
          source={require('../../../img/beach.jpg')}
        />
        <Row title={'Row 1'} onPress={this.alert}/>
        <Row title={'Row 2'} onPress={this.alert}/>
        <Row title={'Row 3'} onPress={this.alert}/>
        <Row title={'Row 4'} onPress={this.alert}/>
        <Row title={'Row 5'} onPress={this.alert}/>
        <Row title={'Row 6'} onPress={this.alert}/>
        <Row title={'Row 7'} onPress={this.alert}/>
        <Row title={'Row 8'} onPress={this.alert}/>
        <Row title={'Row 9'} onPress={this.alert}/>
        <Row title={'Row 10'} onPress={this.alert}/>
        <Row title={'Row 11'} onPress={this.alert}/>
        <Row title={'Row 12'} onPress={this.alert}/>
        <Row title={'Row 13'} onPress={this.alert}/>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white'
  },
  image: {
    height: 250,
    width: undefined
  }
});

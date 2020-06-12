const React = require('react');
const { ScrollView, StyleSheet, Text } = require('react-native');
const Navigation = require('../services/Navigation');

class PreferredScreenEdgesDeferringSystemGesturesScreen extends React.Component {
  static options() {
    return {
      preferredScreenEdgesDeferringSystemGestures: ['bottom']
    };
  }

  componentDidMount() {
    setTimeout(() => {
      Navigation.mergeOptions(this, {
        preferredScreenEdgesDeferringSystemGestures: ['none']
      });
    }, 5000);
  }

  render() {
    return (
      <ScrollView style={styles.root}>
        <Text style={styles.description}>
          The home indicator is dimmed and will be normal after 5 seconds.
        </Text>
      </ScrollView>
    );
  }
}

module.exports = PreferredScreenEdgesDeferringSystemGesturesScreen;

const styles = StyleSheet.create({
  root: {
    marginTop: 0
  },
  description: {
    fontSize: 15,
    letterSpacing: 0.2,
    lineHeight: 25,
    marginTop: 32,
    marginHorizontal: 24
  }
});

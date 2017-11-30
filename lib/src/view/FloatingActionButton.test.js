const React = require('react');
const renderer = require('react-test-renderer');

const FloatingActionButton = require('./FloatingActionButton');

describe('Floating action button', () => {
  it('renders', () => {
    const tree = renderer.create(<FloatingActionButton myProp="1" />);
    expect(tree.toJSON()).toEqual('dont know');
  });
});

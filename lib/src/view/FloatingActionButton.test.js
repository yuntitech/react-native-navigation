import { FloatingActionButton } from '../index';

const React = require('react');
const renderer = require('react-test-renderer');
const { AppRegistry } = require('react-native');

describe('Floating action button', () => {
  function Foo() {
    return <FloatingActionButton />;
  }

  beforeEach(() => {
    AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
  });

  it('renders', () => {
    renderer.render(<Foo />);
    const result = renderer.getRenderOutput();
    expect(result.props.children.type).toEqual(FloatingActionButton);
  });
});

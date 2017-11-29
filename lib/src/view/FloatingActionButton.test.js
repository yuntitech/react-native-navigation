import { FloatingActionButton } from '../index';

const React = require('react');
const { TestUtils } = require('react-native');

describe('Floating action button', () => {
  function Foo() {
    return <FloatingActionButton />;
  }

  it('renders', () => {
    const renderer = TestUtils.createRenderer();
    renderer.render(<Foo />);
    const result = renderer.getRenderOutput();
    expect(result.props.children.type).toEqual(FloatingActionButton);
  });
});

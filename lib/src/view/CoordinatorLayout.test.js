const React = require('react');
const renderer = require('react-test-renderer');

const FloatingActionButton = require('./FloatingActionButton');
const CoordinatorLayout = require('./CoordinatorLayout');

function uut() {
  return (
    <CoordinatorLayout>
      <FloatingActionButton />
    </CoordinatorLayout >
  );
}

describe('Coordinator Layout', () => {
  it('checks render correct type', () => {
    const tree = renderer.create(uut());
    expect(tree.toJSON().type).toEqual('RNNCoordinatorLayout');
  });

  it('checks render contains Fab as a child', () => {
    const tree = renderer.create(uut());
    expect(tree.toJSON().children[0].type).toEqual('RNNFloatingActionButton');
  });
});

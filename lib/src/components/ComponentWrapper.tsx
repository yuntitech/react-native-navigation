import * as React from 'react';
import { ComponentProvider,View } from 'react-native';
import merge from 'lodash/merge'
import { polyfill } from 'react-lifecycles-compat';
import hoistNonReactStatics = require('hoist-non-react-statics');

import { Store } from './Store';
import { ComponentEventsObserver } from '../events/ComponentEventsObserver';

interface HocState { componentId: string; allProps: { enableCustomLargeTitle?: boolean}; }
interface HocProps { componentId: string; }

export interface IWrappedComponent extends React.Component {
  setProps(newProps: Record<string, any>): void;
}

export class ComponentWrapper {
  wrap(
    componentName: string | number,
    OriginalComponentGenerator: ComponentProvider,
    store: Store,
    componentEventsObserver: ComponentEventsObserver,
    concreteComponentProvider: ComponentProvider = OriginalComponentGenerator,
    ReduxProvider?: any,
    reduxStore?: any,
    floatingView?: any
  ): React.ComponentClass<any> {
    const GeneratedComponentClass = OriginalComponentGenerator();
    const AudioPlayFloatingViewClass = floatingView ? floatingView() : null;
    class WrappedComponent extends React.Component<HocProps, HocState> {
      static getDerivedStateFromProps(nextProps: any, prevState: HocState) {
        return {
          allProps: merge({}, nextProps, store.getPropsForId(prevState.componentId))
        };
      }

      constructor(props: HocProps) {
        super(props);
        this._assertComponentId();
        this.state = {
          componentId: props.componentId,
          allProps: {}
        };
        store.setComponentInstance(props.componentId, this);
      }

      public setProps(newProps: any) {
        this.setState({ allProps: newProps });
      }

      componentWillUnmount() {
        store.clearComponent(this.state.componentId);
        componentEventsObserver.unmounted(this.state.componentId);
      }

      render() {
        return AudioPlayFloatingViewClass ? (
            <View style={{ flex: 1 }}>
              <GeneratedComponentClass
                  {...this.state.allProps}
                  componentId={this.state.componentId}
              />
              <AudioPlayFloatingViewClass
                  {...this.props}
                  screenID={this.props.componentId}
                  customTitleBar={this.state.allProps.enableCustomLargeTitle}
              />
            </View>
        ) : (
            <GeneratedComponentClass
                {...this.state.allProps}
                componentId={this.state.componentId}
            />
        );
      }

      private _assertComponentId() {
        if (!this.props.componentId) {
          throw new Error(`Component ${componentName} does not have a componentId!`);
        }
      }
    }

    polyfill(WrappedComponent);
    hoistNonReactStatics(WrappedComponent, concreteComponentProvider());
    return ReduxProvider ? this.wrapWithRedux(WrappedComponent, ReduxProvider, reduxStore) : WrappedComponent;
  }

  wrapWithRedux(WrappedComponent: React.ComponentClass<any>, ReduxProvider: any, reduxStore: any): React.ComponentClass<any> {
    class ReduxWrapper extends React.Component<any, any> {
      render() {
        return (
          <ReduxProvider store={reduxStore}>
            <WrappedComponent {...this.props} />
          </ReduxProvider>
        );
      }
    }
    hoistNonReactStatics(ReduxWrapper, WrappedComponent);
    return ReduxWrapper;
  }
}

"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Store = void 0;
class Store {
    componentsByName = {};
    propsById = {};
    componentsInstancesById = {};
    updateProps(componentId, props) {
        this.propsById[componentId] = props;
        const component = this.componentsInstancesById[componentId];
        if (component) {
            this.componentsInstancesById[componentId].setProps(props);
        }
    }
    getPropsForId(componentId) {
        return this.propsById[componentId] || {};
    }
    clearComponent(componentId) {
        delete this.propsById[componentId];
        delete this.componentsInstancesById[componentId];
    }
    setComponentClassForName(componentName, ComponentClass) {
        this.componentsByName[componentName.toString()] = ComponentClass;
    }
    getComponentClassForName(componentName) {
        return this.componentsByName[componentName.toString()];
    }
    setComponentInstance(id, component) {
        this.componentsInstancesById[id] = component;
    }
    getComponentInstance(id) {
        return this.componentsInstancesById[id];
    }
}
exports.Store = Store;

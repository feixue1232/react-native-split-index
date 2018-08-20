/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
'use strict'

import'react';
import'react-native';

export function getName() {
    return 'from common module! My name is ' + this._name;
}

export function setName(name) {
    this._name = name;
}
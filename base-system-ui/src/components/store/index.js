import React, {PropTypes} from 'react';
import store from 'store';
import expire from 'store/plugins/expire'

store.addPlugin(expire)
export default store;

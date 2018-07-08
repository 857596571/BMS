import * as system from '../../services/system';
import {message} from 'antd';

export default {
  namespace: 'sysRole',
  state: {
    list: [],
  },
  effects: {
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getRoleList, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: {
            list: data,
          },
        });
      }
    },
    *add({ payload, callback }, { call, put }) {
      const { data, ok } = yield call(system.saveRole, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: data,
        });
        if (typeof callback === 'function') {
          callback();
        }
        message.success('保存成功');
      } else {
        message.error('保存失败');
      }
    },
    *updateStates({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.updateRoleStates, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('状态更新成功');
      } else {
        message.error('状态更新失败');
      }
    },
    *saveMenuAuth({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.saveMenuAuth, payload);
      if (ok) {
        message.success('保存菜单权限成功');
      } else {
        message.error('保存菜单权限失败');
      }
    },
    *assignRole({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.assignRole, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('关联成功');
      } else {
        message.error('关联失败');
      }
    },
    *deleteRoleUser({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.deleteRoleUser, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },
    *delete({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.deleteRole, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },
  },

  reducers: {
    save(state, action) {
      const { list } = action.payload;
      return {
        ...state,
        list,
      };
    },
  },
};

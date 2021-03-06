import * as system from '@/services/upms';
import {message} from 'antd';

export default {
  namespace: 'sysMenu',
  state: {
    list: [],
  },
  effects: {
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getMenuList, payload);
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
      const { data, ok } = yield call(system.saveMenu, payload);
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
    *updateSorts({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.updateMenuSorts, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('更新排序成功');
      } else {
        message.error('更新排序失败');
      }
    },
    *updateStates({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.updateMenuStates, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('状态更新成功');
      } else {
        message.error('状态更新失败');
      }
    },
    *delete({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.deleteMenu, payload);
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

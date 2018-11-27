import * as system from '@/services/upms';
import {message} from 'antd';

export default {
  namespace: 'sysDict',
  state: {
    list: [],
    listParentCode: [],
  },
  effects: {
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getDictList, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: {
            list: data,
          },
        });
      }
    },
    *getListByParentCode({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getDictList, payload);
      if (ok) {
        yield put({
          type: 'saveParentCode',
          payload: {
            listParentCode: data,
          },
        });
      }
    },
    *add({ payload, callback }, { call, put }) {
      const { data, ok } = yield call(system.saveDict, payload);
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
      const { ok } = yield call(system.updateDictSorts, payload);
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
      const { ok } = yield call(system.updateDictStates, payload);
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
      const { ok } = yield call(system.deleteDict, payload);
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
    saveParentCode(state, action) {
      const { listParentCode } = action.payload;
      return {
        ...state,
        listParentCode,
      };
    },
  },
};

import * as system from '../../services/system';
import { message } from 'antd';

export default {
  namespace: 'sysOrg',
  state: {
    list: [],
    codeExists: true,
  },
  effects: {
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getOrgList, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: {
            list: data,
          },
        });
      }
    },
    *isCodeExists({ payload }, { call, put }) {
      const { data, ok } = yield call(system.isOrgCodeExists, payload);
      if (ok) {
        yield put({
          type: 'setCodeExists',
          payload: {
            codeExists: data,
          },
        });
      }
    },
    *add({ payload, callback }, { call, put }) {
      const { data, ok } = yield call(system.saveOrg, payload);
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
      const { ok } = yield call(system.updateOrgSorts, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('更新排序成功');
      } else {
        message.error('更新排序失败');
      }
    },
    *deleteById({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.deleteOrgById, payload);
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
    setCodeExists(state, action) {
      const { codeExists } = action.payload;
      return { ...state, codeExists };
    },
  },
};

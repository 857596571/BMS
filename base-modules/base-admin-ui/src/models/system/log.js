import * as system from '@/services/upms';
import {message} from 'antd';
import { pagination } from '@/config'

export default {
  namespace: 'sysLog',
  state: {
    list: [],
    pagination,
  },

  effects: {
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getLogList, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: {
            list: data.list,
            pagination: {
              current: Number(data.pageNum) || 1,
              pageSize: Number(data.pageSize) || 20,
              total: data.total,
            }
          },
        });
      }
    },
  },

  reducers: {
    save(state, action) {
      const { list, pagination } = action.payload
      return {
        ...state,
        list: list,
        pagination: {...pagination}
      };
    },
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser: action.payload.data,
      };
    },
    saveRoleUserList(state, action) {
      return {
        ...state,
        roleUserList: action.payload.roleUserList,
      };
    },
  },
};

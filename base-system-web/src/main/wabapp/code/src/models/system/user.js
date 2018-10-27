import * as system from '@/services/system';
import {message} from 'antd';
import { pagination } from '@/config'
import {getOrgList} from "@/services/system";

export default {
  namespace: 'sysUser',
  state: {
    list: [],
    pagination,
    roleUserList: [],
    currentUser: {},
  },

  effects: {
    *getLoginUser(_, { call, put }) {
      const response = yield call(system.getLoginUser);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getUserList, payload);
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
    *getUsersByRoleId({ payload }, { call, put }) {
      const { data, ok } = yield call(system.getUsersByRoleId, payload);
      if (ok) {
        yield put({
          type: 'saveRoleUserList',
          payload: {
            roleUserList: data,
          },
        });
      }
    },
    *add({ payload, callback }, { call, put }) {
      const { data, ok } = yield call(system.saveUser, payload);
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
      const { ok } = yield call(system.updateUserStates, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('状态更新成功');
      } else {
        message.error('状态更新失败');
      }
    },
    *resetPassword({ payload, callback }, { call, put }) {
      const resp = yield call(system.resetPassword, payload);
      if (resp.ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('密码修改成功');
      } else {
        message.error(`密码修改失败${resp.message}`);
      }
    },
    *delete({ payload, callback }, { call, put }) {
      const { ok } = yield call(system.deleteUser, payload);
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

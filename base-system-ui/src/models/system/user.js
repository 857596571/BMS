import { getLoginUser } from '../../services/system';

export default {
  namespace: 'sysUser',
  state: {
    list: [],
    currentUser: {},
  },

  effects: {
    *fetch(_, { call, put }) {
      // const response = yield call(queryUsers);
      // yield put({
      //   type: 'save',
      //   payload: response,
      // });
    },
    *getLoginUser(_, { call, put }) {
      const response = yield call(getLoginUser);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        list: action.payload,
      };
    },
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser: action.payload.data,
      };
    },
    changeNotifyCount(state, action) {
      return {
        ...state,
        currentUser: {
          ...state.currentUser,
          notifyCount: action.payload,
        },
      };
    },
  },
};

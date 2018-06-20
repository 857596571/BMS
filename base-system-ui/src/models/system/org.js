import { getOrgList, isCodeExists } from '../../services/system';

export default {
  namespace: 'sysOrg',
  state: {
    list: [],
    codeExists: true,
  },
  effects: {
    *getList({ payload }, { call, put }) {
      const {data, ok} = yield call(getOrgList, payload);
      if(ok) {
        yield put({
          type: 'save',
          payload: {
            list: data,
          },
        });
      }
    },
    *isCodeExists({ payload }, { call, put }) {
      const {data, ok} = yield call(isCodeExists, payload);
      if(ok) {
        yield put({
          type: 'setCodeExists',
          payload: {
            codeExists: data
          },
        });
      }
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield call(addRule, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
    },
    *remove({ payload, callback }, { call, put }) {
      const response = yield call(removeRule, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      if (callback) callback();
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

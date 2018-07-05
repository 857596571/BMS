export default {
  namespace: 'sysDict',
  state: {
    list: [],
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
  },
};

import { routerRedux } from 'dva/router';
import { userLogin } from '../../services/system';
import { setAuthority } from '../../utils/authority';
import { reloadAuthorized } from '../../utils/Authorized';

export default {
  namespace: 'sysLogin',
  state: {
    data: {},
  },
  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(userLogin, payload);
      // Login successfully
      if (response.ok) {
        yield put({
          type: 'changeLoginStatus',
          payload: {
            ...response,
            currentAuthority: 'admin',
          },
        });
        localStorage.setItem('Authorization', JSON.stringify(response.data));
        reloadAuthorized();
        yield put(routerRedux.push('/'));
      } else {
        yield put({
          type: 'changeLoginStatus',
          payload: {
            ...response,
          },
        });
      }
    },
    *logout(_, { put, select }) {
      try {
        // get location pathname
        const urlParams = new URL(window.location.href);
        const pathname = yield select(state => state.routing.location.pathname);
        // add the parameters in the url
        urlParams.searchParams.set('redirect', pathname);
        window.history.replaceState(null, 'login', urlParams.href);
      } finally {
        localStorage.removeItem('Authorization');
        yield put({
          type: 'changeLoginStatus',
          payload: {
            currentAuthority: 'guest',
          },
        });
        reloadAuthorized();
        yield put(routerRedux.push('/sys/login'));
      }
    },
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      setAuthority(payload.currentAuthority);
      return {
        ...state,
        data: payload,
      };
    },
  },
};

import { routerRedux } from 'dva/router';
import { stringify } from 'qs';
import { userLogin, userLogout } from '@/services/system';
import { setAuthority } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { reloadAuthorized } from '@/utils/Authorized';
import config from '@/config';

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
          type: 'sysUser/getLoginUser',
        });
        yield put({
          type: 'changeLoginStatus',
          payload: {
            ...response,
            currentAuthority: 'admin',
          },
        });
        localStorage.setItem('Authorization', JSON.stringify(response.data));
        reloadAuthorized();
        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        let { redirect } = params;
        if (redirect) {
          const redirectUrlParams = new URL(redirect);
          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);
            if (redirect.startsWith('/#')) {
              redirect = redirect.substr(2);
            }
          } else {
            window.location.href = redirect;
            return;
          }
        }
        const systemIndex = config.systemIndex;
        yield put(routerRedux.replace(systemIndex || '/'));
      } else {
        yield put({
          type: 'changeLoginStatus',
          payload: {
            ...response,
          },
        });
      }
    },
    *logout(_, { call, put }) {
      const response = yield call(userLogout);
      if (response.ok) {
        yield put({
          type: 'sysUser/saveCurrentUser',
          payload: {},
        });
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

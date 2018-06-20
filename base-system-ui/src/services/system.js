import { stringify } from 'qs';
import request from '../utils/request';

export async function userLogin(params) {
  return request('/auth/token',{
    method: 'POST',
    body: params,
  });
}

export async function getLoginUser() {
  return request('/sys/user/info');
}

export async function getOrgList(params) {
  return request('/sys/org/list', {
    body: params,
  });
}

export async function isCodeExists(params) {
  return request(`/sys/org/isCodeExists?${stringify(params)}`);
}

export async function getListByParentCode(params) {
  return request('/sys/dict/getListByParentCode', {
    body: params,
  });
}

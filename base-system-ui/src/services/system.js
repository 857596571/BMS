import { stringify } from 'qs';
import request from '../utils/request';

export async function userLogin(params) {
  return request('/auth/token', {
    method: 'POST',
    body: params,
  });
}

export async function getLoginUser() {
  return request('/sys/user/info');
}

export async function getOrgList(params) {
  return request(`/sys/org/list?${stringify(params)}`);
}

export async function isOrgCodeExists(params) {
  return request(`/sys/org/isCodeExists?${stringify(params)}`);
}

export async function saveOrg(params) {
  return request(`/sys/org/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateOrgSorts(params) {
  return request(`/sys/org/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteOrgById(id) {
  return request(`/sys/org/deleteOrgById/${id}`);
}

export async function getListByParentCode(params) {
  return request('/sys/dict/getListByParentCode', {
    body: params,
  });
}

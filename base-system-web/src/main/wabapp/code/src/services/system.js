import { stringify } from 'qs';
import request from '@/utils/request';

export async function userLogin(params) {
  return request('/api/auth/token', {
    method: 'POST',
    body: params,
  });
}

export async function userLogout(params) {
  return request('/api/auth/token');
}

export async function getLoginUser() {
  return request('/api/sys/user/info');
}

export async function getOrgList(params) {
  return request(`/api/sys/org/list?${stringify(params)}`);
}

export async function isOrgCodeExists(params) {
  return request(`/api/sys/org/isCodeExists?${stringify(params)}`);
}

export async function saveOrg(params) {
  return request(`/api/sys/org/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateOrgSorts(params) {
  return request(`/api/sys/org/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteOrg(params) {
  return request(`/api/sys/org/delete?${stringify(params)}`);
}

export async function getDictList(params) {
  return request(`/api/sys/dict/list?${stringify(params)}`);
}

export async function getListByParentCode(params) {
  return request(`/api/sys/dict/getListByParentCode?${stringify(params)}`);
}


export async function isDictCodeExists(params) {
  return request(`/api/sys/dict/isCodeExists?${stringify(params)}`);
}

export async function saveDict(params) {
  return request(`/api/sys/dict/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateDictSorts(params) {
  return request(`/api/sys/dict/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function updateDictStates(params) {
  return request(`/api/sys/dict/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteDict(params) {
  return request(`/api/sys/dict/delete?${stringify(params)}`);
}

export async function getMenuList(params) {
  return request(`/api/sys/menu/list?${stringify(params)}`);
}

export async function saveMenu(params) {
  return request(`/api/sys/menu/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateMenuSorts(params) {
  return request(`/api/sys/menu/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function updateMenuStates(params) {
  return request(`/api/sys/menu/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteMenu(params) {
  return request(`/api/sys/menu/delete?${stringify(params)}`);
}

export async function getRoleList(params) {
  return request(`/api/sys/role/list`);
}

export async function isRoleCodeExists(params) {
  return request(`/api/sys/role/isCodeExists?${stringify(params)}`);
}

export async function saveRole(params) {
  return request(`/api/sys/role/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateRoleStates(params) {
  return request(`/api/sys/role/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function saveMenuAuth(params) {
  return request(`/api/sys/role/saveMenuAuth`, {
    method: 'POST',
    body: params,
  });
}

export async function assignRole(params) {
  return request(`/api/sys/role/assignRole`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteRoleUser(params) {
  return request(`/api/sys/role/deleteRoleUser`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteRole(id) {
  return request(`/api/sys/role/delete/${id}`);
}

export async function getUserList(params) {
  return request(`/api/sys/user/list?${stringify(params)}`);
}

export async function getUsersByRoleId(params) {
  return request(`/api/sys/user/getUsersByRoleId?${stringify(params)}`);
}

export async function isUserExists(params) {
  return request(`/api/sys/user/isExists?${stringify(params)}`);
}

export async function saveUser(params) {
  return request(`/api/sys/user/save`, {
    method: 'POST',
    body: params,
  });
}

export async function resetPassword(params) {
  return request(`/api/sys/user/resetPassword`, {
    method: 'POST',
    body: params,
  });
}

export async function updateUserStates(params) {
  return request(`/api/sys/user/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteUser(id) {
  return request(`/api/sys/user/delete/${id}`);
}

export async function getLogList(params) {
  return request(`/api/sys/log/list?${stringify(params)}`);
}

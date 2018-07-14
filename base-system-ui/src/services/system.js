import { stringify } from 'qs';
import request from '../utils/request';

export async function userLogin(params) {
  return request('/auth/token', {
    method: 'POST',
    body: params,
  });
}

export async function userLogout(params) {
  return request('/auth/token', {
    method: 'DELETE',
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

export async function deleteOrg(params) {
  return request(`/sys/org/delete?${stringify(params)}`);
}

export async function getDictList(params) {
  return request(`/sys/dict/list?${stringify(params)}`);
}

export async function isDictCodeExists(params) {
  return request(`/sys/dict/isCodeExists?${stringify(params)}`);
}

export async function saveDict(params) {
  return request(`/sys/dict/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateDictSorts(params) {
  return request(`/sys/dict/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function updateDictStates(params) {
  return request(`/sys/dict/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteDict(params) {
  return request(`/sys/dict/delete?${stringify(params)}`);
}

export async function getMenuList(params) {
  return request(`/sys/menu/list`);
}

export async function saveMenu(params) {
  return request(`/sys/menu/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateMenuSorts(params) {
  return request(`/sys/menu/updateSorts`, {
    method: 'POST',
    body: params,
  });
}

export async function updateMenuStates(params) {
  return request(`/sys/menu/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteMenu(params) {
  return request(`/sys/menu/delete?${stringify(params)}`);
}

export async function getRoleList(params) {
  return request(`/sys/role/list`);
}

export async function isRoleCodeExists(params) {
  return request(`/sys/role/isCodeExists?${stringify(params)}`);
}

export async function saveRole(params) {
  return request(`/sys/role/save`, {
    method: 'POST',
    body: params,
  });
}

export async function updateRoleStates(params) {
  return request(`/sys/role/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function saveMenuAuth(params) {
  return request(`/sys/role/saveMenuAuth`, {
    method: 'POST',
    body: params,
  });
}

export async function assignRole(params) {
  return request(`/sys/role/assignRole`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteRoleUser(params) {
  return request(`/sys/role/deleteRoleUser`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteRole(id) {
  return request(`/sys/role/delete/${id}`);
}

export async function getUserList(params) {
  return request(`/sys/user/list?${stringify(params)}`);
}

export async function getUsersByRoleId(params) {
  return request(`/sys/user/getUsersByRoleId?${stringify(params)}`);
}

export async function isUserExists(params) {
  return request(`/sys/user/isExists?${stringify(params)}`);
}

export async function saveUser(params) {
  return request(`/sys/user/save`, {
    method: 'POST',
    body: params,
  });
}

export async function resetPassword(params) {
  return request(`/sys/user/resetPassword`, {
    method: 'POST',
    body: params,
  });
}

export async function updateUserStates(params) {
  return request(`/sys/user/updateStates`, {
    method: 'POST',
    body: params,
  });
}

export async function deleteUser(id) {
  return request(`/sys/user/delete/${id}`);
}

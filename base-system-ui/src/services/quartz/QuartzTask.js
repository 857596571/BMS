import {stringify} from 'qs';
import request from '../../utils/request';

/**
 * 列表查询
 */
export async function getList(params) {
  return request(`/quartzTask/list?${stringify(params)}`);
}

/**
 * 保存
 */
export async function add(params) {
  return request('/quartzTask/save', {
    method: 'POST',
    body: params,
  });
}

/**
 * 删除
 */
export async function deleteById(id) {
  return request(`/quartzTask/delete/${id}`);
}

/**
 * 状态变更
 */
export async function updateState(params) {
  return request(`/quartzTask/updateState`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 运行一次
 */
export async function runOne(params) {
  return request(`/quartzTask/runOne`, {
    method: 'POST',
    body: params,
  });
}

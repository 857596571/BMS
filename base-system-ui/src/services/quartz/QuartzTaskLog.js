import {stringify} from 'qs';
import request from '../../utils/request';

/**
 * 列表查询
 */
export async function getList(params) {
  return request(`/quartzTaskLog/list?${stringify(params)}`);
}

/**
 * 保存
 */
export async function add(params) {
  return request('/quartzTaskLog/save', {
    method: 'POST',
    body: params,
  });
}

/**
 * 删除
 */
export async function deleteById(id) {
  return request(`/quartzTaskLog/delete/${id}`);
}
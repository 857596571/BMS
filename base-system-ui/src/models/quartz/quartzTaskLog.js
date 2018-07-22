import * as quartzTaskLog from '../../services/quartz/QuartzTaskLog';
import {message} from 'antd';
import { pagination } from '../../utils/config'

/**
 * 任务调度运行日志
 */
export default {
  namespace: 'quartzTaskLog',
  state: {
    list: [],
    pagination,
  },
  effects: {
    /**
     * 列表查询
     */
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(quartzTaskLog.getList, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: {
            list: data.list,
            pagination: {
              current: Number(data.pageNum) || 1,
              pageSize: Number(data.pageSize) || 20,
              total: data.total,
            }
          },
        });
      }
    },
    /**
     *  保存
     */
    *add({ payload, callback }, { call, put }) {
      const { data, ok } = yield call(quartzTaskLog.add, payload);
      if (ok) {
        yield put({
          type: 'save',
          payload: data,
        });
        if (typeof callback === 'function') {
          callback();
        }
        message.success('保存成功');
      } else {
        message.error('保存失败');
      }
    },
    /**
     * 删除
     */
    *deleteById({ id, callback }, { call, put }) {
      const { ok } = yield call(quartzTaskLog.deleteById, id);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },
  },

  reducers: {
    save(state, action) {
      const { list, pagination } = action.payload;
      return {
        ...state,
        list: list,
        pagination: {...pagination},
      };
    },
  },
};

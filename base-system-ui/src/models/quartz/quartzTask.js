import * as quartzTask from '../../services/quartz/QuartzTask';
import {message} from 'antd';
import { pagination } from '../../utils/config'

/**
 * 任务调度资源
 */
export default {
  namespace: 'quartzTask',
  state: {
    list: [],
    pagination,
  },
  effects: {
    /**
     * 列表查询
     */
    *getList({ payload }, { call, put }) {
      const { data, ok } = yield call(quartzTask.getList, payload);
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
      const { data, ok } = yield call(quartzTask.add, payload);
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
      const { ok } = yield call(quartzTask.deleteById, id);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('删除成功');
      } else {
        message.error('删除失败');
      }
    },
    *updateState({ payload, callback }, { call, put }) {
      const { ok } = yield call(quartzTask.updateState, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('操作成功');
      } else {
        message.error('操作失败');
      }
    },
    *runOne({ payload, callback }, { call, put }) {
      const { ok } = yield call(quartzTask.runOne, payload);
      if (ok) {
        if (typeof callback === 'function') {
          callback();
        }
        message.success('操作成功');
      } else {
        message.error('操作失败');
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

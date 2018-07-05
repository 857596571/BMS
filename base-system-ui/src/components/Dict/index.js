import React, {Component} from 'react';
import {Form, Select} from 'antd';
import {stringify} from 'qs';
import request from '../../utils/request';
import store from '../store'

const FormItem = Form.Item;
const Option = Select.Option;

//缓存前缀
const storeKey = "dictStore-";
//缓存实效
const exp = new Date().getTime() + (1000 * 60 * 60 * 24);

const getDictStore = (code) => {
  const dictMap = store.get(storeKey + code);
  if(!dictMap) {
    return [];
  } else {
    return dictMap;
  }
}
const getDictLabel = (code, value) => {
  const dictMap = getDictStore(code);
  const resVal = dictMap.filter(item => item.value == value);
  if(resVal.length > 0) {
    return resVal[0].label;
  }
  return null;
}

export default class Dict extends Component {
  constructor(props) {
    super(props);
    const {code, value, info} = props || {};
    this.state = {
      data: [],
      code: code || 0,
      value: value || '',
      info: info || false,
    };
  }
  componentDidMount() {
    const dictMap = getDictStore(this.props.code);
    if(!dictMap.length > 0) {
      const params = {code: this.props.code};
      request(`/sys/dict/getListByParentCode?${stringify(params)}`).then(data => {
        store.set(storeKey + this.props.code, data.data, exp);
        this.setState({data: data.data})
      });
    } else {
      this.setState({data: dictMap})
    }
  }
  componentWillReceiveProps(nextProps) {
    // Should be a controlled component.
    if ('value' in nextProps) {
      const value = nextProps.value;
      this.setState({ value });
    }
  }
  onChange = (value) => {
    if (!('value' in this.props)) {
      this.setState({ value });
    }
    this.triggerChange({ value });
  }
  triggerChange = (changedValue) => {
    // Should provide an event to pass value to Form.
    const onChange = this.props.onChange;
    if (onChange) {
      onChange(changedValue);
    }
  }
  render() {
    const { info, code, codeValue } = this.props;
    const params = Object.assign({}, {style: {width: '100%'}} , this.props)
    const state = this.state;
    return (
      <span>
        {info ? getDictLabel(code, codeValue) :
        <Select
          onChange={this.onChange}
          {...params}
        >
          {state.data.map(item => <Option value={item.code} key={item.code}>{item.label}</Option>)}
        </Select>}
      </span>
    );
  }
}

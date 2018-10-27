import React, {Component} from 'react';
import { Select, Radio } from 'antd';
import {stringify} from 'qs';
import request from '@/utils/request';

const RadioGroup = Radio.Group;
const Option = Select.Option;

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
    const params = {code: this.props.code};
    request(`/sys/dict/getListByParentCode?${stringify(params)}`).then(data => {
      this.setState({data: data.data})
    });
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
    const { excludeCodes = [], radio, query} = this.props;
    const params = Object.assign({}, {style: {width: '100%'}} , this.props)
    const state = this.state;

    return (
      <span>
      {
        radio ?
          <RadioGroup {...params}>
            {query && <Radio value={''}>全部</Radio>}
            {state.data.filter(item => excludeCodes.filter(code => item.code === code).length === 0 )
              .map(item => <Radio value={item.code} key={item.code}>{item.label}</Radio>)}
          </RadioGroup>
          :
          <Select
            onChange={this.onChange}
            {...params}
          >
            {state.data.filter(item => excludeCodes.filter(code => item.code === code).length === 0 )
              .map(item => <Option value={item.code} key={item.code}>{item.label}</Option>)}
          </Select>
      }
      </span>
    );
  }
}

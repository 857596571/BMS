import * as React from 'react';

export interface IDictProps {
  code?: React.ReactNode | string;
  type?: React.ReactNode | string;
  value?: React.ReactNode | string;
  info?: React.ReactNode | string;
  excludeCodes?: React.ReactNode | any;
  query?: React.ReactNode | boolean;
  radio?: React.ReactNode | boolean;
  style?: React.CSSProperties;
}

export default class Dict extends React.Component<IDictProps, any> {}

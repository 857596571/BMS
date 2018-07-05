import * as React from 'react';

export interface IDictProps {
  code?: React.ReactNode | string;
  type?: React.ReactNode | string;
  value?: React.ReactNode | string;
  info?: React.ReactNode | string;
  style?: React.CSSProperties;
}

export default class Dict extends React.Component<IDictProps, any> {}

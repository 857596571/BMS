module.exports = {
  //系统配置
  name: '基础框架',
  menuName: '基础框架',
  copyrightText: `${new Date().getFullYear()} 基础框架`,
  logoSrc: require('./assets/logo.svg'),
  // baseURL: 'http://xmh.s1.natapp.cc/wisdom',
  // baseURL: 'http://127.0.0.1:8080/wisdom',
  baseURL: 'http://47.95.252.122:8080/wisdom',
  // baseURL: './',
  systemIndex: '/home',
  //umi框架配置
  umiConfig: {
    history: 'hash',
    base: '/wisdom/',
    publicPath: './',
    outputPath: '../',
  },
  //公共分页信息
  pagination: {
    showSizeChanger: true,
    showQuickJumper: true,
    showTotal: (total, range) => `显示第 ${range[0]} 到第 ${range[1]} 条记录，总共 ${total} 条记录`,
    current: 1,
    total: 0,
    pageSize: 20,
    defaultPageSize: 20,
  },
};

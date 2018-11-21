export default [
  // user
  {
    path: '/sys',
    component: '../layouts/UserLayout',
    routes: [
      {path: '/sys', redirect: '/sys/login'},
      {path: '/sys/login', component: './System/Login'},
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/LoadingPage',
    Routes: ['src/pages/Authorized'],
    authority: ['admin', 'user'],
    routes: [
      //定时任务管理
      // {
      //   path: '/quartz',
      //   routes: [
      //     {
      //       path: '/quartz/quartzTaskLog',
      //       component: './quartz/QuartzTaskLog',
      //     },
      //     {
      //       path: '/quartz/quartzTask',
      //       component: './quartz/QuartzTask',
      //     },
      //   ],
      // },

      //系统管理
      {
        path: '/system',
        routes: [
          {
            path: '/system/org',
            component: './System/OrgList',
          },
          {
            path: '/system/role',
            component: './System/RoleList',
          },
          {
            path: '/system/user',
            component: './System/UserList',
          },
          {
            path: '/system/dict',
            component: './System/DictList',
          },
          {
            path: '/system/menu',
            component: './System/MenuList',
          },
          {
            path: '/system/log',
            component: './System/LogList',
          },
        ],
      },

      {
        path: '/exception',
        routes: [
          {
            path: '/exception/403',
            component: './Exception/403',
          },
          {
            path: '/exception/404',
            component: './Exception/404',
          },
          {
            path: '/exception/500',
            component: './Exception/500',
          },
        ],
      },
    ],
  },
];

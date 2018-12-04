import React from 'react';
import { Router as DefaultRouter, Route, Switch } from 'react-router-dom';
import dynamic from 'umi/dynamic';
import renderRoutes from 'umi/_renderRoutes';
import RendererWrapper0 from '/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/pages/.umi-production/LocaleWrapper.jsx'
import _dvaDynamic from 'dva/dynamic'

let Router = require('dva/router').routerRedux.ConnectedRouter;

let routes = [
  {
    "path": "/sys",
    "component": _dvaDynamic({
  
  component: () => import('../../layouts/UserLayout'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
    "routes": [
      {
        "path": "/sys",
        "redirect": "/sys/login",
        "exact": true
      },
      {
        "path": "/sys/login",
        "component": _dvaDynamic({
  
  component: () => import('../System/Login'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
        "exact": true
      }
    ]
  },
  {
    "path": "/",
    "component": _dvaDynamic({
  
  component: () => import('../../layouts/LoadingPage'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
    "Routes": [require('../Authorized').default],
    "authority": [
      "admin",
      "user"
    ],
    "routes": [
      {
        "path": "/system",
        "routes": [
          {
            "path": "/system/org",
            "component": _dvaDynamic({
  
  component: () => import('../System/OrgList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/system/role",
            "component": _dvaDynamic({
  
  component: () => import('../System/RoleList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/system/user",
            "component": _dvaDynamic({
  
  component: () => import('../System/UserList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/system/dict",
            "component": _dvaDynamic({
  
  component: () => import('../System/DictList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/system/menu",
            "component": _dvaDynamic({
  
  component: () => import('../System/MenuList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/system/log",
            "component": _dvaDynamic({
  
  component: () => import('../System/LogList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          }
        ]
      },
      {
        "path": "/exception",
        "routes": [
          {
            "path": "/exception/403",
            "component": _dvaDynamic({
  
  component: () => import('../System/OrgList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/exception/role",
            "component": _dvaDynamic({
  
  component: () => import('../System/RoleList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/exception/user",
            "component": _dvaDynamic({
  
  component: () => import('../System/UserList'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          }
        ]
      },
      {
        "path": "/exception",
        "routes": [
          {
            "path": "/exception/403",
            "component": _dvaDynamic({
  
  component: () => import('../Exception/403'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/exception/404",
            "component": _dvaDynamic({
  
  component: () => import('../Exception/404'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          },
          {
            "path": "/exception/500",
            "component": _dvaDynamic({
  
  component: () => import('../Exception/500'),
  LoadingComponent: require('/Users/xiongmenghui/workspace/项目资料/base/base-system-web/src/main/wabapp/code/src/components/PageLoading/index').default,
}),
            "exact": true
          }
        ]
      }
    ]
  }
];
window.g_plugins.applyForEach('patchRoutes', { initialValue: routes });

export default function() {
  return (
<RendererWrapper0>
          <Router history={window.g_history}>
      { renderRoutes(routes, {}) }
    </Router>
        </RendererWrapper0>
  );
}

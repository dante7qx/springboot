## 单点登录

### 一. 什么是SSO

举个场景，假设我们的系统被切割为N个部分：商城、论坛、直播、社交…… 如果用户每访问一个模块都要登录一次，那么用户将会疯掉， 为了优化用户体验，我们急需一套机制将这N个系统的认证授权互通共享，让用户在一个系统登录之后，便可以畅通无阻的访问其它所有系统。

单点登录——就是为了解决这个问题而生！

简而言之，单点登录可以做到：**`在多个互相信任的系统中，用户只需登录一次，就可以访问所有系统。`**
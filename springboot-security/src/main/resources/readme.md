- WebSecurityConfigurerAdapter在configure(HttpSecurity http)方法中提供了一个默认的配置

```    java
protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.and()
		.httpBasic();
}
``` 

- 相当于，java配置使用and()方法相当于XML标签的关闭

```    xml
<http>
	<intercept-url pattern="/**" access="authenticated"/>
	<form-login />
	<http-basic />
</http>
``` 

- 并发session

max-sessions是设置单个用户最大并行会话数；error-if-maximum-exceeded是配置当用户登录数达到最大时是否报错，设置为true时会报错且后登录的会话不能登录，默认为false不报错且将前一会话置为失效。 
配置完后使用不同浏览器登录系统，就可以看到同一用户后来的会话不能登录或将已登录会话踢掉。

如果spring security的一段<http/>中使用了自定义过滤器<custom-filter/>（特别是FORM_LOGIN_FILTER），
或者配置了AuthenticationEntryPoint，或者使用了自定义的UserDetails、AccessDecisionManager、AbstractSecurityInterceptor、
FilterInvocationSecurityMetadataSource、UsernamePasswordAuthenticationFilter等，上面的简单配置可能就不会生效了。

需要自行配置ConcurrentSessionFilter、ConcurrentSessionControlStrategy和SessionRegistry


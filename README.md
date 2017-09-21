# MVPDemo

这个项目的重点是MVP和dagger2使用和与后台交互的安全。

android前端：

1、MVPDemo是一个MVP架构，dagger2对象依赖注入集合MVP各个组件。页面效果有点类似微信。

2、数据库后台接口使用了retrofit2，json数据格式交互3、自定义ConverterFactory和Interceptor实现加解密。

3、rxjava2，rxlifecycle2，rxbinding2。

4、autolayout布局屏幕适配，标准是720*1080。

5、sqlite的orm框架greenDao3库。

6、socket.io的android和nodejs实现

7、PDF文档库android-pdf-viewer（由于pdfcore的原因使打包出来的app增加了14M左右）

8、事件总线eventbus3，butterknife控件注入

9、bugly的奔溃信息跟踪和热修复管理。建议奔溃信息在开发和测试时不开发，只收集发布后的奔溃信息。

10、jsoup解析csdn博客获取数据。

11、腾讯X5内核浏览器服务TBS代替原生的webview。

12、页面路由Arouter.


与后台交互安全:

后台使用了leancloud和nodejs搭建。

接口签名认证机制，服务器响应内容、签名信息和敏感信息由AES加密传输，防止参数和信息被中间人获取和修改。

AES和RSA加密，防止敏感信息被中途泄漏。AES的密钥是由前端每次启动时产生，再使用服务器的RSA公钥加密传给服务端。

每个客户端都要有APPId和AppScrect，防止伪基站、伪终端的攻击，确保请求来源是合法性的，是自己授权的客户端。

android端的APPId和AppScrect放到.so文件里保护，防止被反编译泄漏。

添加时间戳，作为接口签名的一部分，服务器只允许一定时间范围内的请求，防止重放攻击。

为了保证每个请求的唯一性，可以添加每个请求的唯一性。

# MVPDemo

这个项目的重点有三个：一是：MVP、dagger2和rxjava使用，二是：与后台交互的安全机制。三是: socket

（一）android前端：

1、MVPDemo是一个MVP架构，dagger2对象依赖注入来集合MVP各个组件。页面效果有点类似微信。

2、数据库后台接口交互使用了retrofit2，数据格式为json。

3、自定义retrofit2的ConverterFactory和Interceptor实现统一加解密交互的数据，面向切面。

4、rxjava2，rxlifecycle2，rxbinding2等Rx系列简单使用。

4、autolayout布局屏幕适配，标准是720*1080。在手机和平板等不同设备上显示效果差不多一致。

5、sqlite的orm框架是greenDao3库。

6、socket.io的android和nodejs实现（ NodeTestDemo：https://github.com/zhangsunyucong/NodeTestDemo ）。

7、PDF文档库android-pdf-viewer（由于pdfcore的原因使打包出来的apk增加了14M左右）。

8、事件总线eventbus3，butterknife控件注入

9、bugly的奔溃信息跟踪和热修复管理。建议奔溃信息在开发和测试时不开放，只在发布后收集奔溃信息。

10、使用jsoup解析csdn网站的html页面获取数据。

11、使用腾讯X5内核浏览器服务TBS代替原生的webview。

12、页面路由Arouter.

13、app端出现异常，在杀死应用前，启动异常页面并允许用户重启。

14、Cmake的使用


（二）与后台交互安全机制：

1、后台服务器使用了leancloud和nodejs搭建。https://github.com/zhangsunyucong/NodeTestDemo 

2、接口签名认证保护机制。在参数传输给服务器前，将接口的参数按照一定规则签名后再加密传输，防止参数和信息被中间人获取和修改。

3、AES和RSA加密，防止敏感信息被中途泄漏。AES的密钥是由前端每次启动时动态产生，再使用服务器的RSA公钥加密传给服务端。服务器使用私钥解密。

4、每个客户端都要求有APPId和AppScrect，用于参与接口签名，防止伪基站、伪终端的攻击，以此来确保请求方是合法性的，是自己授权过的客户端。

5、android端的APPId和AppScrect或者第三方开发平台等敏感数据，放到.so文件里保护，防止被反编译泄漏。

6、添加时间戳，作为接口签名的一部分，服务器只允许一定时间范围内的请求，防止重放攻击。

7、为了保证每个请求的唯一性，可以添加每个请求的唯一性。

（三）其它
MPChart，地图服务，图片压缩

# react-native-split-index
基于RN0.5的bundle拆分机制，可以支持拆分的bundle以字符串索引的形式替换掉rn默认的int值机型，使拆分后的bundle不存在值依赖，避免选成升级困扰~

下载后，可使用AndroidStudio 或 react-native run-android 直接启动查看实例,在Android平台上ReactContext的延迟加载和各bundle的主动加载。JS拆包是通用的，demo中不包括oc代码部分加载bundle，oc加载bundle应该更简单~

如果希望移植到自己的项目里，或改js代码试下效果，你需要做以下步骤：

1.将/source目录下的Bundler.js替换以下文件

node_modules/metro-bundler/src/Bundler/Bundler.js

将/source目录下的index.js替换以下文件

node_modules/metro-bundler/src/Resolver/index.js

注：以下解释为啥改成字符串的原因，如果不懂的话，请观注我的blog：https://blog.csdn.net/yeputi1015，实在不懂，反正替换以下就对了~~~

这两个文件是使bundle中的moduleId,默认打包分配为int值替换为包名+文件名的字符串形式，为了使拆包之后升级方便而需要修改的。

因为默认情况下，common.bundle + SubA.bundle + SubB.bundle.我们根据业务拆分了这三个包。

按默认打包规则，common.bundle中的部分比如为(0-138), 业务A中的js文件分配的（138-200），业务B中的js文件分配为(201-300)

假如这时，我们需要为common基础模块中增加一个js文件，那common.bundle中分配的moduleId的范围必将为0-139，那业务A和业务B的bundle包也需要升级，这样存在依赖关系。。我们更希望升级任意一个bundle文件，并不会对其它的未升级bundle文件造成影响~

2.这时你可修改js文件进行偿试，包括在common中增加js文件等~

3.在项目目录下执行./buildBundle.sh 为android平台生成bundle文件，如果有权限问题，请使用chmod 777 ./buildBundle.sh,先为sh文件授权。

4.执行AndroidStudio或react-native run-android执行

更详细的步骤请关注blog : https://blog.csdn.net/yeputi1015/article/details/81476369
有任何疑问可发邮件: feixuedefeng@163.com

如果能帮到您， 求star ~~~~


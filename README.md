## Community
数据库结构：  comment(帖子内容);
             discuss_post(帖子表);
             login_ticket(登录凭证表);
             user(用户表)。
             
###创建实体类
User类.
###创建对应的接口
UserMapper接口,定义user的增删改的方法。
###mybatis映射
写xml文件映射到数据库，对数据库进行操作。user_mapper.xml.
```xml
<!--对多列属性进行操作的时候，可以这样声名一个sql-->
<sql id="selectFields">
        id,username,password,salt,email,type,status,activation_code,header_url,create_time
 </sql>
<!--这样去使用它-->
<include refid="insertFields"></include>
```
注：在测试类下测试对数据库的操作是否正确。
###Controller
这里先写个HomeController,显示网页页面/index.html.***这里重点知识对帖子做分页***
为了更好的分页，我们对分页做个封装，创建个Page类，创建discussPost的实体类。
写他的对应mapper接口定义selectDiscussPosts;selectDiscussPostRows..等方法
page类定义了当前页码current=1;网页帖子显示上限limit=10;总共有多少数据rows,这里利用rows%limit可以得出总共有多少页
还定义了getoffset()方法 用来获取当前页面帖子的首行是多少。
下面写Controller层，getIndexPage(Model model, Page page)方法，这里方法调用之前，mvc自动实例化model,page，并将page注入model，写业务逻辑代码
最终实现分页功能。
###创建Service
UserService:

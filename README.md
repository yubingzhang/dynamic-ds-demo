**<font color='red' size=4>读写分离实现方式上主要分为两种：<br>一种是通过中间件，中间件方式实现比较复杂,技术要求比较高<br>
一种是代码实现，改方式比较简单，只是在不同的项目都要重复实现<br>下面介绍代码实现读写分离的几种实现方式，项目是以spring boot 2.1.3版本实现， 不同的版本，配置的属性会有细微的差别！</font>**


# 项目功能描述 #

目前很多网站都拥有大量用户，而且很多用户同事操作。<br><br>一般写数据的性能比较低，而且写操作相对读是比较少的，大部分的操作都是读。<br><br>因此，项目的架构应该将读和写操作区分开来，保证读操作的性能来提高用户体验。<br><br>该项目主要是通过3种不同的方式来实现数据库的读写分离。

# 项目结构 #

--dynamic-ds-demo&ensp;&ensp;   --父项目， 管理依赖的jar<br>
&ensp;&ensp;  |<br>
&ensp;&ensp; --rw-mapper &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;     --子项目，通过映射不同的mapper文件实现读写分离<br>
&ensp;&ensp;  |<br>
&ensp;&ensp; --rw-annotation &ensp;&ensp;&ensp;  --子项目，通过手动指定注解的方法实现读写分离<br>
&ensp;&ensp;  |<br>
&ensp;&ensp;  --rw-interceptor &ensp;&ensp;&ensp; --子项目，通过mybatis插件实现读写分离


## 读写分离的实现方式之一 ##

***rw-mapper 实现步骤***


一：读和写的操作，分别用不同的mapper接口文件，以及各自对应不同mapper.xml文件

二：分别配置读和写的数据源，然后在数据源配置类里面配置扫描的mapper接口路径，以及mapper.xml的路径

***结论**：*

该方式需要将读操作和写操作放到不同的mapper文件，现实中我们一般都是按某个操作类型来区分的，所以该方式的控制粒度太粗，而且不灵活，不建议使用

----------



（<font color='red'>注意：需要数据源需要区分主从，也就是需要有一个用@primary注解标识的数据源</font>）
<br>
<br>

## 读写分离的实现方式之二 ##

***rw-annotation 实现步骤***

一：通过spring提供的AbstractRoutingDataSource来手动配置数据源

二：通过自定义注解手动设置数据源

三：通过注解切面，将指定的数据源设置到ThreadLocal


***结论***

该方式可以手动控制哪些方法操作哪个数据库，但是只能在方法级别的实行读写分离，粒度不够精细，不能控制到具体的增删改查方法上面。可以在某些只会操作从库的项目上面使用。


----------


（<font color='red'>注意：该方式需要排除spring boot的数据库自动配置，否则会有循环依赖问题，@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) </font>）


## 读写分离的实现方式之三 ##

***rw-interceptor 实现步骤***

一：通过spring提供的AbstractRoutingDataSource配置数据源

二：实现mybatis的Interceptor拦截器，根据业务方法将数据源设置到ThreadLocal

三：蒋ThreadLocal变量的值赋值到AbstractRoutingDataSource提供的determineCurrentLookupKey

***结论：***

该方式是以mybatis插件提供读写分离，可以根据数据库执行的方法来设置数据源，从而实现增删改操作操作主库， 读操作操作从库，而且没有侵入性，推荐使用这种方法实现读写分离

（<font color='red'>注意：需要使用mybatis orm框架</font>）

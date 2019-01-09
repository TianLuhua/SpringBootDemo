###1.@Configuration注解的简单使用
1.@Configuration类似于SpringMVC配置文件，与AnnotationConfigApplicationContext一起使用就形成了SpringMVC的IOC容器<br>
    1.1 @Configuration注释一个类，表示被注解的类是一个配置文件。<br>
   ```java
        @SpringBootApplication
        public class SpringbootDemoApplication {
            public static void main(String[] args) {
                //SpringApplication.run(SpringbootDemoApplication.class, args);
                AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConfigParsent.class);
                Car toyota = (Car) configApplicationContext.getBean("toyota");
                toyota.print();
                Car bmw = (Car) configApplicationContext.getBean("bmw");
                bmw.print();
            }
        }
   ```
    1.2 @Bean注解一个方法，表示被注解的方法返回的对象将要被添加的SpringBoot的IOC容器中。如果注解@Bean()中没有填value值的话默认就是@Bean注解的该方法的名称。<br>
   ```java
        @Configuration
        public class JavaConfigA {
            /**
             * @Bean()中写的话默认值就是方法名， @Bean("getBMW")
             * @return
             */
            @Bean("bmw")
            public Car getBMW() {
                return new BMW();
            }
        }
   ```
    1.3 @Configuration注解的文件可以有多个，也可以用一个java类使用@Import注解来统一管理：
   ```java
        @Configuration
        @Import({JavaConfigA.class, JavaConfigB.class})
        public class ConfigParsent {
        }
   ```
###2.FreeMarker相关<br>


###3.自定义数据源（DataSource）,以阿里巴巴Druid为例：<br>
3.1 在pom.xml中添加Druid和Mysql依赖：
```
     <!--阿里的数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.11</version>
        </dependency>

        <!--用于连接mysql数据库-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
```
3.2 在全局配置文件（application.yml或者application.properties，两个文件等效，只是语法不同）
~~~ yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/mysql_test?useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver

    ########################以下是Druid配置###############################
    type: com.alibaba.druid.pool.DruidDataSource
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
    filters: stat,wall,log4j
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    useGlobalDataSourceStat: true
    #############################以上是Druid配置##########################
~~~
3.3 通过动创建配置文件，创建DruidDataSource的实例。将DruidDataSource实例与步骤3.2中的配置属性绑定，然后将DruidDataSource实例添加到Spring的ioc容器。
~~~
@Configuration //表示该类为配置类
@Import({JavaConfigA.class, JavaConfigB.class})
public class ConfigParsent implements WebMvcConfigurer {
    @ConfigurationProperties(prefix = "spring.datasource") //从配置文件（application.yaml）中读取属性，注入到Datasource中
    @Bean //表示将DruidDataSource的实例注入到Spring的ioc容器中去
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
~~~
然后其他的地方就可以通过注解：@Autowired，获取到DruidDataSource的实例了。

###4.Druid后台整和
4.1 配置Servlet
~~~
    //配置对整个应用监控：
    @Bean //将Druid的Servlet添加到Spring的ioc容器
    public ServletRegistrationBean getViewServelt() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        String[] urlArr = new String[]{"/druid/*"};//用户的请求只要以/druid/* 结尾，都会被改Servlet处理
        bean.setUrlMappings(Arrays.asList(urlArr));
        Map<String, String> map = new HashMap<>();
        //这里可以设置Druid后台登录用户名和密码
        map.put(ResourceServlet.PARAM_NAME_USERNAME, "tianluhua");
        map.put(ResourceServlet.PARAM_NAME_PASSWORD, "123456");
        bean.setInitParameters(map);
        return bean;
    }
~~~
4.2 配置Filter
~~~
    //配置过滤器，来实现对整个web的请求进行过滤，从而实现对web应用对数据库的访问
    @Bean //将Druid的Filter添加到Spring的ioc容器
    public FilterRegistrationBean getFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList(new String[]{"/*"}));//默认拦截所有的请求
        //不能对所有的请求拦截，比如：静态资源，druid的后台请求
        Map<String, String> map = new HashMap<>();
        map.put(WebStatFilter.PARAM_NAME_EXCLUSIONS, "*.js,*.css,*.png,/druid/*");
        bean.setInitParameters(map);
        return bean;
    }
~~~
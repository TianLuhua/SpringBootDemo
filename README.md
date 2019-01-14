1.@Configuration注解的简单使用：@Configuration类似于SpringMVC配置文件，与AnnotationConfigApplicationContext一起使用就形成了SpringMVC的IOC容器<br>
1.1 @Configuration注释一个类，表示被注解的类是一个配置文件。<br>
~~~java
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
~~~
1.2 @Bean注解一个方法，表示被注解的方法返回的对象将要被添加的SpringBoot的IOC容器中。如果注解@Bean()中没有填value值的话默认就是@Bean注解的该方法的名称。<br>
~~~java
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
~~~
1.3 @Configuration注解的文件可以有多个，也可以用一个java类使用@Import注解来统一管理：
~~~java
        @Configuration
        @Import({JavaConfigA.class, JavaConfigB.class})
        public class ConfigParsent {
        }
~~~

2.FreeMarker相关<br>

3.自定义数据源（DataSource）,以阿里巴巴Druid为例：<br>
3.1 在pom.xml中添加Druid和Mysql依赖：
~~~
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
~~~
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

4.Druid后台整和：<br>
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
5.整和MyBatis<br>
5.1 依赖MyBatis
~~~
        <!--用于连接mysql数据库-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>

        <!--依赖MyBatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
~~~
5.2 创建与数据库对应的实体类和Dao层接口<br>
~~~
实体类：
public class Customer {
    private Integer id;
    private Integer custId;
    private String custName;
    private String custCity;
    private String custAddress;
    private String custContact;
    private String custSex;
    
    //省略get/set还有无参和有参构造方法,有需要也可以重写toString方法
}

Dao层接口：
public interface CustomerDao {
    // 增
    public void addCustomer(Customer customer);
    // 删
    public void deleteCustomer(Integer id);
    //改
    public void updateCustomer(Customer customer);
    //查
    public Customer getCustomer(Integer id);
}
~~~
5.3 创建mybatis全局配置文件<br>
5.3.1 开启驼峰命名规则自动转换:resource/mybatis/mybatis-config.xml
~~~
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--开启驼峰命名规则自动转换-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
~~~
5.3.2 根据Dao层接口实现的sql查询语句：resource/mybatis/mapper/CustomerDao.xml
~~~
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.booyue.springboot_demo.dao.CustomerDao"> <!--mapper映射的必须是对应接口的包全名-->

    <insert id="addCustomer">
    insert into customers (cust_id,cust_name,cust_city,cust_address,cust_contact,cust_sex)
      values
      (#{custId},#{custName},#{custCity},#{custAddress},#{custContact},#{custSex})
    </insert>

    <delete id="deleteCustomer">
    delete from customers where id=#{id}
    </delete>

    <update id="updateCustomer">
    update customers s
      set
    s.cust_id=#{custId},s.cust_name=#{custName},s.cust_city=#{custCity},s.cust_address=#{custAddress},s.cust_contact=#{custContact},s.cust_sex=#{custSex}
      where
    s.id=#{id}
    </update>

    <select id="getCustomer" resultType="com.booyue.springboot_demo.model.Customer">
    select * from customers where customers.id=#{id}
    </select>

</mapper>
~~~
5.3.3 告诉SpringBoot我的mybatis的配置所在路径：需要卸载SpringBoot的全局配置文件中(application.yml)
~~~
#############################以下是没有batis配置##########################
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: mybatis/mapper/*.xml
#############################以上是没有batis配置##########################
~~~
5.3.4 告诉SpringBoot我们接口的位置：在SpringBoot主启动类上用@MapperScan加上接口所在包全名
~~~
@SpringBootApplication
@MapperScan({"com.booyue.springboot_demo.dao"})
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
}
~~~
5.3.5 mybatis操作数据库时候遇到的一系列问题：
    5.3.5.1 插入中文乱码报错问题：将数据库使用到char作为字符集的时候设置为utf-8
    5.3.5.2 处理系统一些警告提示：
   ~~~
   Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
   log4j:WARN No appenders could be found for logger (druid.sql.Connection).
   log4j:WARN Please initialize the log4j system properly.
   ~~~
   按照最新官方提示支持将com.mysql.jdbc.Driver  改为  com.mysql.cj.jdbc.Driver<br>
   
5.3.6 测试mybatis：
~~~
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void connectTest() throws SQLException {
        System.out.println(customerDao.getClass());

        //add
//        Customer customer = new Customer();
//        customer.setCustId(123);
//        customer.setCustName("huahua");
//        customer.setCustCity("湖南.湘西");
//        customer.setCustAddress("湖南.湘西.保靖");
//        customer.setCustContact("湖南.湘西.保靖.清水坪");
//        customer.setCustSex("M");
//        customerDao.addCustomer(customer);

        //delate
//         customerDao.deleteCustomer(978);

        //update
//        Customer customer=new Customer();
//        customer.setId(977);
//        customer.setCustId(110);
//        customer.setCustName("田露华");
//        customer.setCustCity("湖南.湘西");
//        customer.setCustAddress("湖南.湘西.保靖");
//        customer.setCustContact("湖南.湘西.保靖.清水坪");
//        customer.setCustSex("M");
//        customerDao.updateCustomer(customer);

        //select
//        Customer customer = customerDao.getCustomer(977);
//        System.out.println(customer.toString());

    }
}
~~~
6.整和JPA：<br>
6.1 添加依赖启动器：jdbc，jpa
~~~
 <!--用于连接mysql数据库-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>

        <!--jdbc启动器依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!--jpa启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
~~~
6.2 jpa全局配置文件
~~~
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/mysql_test?useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver

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

  #############################以下是batis配置##########################
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: mybatis/mapper/*.xml
  #############################以上是batis配置##########################

  ###########以下是jpa配置（Spring-data已经整和jpa，Spring-data中的jpa默认使用的hibernate）#############
  jpa:
    hibernate:
      ddl-auto: update  # 第一次简表create  后面用update
    show-sql: true

  ###########以上是jpa配置（Spring-data已经整和jpa，Spring-data中的jpa默认使用的hibernate）#############
~~~
6.2 创建对应的实体类、注解，添加对应Dao层接口（extends JpaRepository<实体类,主键类型> ）
~~~
//通过@Entity 表明是一个映射的实体类， @Id表明id， @GeneratedValue 字段自动生成
@Entity
public class Seller {

    @Id
    @GeneratedValue
    private Integer id;
    private String sellerName;
    private String sellerAddress;
    private String sellerContent;
    private String sellerType;
    private String sales;
    private Date sellerDate;

    public Seller() {
    }

    public Seller(Integer id, String sellerName, String sellerAddress, String sellerContent, String sellerType, String sales, Date sellerDate) {
        this.id = id;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.sellerContent = sellerContent;
        this.sellerType = sellerType;
        this.sales = sales;
        this.sellerDate = sellerDate;
    }
    //假装有get/set方法
 }
~~~
6.3 测试JPA
~~~
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTest {

    @Autowired
    private SellerDao sellerDao;

    @Test
    public void testJpa() {
        System.out.println(sellerDao.getClass());
        Seller seller = new Seller();
        seller.setSellerName("快乐水");
        seller.setSellerAddress("超市");
        seller.setSellerContent("具体什么内容，我也不知道");
        seller.setSellerType("饮料");
        seller.setSales(1);
        seller.setSellerDate(new Date());
        sellerDao.save(seller);
    }
}
~~~

6.4 jpa遇到的问题：<br>
    6.4.1.插入中文时候报错(这个暂时我还不知道怎么处理，本来想看看能不能从jpa的配置文件中解决。最后还是在数据库中直接修改的utf-8)


7.web小案例
7.1 登录
7.2 判断是否登录,用户拦截器实现：
 实现接口：HandlerInterceptor<br>
 
8.事务处理:SpringBoot中使用事务<br>
8.1 在启动类中添加注解：@EnableTransactionManagement<br>
8.2 在service层访问数据库方法中使用注解：@Transactional<br>
  8.1.1 注解在方法上：指定方法支持事务<br>
  8.1.2 注解在类上：对应类中的全部方法支持事务<br>
8.3 手动指定事务管理器(方法名字无所谓，但是需要@Bean注解和返回PlatformTransactionManager)：
~~~
    @Bean
    public PlatformTransactionManager testPlatformTransactionManager() {
        return new DataSourceTransactionManager();
    }
~~~
9.SpringBoot异常处理<br>
9.1 系统默认存放异常处理页面的目录：/resource/templates/error/4xx.html或者/resource/templates/error/5xx.html,如果不存在对应的页面SpringBoot就显示自己的错误处理页面<br>
9.2 在Controller的方法中用@ExceptionHandler注解，在方法会处理value对应的错误类型（该种方法优先于9.1）：
~~~
    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView exception(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", e);//注入错误类，可以在自定义的视图中显示信息
        modelAndView.setViewName("myerror");//指定错误视图名
        return modelAndView;
    }
~~~
9.3 @ControllerAdvice注解在类上:处理整个项目中所有的Controller爆出的异常:
~~~
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = {java.lang.NullPointerException.class})
    public ModelAndView exception(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", e);//注入错误类，可以在自定义的视图中显示信息
        modelAndView.setViewName("myerror");//指定错误视图名
        return modelAndView;
    }
}
~~~
9.4 配置类中添加对应方法（改方法需要用@Bean注解，为了能放该方法返回的类型的实例加入到Spring的IOC容器中），返回SimpleMappingExceptionResolver
~~~
   @Bean //方法名对应SpringIOC容器的id
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        //错误类型加上处理改错误类型的错误页面的名称。默认注入页面的错误类型的键是：excptions
        properties.put("java.lang.NullPointerException", "nullExcption");
        properties.put("java.lang.ArrayIndexOutOfBoundsException", "aioobExcption");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }

~~~
9.5 实现HandlerExceptionResolver接口，添加到SpringBoot的配置类


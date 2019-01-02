###@Configuration注解的简单使用
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

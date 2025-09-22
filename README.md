# Building Restful WS using Spring Boot

```
Banu Prakash C
Full Stack Architect, Corporate Trainer
Co-founder & EX-CTO: Lucida Technologies Pvt Ltd.,
Email: banuprakashc@yahoo.co.in; banuprakash.cr@gmail.com;
https://www.linkedin.com/in/banu-prakash-50416019/
https://github.com/BanuPrakash/SPRING

===================================

Softwares Required:
1) openJDK 21
https://jdk.java.net/java-se-ri/21

 For Mac machine USE SDKMAN to manage java

curl -s "https://get.sdkman.io" | bash

sdk install java 21.0.6-tem

sdk default java 21.0.6-tem 

https://mydeveloperplanet.com/2022/04/05/how-to-manage-your-jdks-with-sdkman/#:~:text=Some%20time%20ago%2C%20a%20colleague%20of%20mine,maintain%20different%20versions%20of%20JDKs%2C%20Maven%2C%20etc.


2) IntelliJ Ultimate edition https://www.jetbrains.com/idea/download/?section=mac

3) MySQL  [ Prefer on Docker]

Install Docker Desktop / PODMAN

Docker steps:

a) docker pull mysql
b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

Spring Boot 3.5 version needs JDK 17 version

Introduction to Spring Framework and Spring Boot.
RDBMS and JPA
Building RESTful WebServices
* AOP
* Exception Handling
* Micrometer
* Cache
* HATEOAS
* Async operations
Security

===================================================

SOLID Design Principles
S - Single Responsibility
O - Open close Principle
L - Liskov Substitution
I - Interface seggretgation
D - Dependency Injection

========
Container is a layer/application on top of JRE.
Servlet Container / EJB Container
Java 1.2 - Bean is a resuable software component

Spring Framework provides container.
- Life Cycle management of beans [instantiate, destroy]
- Bean: any object managed by spring container
- Wiring dependencies

Spring needs metadata in the form of XML / Annotation

```
    interface EmployeeDao {
        void addEmployee(Employee employee);
    }

    public class EmployeeDaoDbImpl implements EmployeeDao {
        // ..
        public void addEmployee(Employee employee) {
            ..
        }
    }

     public class EmployeeDaoMongoImpl implements EmployeeDao {
        // ...
        public void addEmployee(Employee employee) {
            ..
        }
    }

    public class AppService {
        private EmployeeDao employeeDao;

        private void setEmpDao(EmployeeDao edao) {
            this.employeeDao = edao;
        }

        public void doTask(Employee e) {
            employeeDao.addEmployee(e);
        }
    }
```

XML as metadata for Spring
beans.xml
```
    <beans>
        <bean id="rdbms" class="pkg.EmployeeDaoDbImpl" />
        <bean id="mongo" class="pkg.EmployeeDaoMongoImpl" />
        <bean id="service" class="pkg.AppService">
            <property name="empDao" ref="mongo" />
        </bean>
    </beans>

    // Internals
  // SAX Parser - Runtime
  Object rdbms = Class.forName("pkg.EmployeeDaoDbImpl").getConstructor().newInstance();
  Object mongo = Class.forName("pkg.EmployeeDaoDbImpl").getConstructor().newInstance();
  Object service = Class.forName("pkg.AppService").getConstructor().newInstance();
  service.setEmpDao(mongo);

  ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml", ""); 

  AppService service = ctx.getBean("service", AppService.class); 
```

Annotation as metadata: Preffered
Spring looks for the following annotations at type level and instantiates the beans:
1) @Component [util / helpers]
2) @Repository [persistence store]
3) @Service 
4) @Controller [ traditional web applications]
5) @RestController [ RESTful Web services]
6) @Configuration [ read config files, factory methods, ..]
7) @ControllerAdvice [ Global exception handlers ]
8) @ShellComponent [ spring boot 3.5 version onwards] [ Creating Shell Commands - REPL]

Spring uses @Autowired or Constructor Dependency Injection

Java 1.5 version onwards annotation is integral

```
   interface EmployeeDao {
        void addEmployee(Employee employee);
    }

    @Repository
    public class EmployeeDaoDbImpl implements EmployeeDao {
        // ..
        public void addEmployee(Employee employee) {
            ..
        }
    }

    @Service
    public class AppService {
        @Autowired
        private EmployeeDao employeeDao;

        public void doTask(Employee e) {
            employeeDao.addEmployee(e);
        }
    }

     ApplicationContext ctx = new AnnotationConfigApplicationContext();
     ctx.scan("com.cisco.prj"); // takes care of sub-packages 
     ctx.refresh();
```

A key benefit of using @Repository is that it enables Spring's exception translation mechanism.

https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

```
    try {


    } catch(SQLException exception) {
        if(exception.getErrorCode() == 1605) {
            throw new DuplicateKeyException("Product with the ID : " + id + " already exists!!");
        } else if(exception.getErrorCode() === ..) {

        }
    }
```

Spring Boot Framework:
Framework on top of Spring Framework.
Spring Boot 2 built on top of Spring Framework 5.x
Spring Boot 3 is built ont top of Spring Framework 6.x

Why Spring Boot?
* Highly Opiniated Framework, lots of config comes out of the box
- assume we are building web based application
1) Configures Embedded Tomcat Servlet Container / web server out of the box
Alternatives: Jetty / Netty 
2) Provides JACKSON library for Java to JSON and JSON to Java conversion
Alternates: Jettison / GSON / MOXY
3) Provides DispatcherServlet as FrontController

- assume we are building JPA / ORM based application
1) provides Database Connection Pooling using HikariCP library
 Alternate : C3p0/ DriverManagerDataSource ...
2) Uses Hibernate as ORM provider
Alternate: Toplink / KODO / JDO / OpenJPA ....

* Easy to Dockerize

=================================

SpringApplication.run(DemoApplication.class, args); is similar to    new AnnotationConfigApplicationContext();

@SpringBootApplication is 3 in 1
1) @Configuration
2) @ComponentScan(basePackage="com.cisco.demo")
3) @EnableAutoConfiguration


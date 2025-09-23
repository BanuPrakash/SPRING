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



```
@Autowired
private UserRepo userRepo;

Could not autowire. There is more than one bean of 'UserRepo' type.
Beans:
userRepoDbImpl   (UserRepoDbImpl.java) 
userRepoMongoImpl   (UserRepoMongoImpl.java)

```

Solution 1: using @Primary [ rarely used]

```
@Repository
@Primary
public class UserRepoDbImpl implements UserRepo {

@Repository
public class UserRepoMongoImpl implements UserRepo {

@Service
public class AppService {
    @Autowired
    private UserRepo userRepo; // UserRepoDbImpl will be wired

```

Solution 2: @Qualifier

```
@Repository
public class UserRepoDbImpl implements UserRepo{

@Repository
public class UserRepoMongoImpl implements UserRepo{

@Service
public class AppService {
    @Autowired
    @Qualifier("userRepoDbImpl")
    private UserRepo userRepo;


@Service
public class AdminService {
    @Autowired
    @Qualifier("userRepoMongoImpl")
    private UserRepo userRepo;
```

Solution 3: using @Profile

```
@Repository
@Profile("dev")
public class UserRepoDbImpl implements UserRepo{

@Repository
@Profile("prod")
public class UserRepoMongoImpl implements UserRepo{


@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;

Option 1: use application.properties
spring.profiles.active=dev

Option 2: Program arguments
More Run Configuration -> Modify Run Configuration -> Active profile = dev or prod
java -Dspring.profiles.active=prod com.cisco.demo.DemoApplication

```

Solution 4: using @ConditionalOnMissingBean

```
@Repository
public class UserRepoMongoImpl implements UserRepo{

@Repository
@ConditionalOnMissingBean(name="userRepoMongoImpl")
public class UserRepoDbImpl implements UserRepo{

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;

```

==============

Factory Methods wrt Spring:
1) 3rd party classes to be used in Spring Container
2) Object instantiate and intialize is not straight forward.

============

Spring integration with JPA/ORM

ORM: Object Relational Mapping
Object mapped to relational database tables

ORM frameworks for Java
Hibernate -- JBOSS -- RedHat
TopLink -- Oracle
KODO -- BEA -- Oracle
JDO -- SUN MS -- Oracle
OpenJPA -- Apache
..


Example of using Spring Framework and JPA:
```

@Entity
@Table(name="EMP")
public class Employee {
    @Column(name="EMP_NO")
    private String empNo;

    @Column(name="HDATE")
    private Date hireDate;
}

@Configuration
public class AppConfig {

    // @Bean informs Spring to invoke the method
    // returned object should be managed within Spring container
    @Bean
    public DataSource getDataSource() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.h2.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:h2:mem:testdb" );
        cpds.setUser("sa");
        cpds.setPassword("");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

    @Bean
    public EntityManagerFactory emf(DataSource ds) {
        LocalContainerEntityManagerDactoryBean emf = new LocalContainerEntityManagerDactoryBean();
        emf.setDataSource(ds);
        emf.setJpaVendor(new HibernateJpaVendor());
        emf.setPackagestoScan("com.cisco.prj.entity"); // where are my entities
        ...
        return emf;
    }
}


@Repository
public class EmployeeRepo {
    @PersistenceContext
    EntityManager em;

    public void addEmployee(Employee e) {
        em.persist(e);
    }
}
```

Spring Boot comes with Spring Data JPA module:
* we need to do entity mapping to database table
* No need to write any implementation classes. write an interface, implementation classes are generated by Spring Data JPA.
* no need to write DataSource, EntityManagerFactory ,,, Based on entries present in application.properties Spring Data JPA creates EntityManagerFactory, DataSource, ... [ No need for above AppConfig code]

=======================

```
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql
OR
podman run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql

banuprakash@Banuprakashs-MacBook-Pro SPRING % docker exec -it local-mysql bash
bash-4.4# mysql -u root -p
Enter password: 
mysql> create database SPRING;
mysql> use SPRING;
mysql> 

```

Spring Boot application with following dependencies:
1) lombok
2) Spring Data JPA
3) MySQL
later we will add web [ for RESTful]

https://docs.spring.io/spring-boot/appendix/application-properties/index.html

1) spring.jpa.hibernate.ddl-auto=create
create tables for entities when application starts, on application exist drop tables
Good for Testing environment only

2) spring.jpa.hibernate.ddl-auto=update
Top to Bottom apporach or Middle appraoch
create tables if not exist
if exists map table to entity
if required alter table like adding columns, change size of column

3) spring.jpa.hibernate.ddl-auto=verify
Bottom to Top Approach
map entities to existing tables.
Won't create tables if not present
won't alter tables

===
CommandLineRunner is a interface in Spring Boot that provides a mechanism to execute run() after the Spring Boot application has started and the application context has been fully loaded.


Issues to resolve if any with lombok:
1)
Settings --> Build, Execution, Deployment --> Compiler ->Annotation Processor --> shopapp -> Obtain processors from classpath.

2) mvn clean

==================
insert into customers values ('danny@cisco.com','Danny','Peter');

mysql> insert into customers values ('ria@cisco.com','Ria','Patel');


mysql> insert into customers values ('anne@cisco.com','Anne','Hathaway');

SQL uses table and column names

JP-QL uses class and field names; case sensitive; Polymorphic

```
@Entity
@Table(name="products")
class Product {
}
@Entity
@Table(name="tvs)
class Tv extends Product {
}
@Entity
@Table(name="mobiles")
class Mobile extends Product {

}
from Product; gets all records from products, "tvs" and "mobiles"
from Object; // records from all tables in database
```

By default built-in JpaRepository mutation code is Transactional.

Programmatic Transaction
```
    JDBC:
    public void doTask(...) {
        Connection con = ...
        try {
            con.setAutoCommit(false);
                // perform CRUD operations

            con.commit();
        } catch(SQLException ex) {
            con.rollback();
        }
    }

    Hibernate:
      public void doTask(...) {
        Session session = sessionFactory.getSession();
        Transaction tx = session.beginTransaction();
        try {
           
            session.save(e1);
            session.merge(e2);
            tx.commit();
        } catch(HibernateException ex) {
            tx.rollback();
        }
      }
```
Declarative Transaction:

```
@Transactional
public void doTask(...) {
    // JDBC / Hibernate / TopLink
}

```

Day 1 Recap:

Spring Framework vs Spring Boot
* Spring Core Module: Life cycle management of bean and Wiring dependencies.
* Spring Data JPA Module: HikariCP as database Connection pool and Hibernate as JPAVendor.
* @Entity, PersistenceContext, @Id, @Column, @Table ORM mapping
* JpaRepository interface - predefined Methods for CRUD operations
* JPA Projections using findByXXXX
* @Query for writing custom queries using SQL / JP-QL
* @Modifying
* @Transactional - Custom way of mutation / Insert

Built-in methods for INSERT / DELETE in JpaRepository have autocommit enabled.

productRepo.save(product); -- here in save() method they have turned on autocommit

By default for any other methods we are writing auto-commit is false

By placing    @Transactional, this Aspect works like
* if there are no exceptions on the method which has    @Transactional it commits else rollback

// Atomic 
@Transactional
doTask() {
    oper1
    oper2
    oper3
}
======================

Day 2:
update products set qty = 100 where 1 = 1;

Mapping associations:
1) one to many
2) many to one
3) many to many
4) one to one

https://www.database-answers.com/data_models/

Root aggregate of DDD:
https://martinfowler.com/bliki/BoundedContext.html


@JoinColumn with @ManytoOne introduces FK in owning table/entity
@JoinColumn with @OneToMany introduces FK in child table/entity

==========

Without Cascade Operations:
One order has 4 line items
```
    @OneToMany
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();

Save operation:
orderRepo.save(order);
itemRepo.save(i1);
itemRepo.save(i2);
itemRepo.save(i3);
itemRepo.save(i4);

Delete operation:
orderRepo.delete(order);
itemRepo.delete(i1);
itemRepo.delete(i2);
itemRepo.delete(i3);
itemRepo.delete(i4);
```

With Cascade:
One order has 4 line items
```
Composition and not Aggregation:
 @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();

Save operation:
orderRepo.save(order); // takes care of saving line items also

Delete operation:
orderRepo.delete(order); // takes care of deleting items also

```

Association: Composition or Aggregation

Fetching strategies:
1) by default one to many is Lazy fetching and many to one is EAGER fetching

orderDao.findById(1);
gets the customer data also but not line items

to get line items we need
itemDao.getItemsOfOrder(orderId); // select * from line_items where order_fk = 1;

Make EAGER fetching:
```
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();

orderDao.findById(1);
gets the customer data also but not line items
gets line items also for the given order because of EAGER fetching
```
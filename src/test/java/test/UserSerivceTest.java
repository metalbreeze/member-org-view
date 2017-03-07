package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shop.model.User;
import com.shop.service.UserService;




/**
 * @author niesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/spring-security.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
	}) //加载配置文件  
public class UserSerivceTest {
	//------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例  

	//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！  

	//@Transactional  

	//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时//指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！  

	//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  

	//------------  
	@Autowired(required = true)
	@Qualifier(value = "userService")
	UserService userService;
     
    @Test   //标明是测试方法  
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚  
    public void insert( ) {  
    	User u = new User();
    	u.setName("xxxxxxYYYY");
    	userService.addUser(u);
    }
}

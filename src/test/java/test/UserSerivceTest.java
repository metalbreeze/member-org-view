package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.controller.ReportCenterController;
import com.shop.dao.GroupDAO;
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.Operation;
import com.shop.model.ReportCenter;
import com.shop.model.User;
import com.shop.service.ReportService;
import com.shop.service.UserService;

/**
 * @author niesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 使用junit4进行测试
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/spring-security.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
// 加载配置文件
public class UserSerivceTest extends BaseObject {
	// ------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例

	// 这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！

	// @Transactional

	// 这里的事务关联到配置文件中的事务控制器（transactionManager =
	// "transactionManager"），同时//指定自动回滚（defaultRollback =
	// true）。这样做操作的数据才不会污染数据库！

	// @TransactionConfiguration(transactionManager = "transactionManager",
	// defaultRollback = true)

	private static final int sheet_1_reportCenter = 6;

	private static final int sheet_1_mobile = 5;

	private static final int sheet_1_name = 4;

	private static final int sheet_1_parent = 3;

	private static final int sheet_1_date = 2;

	private static final int sheet_1_id = 1;

	// ------------
	@Autowired(required = true)
	@Qualifier(value = "userService")
	UserService userService;
	
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	UserDAO userDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;	
	
	@Autowired(required = true)
	@Qualifier(value = "reportService")
	private ReportService reportService;	
	
	
	// 标明是测试方法
	@Transactional
	// 标明此方法需使用事务
	@Rollback(false)
	// 标明使用完此方法后事务不回滚,true时为回滚
	public void insert() {
		User u = new User();
		u.setName("xxxxxxYYYY");
		userService.addUser(u);
	}

	public static void main(String[] ss){
		new UserSerivceTest().importExcel();
	}
	public UserSerivceTest(){
		try {
			in = new FileInputStream("C:\\Users\\niesh\\Desktop\\茶多酚\\绿康-修定后的新数据.xls");
			wb = new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
	}
	FileInputStream in = null;
	Workbook wb =null;
	FormulaEvaluator evaluator =null; 
	@Test
	@Transactional
	@Rollback(false)
	public void importExcel() {
//		importUser();
//		importGroup();
		importWithdraw();
	}

	void importWithdraw(){
			Sheet sheet = wb.getSheetAt(3);
			for (Row row : sheet) {
				final int rowNum = row.getRowNum();
				if (rowNum < 7||rowNum>497) {
					continue;
				}
				info("==========================\nrowNum"+rowNum);
				Cell cell = row.getCell(0);
				if(cell==null||"".equals(cell.toString()))continue;
				evaluator.evaluateFormulaCell(row.getCell(34));
				info("cell is"+cell+" id is "+cell+" name is "+row.getCell(3)+" withdraw:"+row.getCell(34).getNumericCellValue());
				User u = userDAO.getUserById(10000+parseInt(cell));
				u.setWithdraw(new BigDecimal(row.getCell(34).getNumericCellValue()));
				userDAO.updateUser(u);
			}
	}
	void importUser(){
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			final int rowNum = row.getRowNum();
			if (rowNum < 1||rowNum>491) {
				continue;
			}
			info("==========================\nrowNum"+rowNum);
			Cell cell = row.getCell(sheet_1_id);
			if(cell==null||"".equals(cell.toString()))continue;
			User u = new User();
			u.setId(10000+parseInt(cell));
			u.setRegisterDate(parseDate(row.getCell(sheet_1_date)));
			String parentName= parseString(row.getCell(sheet_1_parent));
			if (parentName!=null&&!parentName.equals("")){
				info("parent:"+parentName);
				User p = userDAO.getUserByName(parentName);
				u.setParent(p);
			}
			u.setName(row.getCell(sheet_1_name).getStringCellValue());
			info("name"+u.getName()+"id:"+u.getId());
			u.setMobile(parseString(row.getCell(sheet_1_mobile)));
//			u.setWechat(parseString(row.getCell(6)));
			ReportCenter rc = new ReportCenter();
			rc.setId(Integer.parseInt(parseString(row.getCell(sheet_1_reportCenter))));
			u.setReportCenter(rc);
			userDAO.saveWithId(u,u.getId());

//			reportService.activeUser(null,u.getId(),null);
		}
	}
	void importGroup(){
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			final int rowNum = row.getRowNum();
			if (rowNum < 1||rowNum>491) {
				continue;
			}
			info("==========================\nrowNum"+rowNum);
			Cell cell = row.getCell(sheet_1_id);
			if(cell==null||"".equals(cell.toString()))continue;
			User u = new User();
			u.setId(10000+parseInt(cell));
			reportService.activeUser(null,u.getId(),null);
		}
	}
	public void active(int id) {
		logger.info("activeUser " + id);
		User target = userDAO.getUserById(id);

		ReportCenter r = target.getReportCenter();
		if (target.getReportCenter().getId() != r.getId()) {
			logger.error("activeUser 非法激活 " + id);
		} else {
			if (r.getElectricMoney() == null) {
				r.setElectricMoney(new BigDecimal(0));
			}
			BigDecimal b = r.getElectricMoney().add(
					new BigDecimal(999).negate());
			if (b.signum() == -1) {
				logger.error("activeUser 钱不够 " + id);
			} else {
				r.setElectricMoney(b);
				Group group = groupDAO.getAvailableGroup();
				List<User> users = group.getUsers();
				logger.info(group.getName() + " group.getUsers().size() "
						+ users.size());
				if (users.size() == 62) {
					target.setLevel("F");
					target.setStatus(old_status);
					users.add(target);
					group.setEndDate(new Timestamp(System.currentTimeMillis()));
					Group group1 = new Group();
					Group group2 = new Group();
					group1.setName(group.getId() + "-A");
					group2.setName(group.getId() + "-B");
					Group.transform(group);
					for (int i = 1; i < Group.labels.length; i++) {
						List<User> ulist = group.getLevelUsers().get(
								Group.labels[i]);
						for (int j = 0; j < ulist.size(); j++) {
							User user = (User) ulist.get(j);
							logger.debug("upgrade level:" + user.getId()
									+ " level:" + user.getLevel() + " to:"
									+ Group.labels[i - 1]);
							user.setLevel(Group.labels[i - 1]);
							// 会员分红奖
							user.addBonusMoney(Group.levelMoney[i - 1]);
							Operation op = new Operation();
							op.setMoney(new BigDecimal(Group.levelMoney[i - 1]));
							op.setOperation(Group.labels[i] + "-"
									+ Group.labels[i - 1]);
							op.setReportCenter(r);
							op.setUser(user);
							operationDAO.addOperation(op);
							if (j >= Group.maxLabels[i] / 2) {
								user.setGroup(group2);
								user.setPosition(j - Group.maxLabels[i] / 2 + 1);
							} else {
								user.setGroup(group1);
								user.setPosition(j + 1);
							}
						}
					}
					final User userLevealA = group.getLevelUsers()
							.get(Group.labels[0]).get(0);
					group.getUsers().clear();
					groupDAO.addGroup(group1);
					groupDAO.addGroup(group2);
					groupDAO.updateGroup(group);
					// target.setLevel("E");
					// target.setGroup(group2);
					// target.setStatus(old_status);
					// userDAO.updateUser(target);
					logger.debug(target.getName() + " 用户已经激活" + "\r\n 分群成功:id"
							+ group1.getId() + "和" + group2.getId());
					userLevealA.setLevel("F");
					userLevealA.setPosition(1);
					userLevealA.setGroup(group1);
					userDAO.updateUser(userLevealA);
					// 分享回馈奖
					if (userLevealA.getParent() != null) {
						userLevealA.getParent().addFeedbackMoney(3000);
						operationDAO.addOperation(new Operation(target
								.getParent(), r, "回馈奖", 3000));
					}
					Operation op = new Operation();
					op.setMoney(90);
					op.setOperation("费用2");
					op.setReportCenter(r);
					op.setUser(userLevealA);
					operationDAO.addOperation(op);
					// 有空测试下.
					if (userLevealA.getParent() != null) {
						userDAO.updateUser(userLevealA.getParent());
					}
					// 出局服务费
					Operation op1 = new Operation();
					op1.setMoney(90);
					op1.setOperation("出局");
					op1.setReportCenter(r);
					op1.setUser(userLevealA);
					operationDAO.addOperation(op1);
					r.addMoney1(90);
				} else {
					groupDAO.refresh(group);
					Group.transform(group);
					String string = group.getAvailbleLabes().get(0);
					logger.debug(" getAvailbleLabes  " + string);
					target.setLevel(string);
					target.setGroup(group);
					target.setStatus(old_status);
					int i = userDAO.getCurrentPosiztionByGroup(group, "F");
					target.setPosition(i + 1);
					userDAO.updateUser(target);
					logger.debug(flashMsg + target.getName() + " 用户已经激活");
				}
				if (target.getParent() != null) {
					target.getParent().addSaleMoney(100);
					// 有空测试下.直推奖
					userDAO.updateUser(target.getParent());
					operationDAO.addOperation(new Operation(target.getParent(),
							r, "直推奖", 100, target.getParent().getSaleMoney()
									+ "xx 直推" + target.getId() + "直推"));
				} else {
					operationDAO.addOperation(new Operation(target, r,
							"无直推人", 100));
				}

				logger.info("active a user " + target.toString()
						+ " with money");
				// 每报一单 10
				Operation op = new Operation();
				op.setMoney(10);
				op.setOperation("费用1");
				op.setReportCenter(r);
				op.setUser(target);
				operationDAO.addOperation(op);
				r.addMoney1(10);
				reportCenterDAO.updateReportCenter(r);
			}
		}
	}
	
	
	
	Timestamp parseDate(Cell c){
		if(c!=null&&c.getCellType()==Cell.CELL_TYPE_NUMERIC&&HSSFDateUtil.isCellDateFormatted(c)){
			return new Timestamp(HSSFDateUtil.getJavaDate(c.getNumericCellValue()).getTime());
		}else{
			return null;
		}
	}
	int parseInt(Cell c){
		if(c!=null&&c.getCellType()==Cell.CELL_TYPE_NUMERIC){
			c.setCellType(Cell.CELL_TYPE_STRING);
			return Integer.parseInt(c.getStringCellValue());
		}else{
			return 0;
		}
	}
	String parseString(Cell c){
		if(c==null){
			return null;
		}else if(c.getCellType()==Cell.CELL_TYPE_NUMERIC){
			c.setCellType(Cell.CELL_TYPE_STRING);
			return c.getStringCellValue();
		}else{
			return c.getStringCellValue();
		}
	}
}

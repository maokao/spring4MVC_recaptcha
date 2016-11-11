#Simple spring with security

####準備工作：

工具名稱      | 版本                  
------------ | ------------------------------
Eclipse      | Luna Service Release 1 (4.4.1)
Oracle-JDK   | 1.7.0_55
Apache tomcat| 7.0.53
MySQL Server | 5.6.22

由於 Eclipse 內建 Maven 以及 Git ，所以不用另行安裝

Git client 端建議使用 [Source Tree](http://www.sourcetreeapp.com/)

MySQL Client 端建議使用 [MySQL workbench](http://www.mysql.com/products/workbench/)

####匯入專案：
共分為以下幾個步驟：

1. Eclipse "File" -> "import"
2. 選擇 "Git" -> "project from git"
3. 選擇 "Existing local repository" 
	* 如果要 import 的目標沒有在清單上則選取 "add" 增加來源
4. 選擇 "import existing project" -> "simple-spring-with-security" -> "Finish" 

####資料庫設定：
總共有以下幾點要做確認：

1. 找到 "simple-spring-with-security" -> "scripts" -> `test.sql`
2. 使用 MySQL workbench 將 `test.sql` 匯入 `test` 資料庫中
3. 匯入成功的話會看到 "test" 資料庫中有下列兩張表
#####users
user   | password                        |email              | enabled
------ | ------------------------------- | ----------------- |-------
root   | e10adc3949ba59abbe56e057f20f883e|mkyong@test.org.tw |1
alex   | e10adc3949ba59abbe56e057f20f883e|alex@test.org.tw   |1
maokao | e10adc3949ba59abbe56e057f20f883e|asdfs@123.123.213  |1
amelia | e10adc3949ba59abbe56e057f20f883e|adf@123.123.123    |1
ranma  | e10adc3949ba59abbe56e057f20f883e|maokao25@gmail.com |1

	以及
#####user_roles
user_role_id | username | role                  
------------ | -------- | -------
            1| root     | ROLE_USER
            2| root     | ROLE_ADMIN
            3| alex     | ROLE_USER
            4| maokao   | ROLE_ADMIN
            5| amelia   | ROLE_ADMIN

4. 確認 `/simple-spring-with-security/src/main/webapp/WEB-INF/spring-database.xml` 內容
	* 資料庫`使用者帳號密碼`與`本機 MySQL 的帳號密碼`一致
	* `有權限`從 `localhost 登入`
	* `有權限`查詢與刪除修改 `test 資料表`

	內容如下:
	
		<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
		
			<bean id="dataSource" 	class="org.springframework.jdbc.datasource.DriverManagerDataSource">

				<property name="driverClassName" value="com.mysql.jdbc.Driver" />
		
				<property name="url" value="jdbc:mysql://localhost:3306/test" />
				####### 確認資料庫登入位置 localhost 、 port 3306 以及資料表 test #######
		
				<property name="username" value="dbuser" />
				####### 確認資料庫登入使用者帳號為 dbuser #######
		
				<property name="password" value="password" />
				####### 確認資料庫登入使用者密碼為 password #######
		
			</bean>

		</beans>


####執行測試：
分為以下幾個步驟：

1. 在 "Eclipse" 中專案 "simple-spring-with-security" 右鍵 -> "Run As" -> "Run on Server"
2. 看到 "Hello World" ， 並且點擊 "Login"
3. 看到 "Login Form" ， 輸入`使用者 "root"` 跟 `密碼 "123456"`
4. 登入成功則確認`網頁程式執行成功`以及`資料庫連接成功`


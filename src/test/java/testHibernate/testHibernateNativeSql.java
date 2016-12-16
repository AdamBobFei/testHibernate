package testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

/**
 * 在旧版本的hibernate中用的方法时createSqlQuery
 * heiberNate执行原生sql的例子
 * @author Administrator
 *
 */
public class testHibernateNativeSql {

	public final static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	public SessionFactory getSessionFactory() {
		
		SessionFactory sessionFactory=null;
		try {
			 sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			
		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}

		return sessionFactory;
	}
	
	
	/**
	 * 执行本地sql语句
	 * 一般用于执行创建表,插入数据
	 * @param nativeQuerySql
	 */
	public void executeNativeSql(String nativeQuerySql){
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			NativeQuery nativeQuery = session.createNativeQuery(nativeQuerySql);
			nativeQuery.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
	
	}
	
	/**
	 * 根据
	 * @param nativeQuerySql
	 */
	public void queryForNativeSql(String nativeQuerySql) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			NativeQuery nativeQuery = session.createNativeQuery(nativeQuerySql);
			//这里的参数从1开始
			nativeQuery.setParameter(1, "fei1");
			//得到查询的第一个实例,如果想得到所有用nativeQuery.getResultList();
			System.out.println(nativeQuery.getSingleResult().toString());;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
	}
	
	
	/**
	 * 测试插入表
	 */
	@Test
	public void createTable(){
		String nativeQuerySql="create table foo(name char(10) ,age char(3));";
		executeNativeSql(nativeQuerySql);
	}
	
	/**
	 * 插入数据
	 */
	@Test
	public void interTable(){
		createTable();
		String nativeQuerySql="insert into foo values('fei','3');";
		executeNativeSql(nativeQuerySql);
	}
	
	/**
	 * 测试查询
	 */
	@Test
	public void selectTavle(){
		interTable();
		String nativeQuerySql="select count(*) from foo where name = ?";
		queryForNativeSql(nativeQuerySql);
	}

}

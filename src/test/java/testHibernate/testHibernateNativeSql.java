package testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

/**
 * �ھɰ汾��hibernate���õķ���ʱcreateSqlQuery
 * heiberNateִ��ԭ��sql������
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
	 * ִ�б���sql���
	 * һ������ִ�д�����,��������
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
	 * ����
	 * @param nativeQuerySql
	 */
	public void queryForNativeSql(String nativeQuerySql) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			NativeQuery nativeQuery = session.createNativeQuery(nativeQuerySql);
			//����Ĳ�����1��ʼ
			nativeQuery.setParameter(1, "fei1");
			//�õ���ѯ�ĵ�һ��ʵ��,�����õ�������nativeQuery.getResultList();
			System.out.println(nativeQuery.getSingleResult().toString());;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		session.getTransaction().commit();
		session.close();
	}
	
	
	/**
	 * ���Բ����
	 */
	@Test
	public void createTable(){
		String nativeQuerySql="create table foo(name char(10) ,age char(3));";
		executeNativeSql(nativeQuerySql);
	}
	
	/**
	 * ��������
	 */
	@Test
	public void interTable(){
		createTable();
		String nativeQuerySql="insert into foo values('fei','3');";
		executeNativeSql(nativeQuerySql);
	}
	
	/**
	 * ���Բ�ѯ
	 */
	@Test
	public void selectTavle(){
		interTable();
		String nativeQuerySql="select count(*) from foo where name = ?";
		queryForNativeSql(nativeQuerySql);
	}

}

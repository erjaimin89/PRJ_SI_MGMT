package contoller;

import hibernate.HibernateUtil;
import hibernate.UserEntity;

import java.util.Iterator;
import java.util.List;

import main.UserType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageUsers {
	private SessionFactory factory;

	public ManageUsers() {
		factory = HibernateUtil.getSessionFactory();
	}

	public boolean addUser(UserEntity entity) {

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEntity> listUsers(int type) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<UserEntity> users = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(
					"FROM UserEntity where type =:type order by userName");
			query.setParameter("type", type);
			users = (List<UserEntity>) query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}
	
	public boolean isUserExists(String name) {
		List<UserEntity> users = listUsers(UserType.USER.ordinal());
		for (Iterator<UserEntity> iterator = users.iterator(); iterator.hasNext();) {
			UserEntity user = iterator.next();
			if (user.getUserName().equalsIgnoreCase(
					name)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}
	
	public UserEntity fetchUserEntity(long userId){
		Session session = factory.openSession();
		Transaction tx = null;
		UserEntity user = null;
		try {
			tx = session.beginTransaction();
			user = (UserEntity) session.get(UserEntity.class, userId);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

}

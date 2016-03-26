package contoller;

import hibernate.CustomerEntity;
import hibernate.HibernateUtil;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageCustomers {

	private SessionFactory factory;

	public ManageCustomers() {
		factory = HibernateUtil.getSessionFactory();
	}

	public boolean addCustomer(CustomerEntity entity) {

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

	public boolean updateCustomer(CustomerEntity entity) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(entity);
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
	public List<CustomerEntity> listCustomer() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<CustomerEntity> customers = null;
		try {
			tx = session.beginTransaction();
			customers = (List<CustomerEntity>)session.createQuery(
					"FROM CustomerEntity order by customerName").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerEntity> listCustomerByName(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<CustomerEntity> customers = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM CustomerEntity where customerName like :customerName order by customerName");
			query.setParameter("customerName", name + "%");
			customers = (List<CustomerEntity>) query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerEntity> listCustomerByCompany(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<CustomerEntity> customers = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM CustomerEntity where companyName like :companyName order by companyName");
			query.setParameter("companyName", name + "%");
			customers = (List<CustomerEntity>)query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerEntity> listCustomerByNameAndCompany(String name, String company) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<CustomerEntity> customers = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("FROM CustomerEntity where customerName like :customerName and companyName like :companyName order by customerName");
			query.setParameter("customerName", name + "%");
			query.setParameter("companyName", company + "%");
			customers = (List<CustomerEntity>)query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	public CustomerEntity getCustomer(long id) {
		Session session = factory.openSession();
		Transaction tx = null;
		CustomerEntity customer = null;
		try {
			tx = session.beginTransaction();
//			Query query = session
//					.createQuery("FROM CustomerEntity where customerName = :customerName");
//			query.setParameter("customerName", customerName);
//			customers = (List<CustomerEntity>)query.list();
			customer = session.get(CustomerEntity.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customer;
	}

	public int deleteCustomer(String customerName) {
		Session session = factory.openSession();
		Transaction tx = null;
		int update = 0;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("DELETE FROM CustomerEntity where customerName = :customerName");
			query.setParameter("customerName", customerName);
			update = query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return update;
	}

	public int activateDeactivateCustomer(long id) {
		CustomerEntity entity = getCustomer(id);
		Session session = factory.openSession();
		Transaction tx = null;
		int update = 0;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("update CustomerEntity set active = :active where customerName = :customerName");
			if (entity.isActive()) {
				query.setParameter("active", false);
			} else {
				query.setParameter("active", true);
			}
			query.setParameter("customerName", entity.getCustomerName());
			update = query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return update;
	}

	public boolean isCustomerExists(String customerName) {
		List<CustomerEntity> customers = listCustomer();
		for (Iterator<CustomerEntity> iterator = customers.iterator(); iterator.hasNext();) {
			CustomerEntity customer = iterator.next();
			if (customer.getCustomerName().equalsIgnoreCase(
					customerName)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	public CustomerEntity fetchCustomerEntity(long key) {
		Session session = factory.openSession();
		Transaction tx = null;
		CustomerEntity cust = null;
		try {
			tx = session.beginTransaction();
			cust = (CustomerEntity) session.get(CustomerEntity.class, key);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cust;
	}
}

package contoller;

import hibernate.HibernateUtil;
import hibernate.InvoiceDetailsEntity;
import hibernate.TaxEntity;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageTaxes {
	private SessionFactory factory;

	public ManageTaxes() {
		factory = HibernateUtil.getSessionFactory();
	}

	public boolean addTaxingScheme(TaxEntity entity) {
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

	public int updateTaxingScheme(TaxEntity entity) {
		Session session = factory.openSession();
		Transaction tx = null;
		int update = 0;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("update TaxEntity set primaryTaxName = :primaryTaxName, primaryTaxRate =:primaryTaxRate, "
							+ "secondaryTaxName =:secondaryTaxName, secondaryTaxRate =:secondaryTaxRate, "
							+ "updatedOn =:updatedOn, isSecondaryCompound =:isSecondaryCompound where taxSchemeName = :taxSchemeName");
			query.setParameter("primaryTaxName", entity.getPrimaryTaxName());
			query.setParameter("primaryTaxRate", entity.getPrimaryTaxRate());
			query.setParameter("secondaryTaxName", entity.getSecondaryTaxName());
			query.setParameter("secondaryTaxRate", entity.getSecondaryTaxRate());
			query.setParameter("isSecondaryCompound", entity.getIsSecondaryCompound());
			query.setParameter("updatedOn", entity.getUpdatedOn());
			query.setParameter("taxSchemeName", entity.getTaxSchemeName());
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

	@SuppressWarnings("unchecked")
	public List<TaxEntity> listTaxingSchemes() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<TaxEntity> taxSchemes = null;
		try {
			tx = session.beginTransaction();
			taxSchemes = (List<TaxEntity>) session.createQuery(
					"FROM TaxEntity order by taxSchemeName").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return taxSchemes;
	}

	public TaxEntity fetchTaxEntity(long taxId){
		Session session = factory.openSession();
		Transaction tx = null;
		TaxEntity tax = null;
		try {
			tx = session.beginTransaction();
			tax = (TaxEntity) session.get(TaxEntity.class, taxId);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tax;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<InvoiceDetailsEntity> getInvoiceDetailEntities(){
		Session session = factory.openSession();
		Transaction tx = null;
		List<InvoiceDetailsEntity> tax = null;
		try {
			tx = session.beginTransaction();
			tax = (List<InvoiceDetailsEntity>) session.createQuery(
					"FROM InvoiceDetailsEntity order by detailId").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tax;
	}

	public void createEmptyDetails() {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			for(int i=0; i<4; i++){
				session.save(new InvoiceDetailsEntity("", 0));
			}
			for(int i=4; i<8; i++){
				session.save(new InvoiceDetailsEntity("", 1));
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateInvoiceDetails(List<InvoiceDetailsEntity> detailEntities) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			for(InvoiceDetailsEntity entity : detailEntities){
				session.update(entity);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	

}

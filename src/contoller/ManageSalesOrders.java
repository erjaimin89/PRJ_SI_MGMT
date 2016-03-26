package contoller;

import hibernate.HibernateUtil;
import hibernate.InvoiceEntity;
import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.SalesOrderDetailEntity;
import hibernate.SalesOrderEntity;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import main.ShippingStatus;
import main.Utils;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageSalesOrders {
	
	private SessionFactory factory;

	public ManageSalesOrders() {
		factory = HibernateUtil.getSessionFactory();
	}

	public Long addSalesOrder(SalesOrderEntity entity, boolean shipped){
		
			Session session = factory.openSession();
			Transaction tx = null;
			Long orderId = null;
			try {
				tx = session.beginTransaction();
				Set<SalesOrderDetailEntity> orderDetails = entity.getOrderDetails();
				if(shipped){
					for(SalesOrderDetailEntity orderDetail : orderDetails){
						ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
						for(ProductDetailEntity detailEntity : productEntity.getDetails()){
							if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
								detailEntity.setXsmall(detailEntity.getXsmall() - orderDetail.getXsmall());
								detailEntity.setSmall(detailEntity.getSmall() - orderDetail.getSmall());
								detailEntity.setMedium(detailEntity.getMedium() - orderDetail.getMedium());
								detailEntity.setLarge(detailEntity.getLarge() - orderDetail.getLarge());
								detailEntity.setXlarge(detailEntity.getXlarge() - orderDetail.getXlarge());
								detailEntity.setXxlarge(detailEntity.getXxlarge() - orderDetail.getXxlarge());
								detailEntity.setXxxlarge(detailEntity.getXxxlarge() - orderDetail.getXxxlarge());
								detailEntity.setMix(detailEntity.getMix() - orderDetail.getMix());
								detailEntity.setSubTotal(detailEntity.getSubTotal() - orderDetail.getTotalQuantity());
								
								productEntity.setQuantity(productEntity.getQuantity() - orderDetail.getTotalQuantity());
								break;
							}
						}
						session.update(productEntity);
					}
				}else{
					for(SalesOrderDetailEntity orderDetail : orderDetails){
						ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
						for(ProductDetailEntity detailEntity : productEntity.getDetails()){
							if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
								detailEntity.setRes_xsmall(detailEntity.getRes_xsmall() + orderDetail.getXsmall());
								detailEntity.setRes_small(detailEntity.getRes_small() + orderDetail.getSmall());
								detailEntity.setRes_medium(detailEntity.getRes_medium() + orderDetail.getMedium());
								detailEntity.setRes_large(detailEntity.getRes_large() + orderDetail.getLarge());
								detailEntity.setRes_xlarge(detailEntity.getRes_xlarge() + orderDetail.getXlarge());
								detailEntity.setRes_xxlarge(detailEntity.getRes_xxlarge() + orderDetail.getXxlarge());
								detailEntity.setRes_xxxlarge(detailEntity.getRes_xxxlarge() + orderDetail.getXxxlarge());
								detailEntity.setRes_mix(detailEntity.getRes_mix() + orderDetail.getMix());
								break;
							}
						}
						session.update(productEntity);
					}
				}
				orderId = (Long) session.save(entity);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		return orderId;
	}


	@SuppressWarnings("unchecked")
	public List<SalesOrderEntity> listOrders() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SalesOrderEntity> orders = null;
		try {
			tx = session.beginTransaction();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			final Query query = session.createQuery(
					"FROM SalesOrderEntity where orderDate >= :updatedOn order by salesOrderId desc");
			query.setParameter("updatedOn", cal.getTimeInMillis());
			orders = (List<SalesOrderEntity>)query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return orders;
	}


	@SuppressWarnings("unchecked")
	public List<SalesOrderEntity> listOrdersBySearch(String name, String orderNo, int shipStatus, int invStatus, int payStatus,
			Date value1, Date value2) {
		StringBuilder sb = new StringBuilder();
		boolean flag= false;
		sb.append("FROM SalesOrderEntity WHERE ");
		if(name.trim().length()!=0){
			flag = true;
			sb.append("customer.customerName like :customerName ");
		}
		if(orderNo.trim().length()!=0){
			if(flag)
				sb.append(" and ");
			sb.append("salesOrderId = :salesOrderId ");
			flag = true;
		}
		if(shipStatus >= 0){
			if(flag)
				sb.append(" and ");
			sb.append("shippingStatus = :shippingStatus ");
			flag=true;
		}
		if(invStatus >= 0){
			if(flag)
				sb.append(" and ");
			sb.append("invoiceStatus = :invoiceStatus ");
			flag=true;
		}
		if(payStatus >= 0){
			if(flag)
				sb.append(" and ");
			sb.append("paymentStatus = :paymentStatus ");
			flag = true;
		}
		if(value1 != null){
			if(flag)
				sb.append(" and ");
			sb.append("orderDate >= :orderDate1 ");
			flag=true;
		}
		if(value2 != null){
			if(flag)
				sb.append(" and ");
			sb.append("orderDate <= :orderDate2 ");
			flag=true;
		}
		if(!flag)
			sb.append(" orderDate >= :updatedOn ");
		
		sb.append("order by orderDate desc");
		Session session = factory.openSession();
		Transaction tx = null;
		List<SalesOrderEntity> orders = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sb.toString());
			if(name.trim().length()!=0)
				query.setParameter("customerName", name + "%");
			if(orderNo.trim().length()!=0)
				query.setParameter("salesOrderId", Utils.normalOrderId(orderNo));
			if(shipStatus >= 0)
				query.setParameter("shippingStatus", shipStatus);
			if(invStatus >= 0)
				query.setParameter("invoiceStatus", invStatus);
			if(payStatus >= 0)
				query.setParameter("paymentStatus", payStatus);
			Calendar cal = Calendar.getInstance();
			if(value1 != null){
				cal.setTime(value1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				query.setParameter("orderDate1", cal.getTimeInMillis());
			}
			if(value2 != null){
				cal.setTime(value2);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				query.setParameter("orderDate2", cal.getTimeInMillis());
			}	
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			if(!flag)
				query.setParameter("updatedOn", cal.getTimeInMillis());
			orders = (List<SalesOrderEntity>) query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return orders;
	}

	
	@SuppressWarnings("unchecked")
	public List<SalesOrderEntity> getCustomerOrders(long customerId) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<SalesOrderEntity> orders = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(
					"FROM SalesOrderEntity where customer.customerId = :customerId order by salesOrderId desc");
			query.setParameter("customerId", customerId);
			orders = (List<SalesOrderEntity>) query.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return orders;
	}


	public boolean updateSalesOrder(SalesOrderEntity salesOrderEntity, SortedSet<SalesOrderDetailEntity> newOrderDetails,
			boolean shipped, boolean notshipped) {
		if(salesOrderEntity != null){
			Session session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Set<SalesOrderDetailEntity> orderDetails = salesOrderEntity.getOrderDetails();
				for(SalesOrderDetailEntity orderDetail : orderDetails){
					ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
					for(ProductDetailEntity detailEntity : productEntity.getDetails()){
						if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
							int xs = detailEntity.getRes_xsmall() - orderDetail.getXsmall();
							int s = detailEntity.getRes_small() - orderDetail.getSmall();
							int m = detailEntity.getRes_medium() - orderDetail.getMedium();
							int l = detailEntity.getRes_large() - orderDetail.getLarge();
							int xl = detailEntity.getRes_xlarge() - orderDetail.getXlarge();
							int xxl = detailEntity.getRes_xxlarge() - orderDetail.getXxlarge();
							int xxxl = detailEntity.getRes_xxxlarge() - orderDetail.getXxxlarge();
							int mx = detailEntity.getRes_mix() - orderDetail.getMix();
							
							detailEntity.setRes_xsmall((xs<0)?0:xs);
							detailEntity.setRes_small((s<0)?0:s);
							detailEntity.setRes_medium((m<0)?0:m);
							detailEntity.setRes_large((l<0)?0:l);
							detailEntity.setRes_xlarge((xl<0)?0:xl);
							detailEntity.setRes_xxlarge((xxl<0)?0:xxl);
							detailEntity.setRes_xxxlarge((xxxl<0)?0:xxxl);
							detailEntity.setRes_mix((mx<0)?0:mx);
							
							break;
						}
					}
					session.update(productEntity);
					session.delete(orderDetail);
				}
				if(shipped){
					for(SalesOrderDetailEntity orderDetail : newOrderDetails){
						ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
						for(ProductDetailEntity detailEntity : productEntity.getDetails()){
							if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
								int xs = detailEntity.getXsmall() - orderDetail.getXsmall();
								int s = detailEntity.getSmall() - orderDetail.getSmall();
								int m = detailEntity.getMedium() - orderDetail.getMedium();
								int l = detailEntity.getLarge() - orderDetail.getLarge();
								int xl = detailEntity.getXlarge() - orderDetail.getXlarge();
								int xxl = detailEntity.getXxlarge() - orderDetail.getXxlarge();
								int xxxl = detailEntity.getXxxlarge() - orderDetail.getXxxlarge();
								int mx = detailEntity.getMix() - orderDetail.getMix();
								int subtotal = detailEntity.getSubTotal() - orderDetail.getTotalQuantity();
								int total = productEntity.getQuantity() - orderDetail.getTotalQuantity();
								
								detailEntity.setXsmall((xs<0)?0:xs);
								detailEntity.setSmall((s<0)?0:s);
								detailEntity.setMedium((m<0)?0:m);
								detailEntity.setLarge((l<0)?0:l);
								detailEntity.setXlarge((xl<0)?0:xl);
								detailEntity.setXxlarge((xxl<0)?0:xxl);
								detailEntity.setXxxlarge((xxxl<0)?0:xxxl);
								detailEntity.setMix((mx<0)?0:mx);
								detailEntity.setSubTotal((subtotal<0)?0:subtotal);
								
								productEntity.setQuantity((total<0)?0:total);
								
								break;
							}
						}
						session.update(productEntity);
					}
					
				}else if(notshipped){
					for(SalesOrderDetailEntity orderDetail : newOrderDetails){
							ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
							for(ProductDetailEntity detailEntity : productEntity.getDetails()){
								if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
									detailEntity.setRes_xsmall(detailEntity.getRes_xsmall() + orderDetail.getXsmall());
									detailEntity.setRes_small(detailEntity.getRes_small() + orderDetail.getSmall());
									detailEntity.setRes_medium(detailEntity.getRes_medium() + orderDetail.getMedium());
									detailEntity.setRes_large(detailEntity.getRes_large() + orderDetail.getLarge());
									detailEntity.setRes_xlarge(detailEntity.getRes_xlarge() + orderDetail.getXlarge());
									detailEntity.setRes_xxlarge(detailEntity.getRes_xxlarge() + orderDetail.getXxlarge());
									detailEntity.setRes_xxxlarge(detailEntity.getRes_xxxlarge() + orderDetail.getXxxlarge());
									detailEntity.setRes_mix(detailEntity.getRes_mix() + orderDetail.getMix());
									break;
								}
							}
							
							session.update(productEntity);
						}	
				}
				salesOrderEntity.setOrderDetails(newOrderDetails);
				session.saveOrUpdate(salesOrderEntity);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				return false;
			} finally {
				session.close();
			}
		}
		return true;
	}


	public BigDecimal getCustomerBalance(long customerId) {
		BigDecimal balance = new BigDecimal("0.00");
		if(customerId != 0){
			List<SalesOrderEntity> customerOrders = getCustomerOrders(customerId);
			for(SalesOrderEntity entity : customerOrders){
				balance = balance.add(entity.getRemainingBalance());
			}
			return balance;
		}
		return balance;
	}
	
	public void saveInvoice(long orderId, byte[] data, long invoiceDate) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SalesOrderEntity entity = (SalesOrderEntity) session.get(SalesOrderEntity.class, orderId);
			Blob blob = Hibernate.getLobCreator(session).createBlob(data);
			entity.setInvoice(new InvoiceEntity(blob, invoiceDate));
			session.update(entity);
			tx.commit();
			blob.free();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean cancelOrder(Long orderId) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SalesOrderEntity entity = (SalesOrderEntity) session.get(SalesOrderEntity.class, orderId);
			Set<SalesOrderDetailEntity> orderDetails = entity.getOrderDetails();
			if(entity.getShippingStatus() == ShippingStatus.SHIPPED.ordinal()){
				for(SalesOrderDetailEntity orderDetail : orderDetails){
					ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
					for(ProductDetailEntity detailEntity : productEntity.getDetails()){
						if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
							detailEntity.setXsmall(detailEntity.getXsmall() + orderDetail.getXsmall());
							detailEntity.setSmall(detailEntity.getSmall() + orderDetail.getSmall());
							detailEntity.setMedium(detailEntity.getMedium() + orderDetail.getMedium());
							detailEntity.setLarge(detailEntity.getLarge() + orderDetail.getLarge());
							detailEntity.setXlarge(detailEntity.getXlarge() + orderDetail.getXlarge());
							detailEntity.setXxlarge(detailEntity.getXxlarge() + orderDetail.getXxlarge());
							detailEntity.setXxxlarge(detailEntity.getXxxlarge() + orderDetail.getXxxlarge());
							detailEntity.setMix(detailEntity.getMix() + orderDetail.getMix());
							detailEntity.setSubTotal(detailEntity.getSubTotal() + orderDetail.getTotalQuantity());
							productEntity.setQuantity(productEntity.getQuantity() + orderDetail.getTotalQuantity());
							break;
						}
					}
					session.update(productEntity);
				}
			}else{
				for(SalesOrderDetailEntity orderDetail : orderDetails){
					ProductEntity productEntity = session.get(ProductEntity.class, orderDetail.getItemId());
					for(ProductDetailEntity detailEntity : productEntity.getDetails()){
						if(detailEntity.getItemDetailId() == orderDetail.getItemDetailId()){
							detailEntity.setRes_xsmall(detailEntity.getRes_xsmall() - orderDetail.getXsmall());
							detailEntity.setRes_small(detailEntity.getRes_small() - orderDetail.getSmall());
							detailEntity.setRes_medium(detailEntity.getRes_medium() - orderDetail.getMedium());
							detailEntity.setRes_large(detailEntity.getRes_large() - orderDetail.getLarge());
							detailEntity.setRes_xlarge(detailEntity.getRes_xlarge() - orderDetail.getXlarge());
							detailEntity.setRes_xxlarge(detailEntity.getRes_xxlarge() - orderDetail.getXxlarge());
							detailEntity.setRes_xxxlarge(detailEntity.getRes_xxxlarge() - orderDetail.getXxxlarge());
							detailEntity.setRes_mix(detailEntity.getRes_mix() - orderDetail.getMix());
							break;
						}
					}
					session.update(productEntity);
				}
			}
			session.delete(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
}

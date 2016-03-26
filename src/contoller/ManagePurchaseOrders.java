package contoller;

import hibernate.HibernateUtil;
import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.PurchaseOrderDetailEntity;
import hibernate.PurchaseOrderEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManagePurchaseOrders {

    private SessionFactory factory;

    public ManagePurchaseOrders() {
        factory = HibernateUtil.getSessionFactory();
    }

    public boolean savePurchaseOrder(PurchaseOrderEntity entity) {

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Set<PurchaseOrderDetailEntity> orderDetails = entity.getOrderDetails();
            Set<Long> itemSet = new HashSet<Long>();
            for (PurchaseOrderDetailEntity orderDetail : orderDetails) {
                long itemId = orderDetail.getItemId();
                ProductEntity productEntity = session.get(ProductEntity.class, itemId);
                if (!itemSet.contains(itemId)) {
                    productEntity.setTransitQuantity(productEntity.getTransitQuantity()
                            + orderDetail.getItemTotalQuantity());
                    productEntity.setUpdatedOn(System.currentTimeMillis());
                    itemSet.add(itemId);
                }
                Set<ProductDetailEntity> details = productEntity.getDetails();
                for (ProductDetailEntity detailEntity : details) {
                    if (detailEntity.getItemDetailId() == orderDetail.getItemDetailId()) {
                        detailEntity.setTrans_xsmall(detailEntity.getTrans_xsmall() + orderDetail.getXsmall());
                        detailEntity.setTrans_small(detailEntity.getTrans_small() + orderDetail.getSmall());
                        detailEntity.setTrans_medium(detailEntity.getTrans_medium() + orderDetail.getMedium());
                        detailEntity.setTrans_large(detailEntity.getTrans_large() + orderDetail.getLarge());
                        detailEntity.setTrans_xlarge(detailEntity.getTrans_xlarge() + orderDetail.getXlarge());
                        detailEntity.setTrans_xxlarge(detailEntity.getTrans_xxlarge() + orderDetail.getXxlarge());
                        detailEntity.setTrans_xxxlarge(detailEntity.getTrans_xxxlarge() + orderDetail.getXxxlarge());
                        detailEntity.setTrans_mix(detailEntity.getTrans_mix() + orderDetail.getMix());
                        detailEntity.setTotal_transit(detailEntity.getTotal_transit()
                                + orderDetail.getDetailTotalQuantity());

                        break;
                    }
                }
                session.update(productEntity);
            }
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseOrderEntity> listOrders() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<PurchaseOrderEntity> orders = null;
        try {
            tx = session.beginTransaction();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            final Query query = session
                    .createQuery("FROM PurchaseOrderEntity where orderDate >= :updatedOn order by purchaseOrderId desc");
            query.setParameter("updatedOn", cal.getTimeInMillis());
            orders = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseOrderEntity> listOrdersBySearch(String lotNo, Date value1, Date value2) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        sb.append("FROM PurchaseOrderEntity WHERE ");
        if (lotNo.trim().length() != 0) {
            flag = true;
            sb.append("lotNo like :lotNo ");
        }
        if (value1 != null) {
            if (flag) {
                sb.append(" and ");
            }
            sb.append("orderDate >= :orderDate1 ");
            flag = true;
        }
        if (value2 != null) {
            if (flag) {
                sb.append(" and ");
            }
            sb.append("orderDate <= :orderDate2 ");
            flag = true;
        }
        if (!flag) {
            sb.append(" orderDate >= :updatedOn ");
        }
        sb.append("order by orderDate desc");
        Session session = factory.openSession();
        Transaction tx = null;
        List<PurchaseOrderEntity> orders = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(sb.toString());
            if (lotNo.trim().length() != 0) {
                query.setParameter("lotNo", lotNo + "%");
            }
            Calendar cal = Calendar.getInstance();
            if (value1 != null) {
                cal.setTime(value1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                query.setParameter("orderDate1", cal.getTimeInMillis());
            }
            if (value2 != null) {
                cal.setTime(value2);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                query.setParameter("orderDate2", cal.getTimeInMillis());
            }
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -1);
            if (!flag) {
                query.setParameter("updatedOn", cal.getTimeInMillis());
            }
            orders = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public boolean isLotNumberExists(String lotNo) {
        List<PurchaseOrderEntity> orders = listOrders();
        for (PurchaseOrderEntity order : orders) {
            if (order.getLotNo().equalsIgnoreCase(lotNo)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public boolean updatePurchaseOrder(PurchaseOrderEntity entity, boolean shipped) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            PurchaseOrderEntity orderEntity = session.load(PurchaseOrderEntity.class, entity.getPurchaseOrderId());
            Set<Long> itemSet = new HashSet<Long>();
            for (PurchaseOrderDetailEntity orderDetailEntity : orderEntity.getOrderDetails()) {
                long itemId = orderDetailEntity.getItemId();
                ProductEntity productEntity = session.get(ProductEntity.class, itemId);
                if (!itemSet.contains(itemId)) {
                    productEntity.setTransitQuantity(productEntity.getTransitQuantity()
                            - orderDetailEntity.getItemTotalQuantity());
                    productEntity.setUpdatedOn(System.currentTimeMillis());
                    itemSet.add(itemId);
                }
                Set<ProductDetailEntity> details = productEntity.getDetails();
                for (ProductDetailEntity detailEntity : details) {
                    if (detailEntity.getItemDetailId() == orderDetailEntity.getItemDetailId()) {
                        detailEntity.setTrans_xsmall(detailEntity.getTrans_xsmall() - orderDetailEntity.getXsmall());
                        detailEntity.setTrans_small(detailEntity.getTrans_small() - orderDetailEntity.getSmall());
                        detailEntity.setTrans_medium(detailEntity.getTrans_medium() - orderDetailEntity.getMedium());
                        detailEntity.setTrans_large(detailEntity.getTrans_large() - orderDetailEntity.getLarge());
                        detailEntity.setTrans_xlarge(detailEntity.getTrans_xlarge() - orderDetailEntity.getXlarge());
                        detailEntity.setTrans_xxlarge(detailEntity.getTrans_xxlarge() - orderDetailEntity.getXxlarge());
                        detailEntity.setTrans_xxxlarge(detailEntity.getTrans_xxxlarge()
                                - orderDetailEntity.getXxxlarge());
                        detailEntity.setTrans_mix(detailEntity.getTrans_mix() - orderDetailEntity.getMix());
                        detailEntity.setTotal_transit(detailEntity.getTotal_transit()
                                - orderDetailEntity.getDetailTotalQuantity());
                        break;
                    }
                }
                session.update(productEntity);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        Session session1 = factory.openSession();
        Transaction tx1 = null;
        try {
            tx1 = session1.beginTransaction();
            Set<PurchaseOrderDetailEntity> orderDetails = entity.getOrderDetails();
            Set<Long> itemSet1 = new HashSet<Long>();
            if (shipped) {
                for (PurchaseOrderDetailEntity orderDetail : orderDetails) {
                    long itemId = orderDetail.getItemId();
                    ProductEntity productEntity = session1.get(ProductEntity.class, itemId);
                    if (!itemSet1.contains(itemId)) {
                        productEntity.setQuantity(productEntity.getQuantity() + orderDetail.getItemTotalQuantity());
                        productEntity.setUpdatedOn(System.currentTimeMillis());
                        itemSet1.add(itemId);
                    }
                    Set<ProductDetailEntity> details = productEntity.getDetails();
                    for (ProductDetailEntity detailEntity : details) {
                        if (detailEntity.getItemDetailId() == orderDetail.getItemDetailId()) {
                            detailEntity.setXsmall(detailEntity.getXsmall() + orderDetail.getXsmall());
                            detailEntity.setSmall(detailEntity.getSmall() + orderDetail.getSmall());
                            detailEntity.setMedium(detailEntity.getMedium() + orderDetail.getMedium());
                            detailEntity.setLarge(detailEntity.getLarge() + orderDetail.getLarge());
                            detailEntity.setXlarge(detailEntity.getXlarge() + orderDetail.getXlarge());
                            detailEntity.setXxlarge(detailEntity.getXxlarge() + orderDetail.getXxlarge());
                            detailEntity.setXxxlarge(detailEntity.getXxxlarge() + orderDetail.getXxxlarge());
                            detailEntity.setMix(detailEntity.getMix() + orderDetail.getMix());
                            detailEntity.setSubTotal(detailEntity.getSubTotal() + orderDetail.getDetailTotalQuantity());

                            int xQty = 0, sQty = 0, mQty = 0, lQty = 0, xlQty = 0, xxlQty = 0, xxxlQty = 0, mixQty = 0, totalQty = 0;
                            if ((detailEntity.getProd_xsmall() - orderDetail.getXsmall()) > 0) {
                                xQty = detailEntity.getProd_xsmall() - orderDetail.getXsmall();
                            }
                            if ((detailEntity.getProd_small() - orderDetail.getSmall()) > 0) {
                                sQty = detailEntity.getProd_small() - orderDetail.getSmall();
                            }
                            if ((detailEntity.getProd_medium() - orderDetail.getMedium()) > 0) {
                                mQty = detailEntity.getProd_medium() - orderDetail.getMedium();
                            }
                            if ((detailEntity.getProd_large() - orderDetail.getLarge()) > 0) {
                                lQty = detailEntity.getProd_large() - orderDetail.getLarge();
                            }
                            if ((detailEntity.getProd_xlarge() - orderDetail.getXlarge()) > 0) {
                                xlQty = detailEntity.getProd_xlarge() - orderDetail.getXlarge();
                            }
                            if ((detailEntity.getProd_xxlarge() - orderDetail.getXxlarge()) > 0) {
                                xxlQty = detailEntity.getProd_xxlarge() - orderDetail.getXxlarge();
                            }
                            if ((detailEntity.getProd_xxxlarge() - orderDetail.getXxxlarge()) > 0) {
                                xxxlQty = detailEntity.getProd_xxxlarge() - orderDetail.getXxxlarge();
                            }
                            if ((detailEntity.getProd_mix() - orderDetail.getMix()) > 0) {
                                mixQty = detailEntity.getProd_mix() - orderDetail.getMix();
                            }

                            totalQty = xQty + sQty + mQty + lQty + xlQty + xxlQty + xxxlQty + +mixQty;

                            detailEntity.setProd_xsmall(xQty);
                            detailEntity.setProd_small(sQty);
                            detailEntity.setProd_medium(mQty);
                            detailEntity.setProd_large(lQty);
                            detailEntity.setProd_xlarge(xlQty);
                            detailEntity.setProd_xxlarge(xxlQty);
                            detailEntity.setProd_xxxlarge(xxxlQty);
                            detailEntity.setProd_mix(mixQty);
                            detailEntity.setTotal_production(totalQty);
                            int prodQty = productEntity.getProductionQuantity() - totalQty;
                            productEntity.setProductionQuantity((prodQty > 0) ? prodQty : 0);

                            break;
                        }
                    }
                    session1.update(productEntity);
                }
            } else {
                for (PurchaseOrderDetailEntity orderDetail : orderDetails) {
                    long itemId = orderDetail.getItemId();
                    ProductEntity productEntity = session1.get(ProductEntity.class, itemId);
                    if (!itemSet1.contains(itemId)) {
                        productEntity.setTransitQuantity(productEntity.getTransitQuantity()
                                + orderDetail.getItemTotalQuantity());
                        productEntity.setUpdatedOn(System.currentTimeMillis());
                        itemSet1.add(itemId);
                    }
                    Set<ProductDetailEntity> details = productEntity.getDetails();
                    for (ProductDetailEntity detailEntity : details) {
                        if (detailEntity.getItemDetailId() == orderDetail.getItemDetailId()) {
                            detailEntity.setTrans_xsmall(detailEntity.getTrans_xsmall() + orderDetail.getXsmall());
                            detailEntity.setTrans_small(detailEntity.getTrans_small() + orderDetail.getSmall());
                            detailEntity.setTrans_medium(detailEntity.getTrans_medium() + orderDetail.getMedium());
                            detailEntity.setTrans_large(detailEntity.getTrans_large() + orderDetail.getLarge());
                            detailEntity.setTrans_xlarge(detailEntity.getTrans_xlarge() + orderDetail.getXlarge());
                            detailEntity.setTrans_xxlarge(detailEntity.getTrans_xxlarge() + orderDetail.getXxlarge());
                            detailEntity
                                    .setTrans_xxxlarge(detailEntity.getTrans_xxxlarge() + orderDetail.getXxxlarge());
                            detailEntity.setTrans_mix(detailEntity.getTrans_mix() + orderDetail.getMix());
                            detailEntity.setTotal_transit(detailEntity.getTotal_transit()
                                    + orderDetail.getDetailTotalQuantity());
                            break;
                        }
                    }
                    session1.update(productEntity);
                }
            }
            session1.update(entity);
            tx1.commit();
        } catch (HibernateException e) {
            if (tx1 != null) {
                tx1.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session1.close();
        }
        return true;
    }

    public boolean cancelPurchaseOrder(long orderNo) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            PurchaseOrderEntity orderEntity = session.get(PurchaseOrderEntity.class, orderNo);
            Set<Long> itemSet = new HashSet<Long>();
            for (PurchaseOrderDetailEntity orderDetailEntity : orderEntity.getOrderDetails()) {
                long itemId = orderDetailEntity.getItemId();
                ProductEntity productEntity = session.get(ProductEntity.class, itemId);
                if (!itemSet.contains(itemId)) {
                    productEntity.setTransitQuantity(productEntity.getTransitQuantity()
                            - orderDetailEntity.getItemTotalQuantity());
                    productEntity.setUpdatedOn(System.currentTimeMillis());
                    itemSet.add(itemId);
                }
                Set<ProductDetailEntity> details = productEntity.getDetails();
                for (ProductDetailEntity detailEntity : details) {
                    if (detailEntity.getItemDetailId() == orderDetailEntity.getItemDetailId()) {
                        detailEntity.setTrans_xsmall(detailEntity.getTrans_xsmall() - orderDetailEntity.getXsmall());
                        detailEntity.setTrans_small(detailEntity.getTrans_small() - orderDetailEntity.getSmall());
                        detailEntity.setTrans_medium(detailEntity.getTrans_medium() - orderDetailEntity.getMedium());
                        detailEntity.setTrans_large(detailEntity.getTrans_large() - orderDetailEntity.getLarge());
                        detailEntity.setTrans_xlarge(detailEntity.getTrans_xlarge() - orderDetailEntity.getXlarge());
                        detailEntity.setTrans_xxlarge(detailEntity.getTrans_xxlarge() - orderDetailEntity.getXxlarge());
                        detailEntity.setTrans_xxxlarge(detailEntity.getTrans_xxxlarge()
                                - orderDetailEntity.getXxxlarge());
                        detailEntity.setTrans_mix(detailEntity.getTrans_mix() - orderDetailEntity.getMix());
                        detailEntity.setTotal_transit(detailEntity.getTotal_transit()
                                - orderDetailEntity.getDetailTotalQuantity());
                        break;
                    }
                }
                session.update(productEntity);
            }
            session.delete(orderEntity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @SuppressWarnings({ "rawtypes" })
    public List<Object[]> getInTransitDetails(long itemId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createSQLQuery(
                            "SELECT a.LOT_NO, b.ITEM_DETAIL_ID, b.ITEM_DETAIL_NAME, b.SIZE_1, b.SIZE_2, "
                                    + "b.SIZE_3, b.SIZE_4, b.SIZE_5, b.SIZE_6, b.SIZE_7, b.SIZE_8, b.TOTAL_QUANTITY, a.ARRIVAL_DATE "
                                    + " FROM PURCHASE_ORDERS a, PURCHASE_ORDERS_DETAILS b "
                                    + "WHERE b.PURCHASE_ORDER_ID=a.ID and a.SHIPPING_STATUS=0 and b.ITEM_ID=:itemId order by b.ITEM_DETAIL_NAME, a.ARRIVAL_DATE")
                    .setParameter("itemId", itemId);
            List data = query.list();
            Iterator iterator = data.iterator();
            while (iterator.hasNext()) {
                list.add((Object[]) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }
}

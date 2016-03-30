package contoller;

import hibernate.BackUpOrderDetailEntity;
import hibernate.BackUpOrderEntity;
import hibernate.HibernateUtil;
import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.ThresholdDetailEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.BackUpOrderStatus;
import main.ComboItem;
import main.Utils;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageBackUpOrders {

    private SessionFactory factory;

    public ManageBackUpOrders() {
        factory = HibernateUtil.getSessionFactory();
    }

    public boolean addThresholdDetails(List<ThresholdDetailEntity> detailEntitites) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (ThresholdDetailEntity entity : detailEntitites) {
                session.saveOrUpdate(entity);
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<ThresholdDetailEntity> fetchThresholdDetails(long itemId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<ThresholdDetailEntity> backupOrders = null;
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createQuery("FROM ThresholdDetailEntity where itemId = :itemId order by itemDetailId");
            query.setParameter("itemId", itemId);
            backupOrders = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return backupOrders;
    }

    @SuppressWarnings("unchecked")
    public List<ThresholdDetailEntity> fetchThresholdDetailsByParty(long partyId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<ThresholdDetailEntity> backupOrders = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ThresholdDetailEntity where partyId = :partyId order by itemId");
            query.setParameter("partyId", partyId);
            backupOrders = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return backupOrders;
    }

    /**
     * @param selectedParty
     * @param selectedItem
     * @param arrvDate
     * @param remarks
     * @return
     */
    public boolean generateBackUpOrder(ComboItem selectedParty, ComboItem selectedItem, Long arrvDate, String remarks) {
        ManageProducts products = new ManageProducts();
        List<ThresholdDetailEntity> detailsByParty = fetchThresholdDetailsByParty(selectedParty.getKey());
        if (!detailsByParty.isEmpty()) {
            Map<Long, List<ThresholdDetailEntity>> detailIds = new HashMap<Long, List<ThresholdDetailEntity>>();
            for (ThresholdDetailEntity next : detailsByParty) {
                long itemId = next.getItemId();
                if (detailIds.containsKey(itemId)) {
                    List<ThresholdDetailEntity> list = detailIds.get(itemId);
                    list.add(next);
                    detailIds.put(itemId, list);
                } else {
                    ArrayList<ThresholdDetailEntity> arrayList = new ArrayList<ThresholdDetailEntity>();
                    arrayList.add(next);
                    detailIds.put(itemId, arrayList);
                }
            }
            if (!detailIds.isEmpty()) {
                Set<Long> keySet = detailIds.keySet();
                Set<BackUpOrderDetailEntity> backUpOrderDetails = new HashSet<BackUpOrderDetailEntity>();
                int totalQuantity = 0;
                for (Long itemId : keySet) {

                    if (selectedItem != null) {
                        if (itemId != selectedItem.getKey()) {
                            continue;
                        }
                    }

                    ProductEntity fetchProductEntity = products.fetchProductEntity(itemId);
                    Set<ProductDetailEntity> productDetails = fetchProductEntity.getDetails();

                    List<ThresholdDetailEntity> thresholdDetails = detailIds.get(itemId);
                    List<BackUpOrderDetailEntity> itemDetails = new ArrayList<BackUpOrderDetailEntity>();

                    int itemTotalQuantity = 0;
                    for (ThresholdDetailEntity thresholdDetail : thresholdDetails) {
                        for (ProductDetailEntity productDetail : productDetails) {
                            if (thresholdDetail.getItemDetailId() == productDetail.getItemDetailId()) {
                                boolean flag = false;
                                int xsmall = 0, small = 0, medium = 0, large = 0, xlarge = 0, xxlarge = 0, xxxlarge = 0, mix = 0;
                                int xQty = productDetail.getProd_xsmall() - productDetail.getTrans_xsmall();
                                xQty = (xQty > 0) ? xQty : 0;
                                if ((productDetail.getXsmall() + productDetail.getTrans_xsmall() + xQty) <= thresholdDetail
                                        .getXsmallThreshold()) {
                                    xsmall = thresholdDetail.getXsmallTarget();
                                    flag = true;
                                }
                                int sQty = productDetail.getProd_small() - productDetail.getTrans_small();
                                sQty = (sQty > 0) ? sQty : 0;
                                if ((productDetail.getSmall() + productDetail.getTrans_small() + sQty) <= thresholdDetail
                                        .getSmallThreshold()) {
                                    small = thresholdDetail.getSmallTarget();
                                    flag = true;
                                }
                                int mQty = productDetail.getProd_medium() - productDetail.getTrans_medium();
                                mQty = (mQty > 0) ? mQty : 0;
                                if ((productDetail.getMedium() + productDetail.getTrans_medium() + mQty) <= thresholdDetail
                                        .getMediumThreshold()) {
                                    medium = thresholdDetail.getMediumTarget();
                                    flag = true;
                                }
                                int lQty = productDetail.getProd_large() - productDetail.getTrans_large();
                                lQty = (lQty > 0) ? lQty : 0;
                                if ((productDetail.getLarge() + productDetail.getTrans_large() + lQty) <= thresholdDetail
                                        .getLargeThreshold()) {
                                    large = thresholdDetail.getLargeTarget();
                                    flag = true;
                                }
                                int xlQty = productDetail.getProd_xlarge() - productDetail.getTrans_xlarge();
                                xlQty = (xlQty > 0) ? xlQty : 0;
                                if ((productDetail.getXlarge() + productDetail.getTrans_xlarge() + xlQty) <= thresholdDetail
                                        .getXlargeThreshold()) {
                                    xlarge = thresholdDetail.getXlargeTarget();
                                    flag = true;
                                }
                                int xxlQty = productDetail.getProd_xxlarge() - productDetail.getTrans_xxlarge();
                                xxlQty = (xxlQty > 0) ? xxlQty : 0;
                                if ((productDetail.getXxlarge() + productDetail.getTrans_xxlarge() + xxlQty) <= thresholdDetail
                                        .getXxlargeThreshold()) {
                                    xxlarge = thresholdDetail.getXxlargeTarget();
                                    flag = true;
                                }
                                int xxxlQty = productDetail.getProd_xxxlarge() - productDetail.getTrans_xxxlarge();
                                xxxlQty = (xxxlQty > 0) ? xxxlQty : 0;
                                if ((productDetail.getXxxlarge() + productDetail.getTrans_xxxlarge() + xxxlQty) <= thresholdDetail
                                        .getXxxlargeThreshold()) {
                                    xxxlarge = thresholdDetail.getXxxlargeTarget();
                                    flag = true;
                                }
                                int mixQty = productDetail.getProd_mix() - productDetail.getTrans_mix();
                                mixQty = (mixQty > 0) ? mixQty : 0;
                                if ((productDetail.getMix() + productDetail.getTrans_mix() + mixQty) <= thresholdDetail
                                        .getMixThreshold()) {
                                    mix = thresholdDetail.getMixTarget();
                                    flag = true;
                                }
                                if (flag) {
                                    int detailTotalQuantity = xsmall + small + medium + large + xlarge + xxlarge
                                            + xxxlarge + mix;
                                    itemTotalQuantity += detailTotalQuantity;
                                    itemDetails.add(new BackUpOrderDetailEntity(selectedParty.getKey(), selectedParty
                                            .getValue(), itemId, fetchProductEntity.getItemName(), productDetail
                                            .getItemDetailId(), productDetail.getName(), xsmall, small, medium, large,
                                            xlarge, xxlarge, xxxlarge, mix, detailTotalQuantity));
                                }
                                break;
                            }
                        }
                    }
                    for (BackUpOrderDetailEntity itemDetail : itemDetails) {
                        itemDetail.setItemTotalQuantity(itemTotalQuantity);
                    }
                    backUpOrderDetails.addAll(itemDetails);
                    totalQuantity += itemTotalQuantity;
                }
                if (!backUpOrderDetails.isEmpty()) {
                    BackUpOrderEntity backUpEntity = new BackUpOrderEntity(totalQuantity, arrvDate,
                            BackUpOrderStatus.NOT_SENT.ordinal(), Utils.getDateInMillies(new Date()),
                            System.currentTimeMillis(), backUpOrderDetails, remarks);
                    Session session = factory.openSession();
                    Transaction tx = null;
                    try {
                        tx = session.beginTransaction();
                        session.saveOrUpdate(backUpEntity);
                        tx.commit();
                        return true;
                    } catch (HibernateException e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        e.printStackTrace();
                        return false;
                    } finally {
                        session.close();
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List<BackUpOrderEntity> listOrders() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<BackUpOrderEntity> orders = null;
        try {
            tx = session.beginTransaction();
            orders = session.createQuery("FROM BackUpOrderEntity order by backUpOrderId desc").list();
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

    public boolean deleteOrder(Long backUpOrderId) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            BackUpOrderEntity myObject = session.load(BackUpOrderEntity.class, backUpOrderId);
            if (myObject.getShippingStatus() == BackUpOrderStatus.SENT.ordinal()) {
                Set<BackUpOrderDetailEntity> orderDetails = myObject.getOrderDetails();
                Set<Long> itemSet = new HashSet<Long>();
                for (BackUpOrderDetailEntity orderDetail : orderDetails) {
                    long itemId = orderDetail.getItemId();
                    ProductEntity productEntity = session.get(ProductEntity.class, itemId);
                    if (!itemSet.contains(itemId)) {
                        int prodQty = productEntity.getProductionQuantity() - orderDetail.getItemTotalQuantity();
                        productEntity.setProductionQuantity((prodQty > 0) ? prodQty : 0);
                        productEntity.setUpdatedOn(System.currentTimeMillis());
                        itemSet.add(itemId);
                    }
                    for (ProductDetailEntity detailEntity : productEntity.getDetails()) {
                        if (detailEntity.getItemDetailId() == orderDetail.getItemDetailId()) {
                            int xsQty = detailEntity.getProd_xsmall() - orderDetail.getXsmall();
                            int sQty = detailEntity.getProd_small() - orderDetail.getSmall();
                            int mQty = detailEntity.getProd_medium() - orderDetail.getMedium();
                            int lQty = detailEntity.getProd_large() - orderDetail.getLarge();
                            int xlQty = detailEntity.getProd_xlarge() - orderDetail.getXlarge();
                            int xxlQty = detailEntity.getProd_xxlarge() - orderDetail.getXxlarge();
                            int xxxlQty = detailEntity.getProd_xxxlarge() - orderDetail.getXxxlarge();
                            int mixQty = detailEntity.getProd_mix() - orderDetail.getMix();
                            int totalQty = detailEntity.getTotal_production() - orderDetail.getDetailTotalQuantity();
                            detailEntity.setProd_xsmall((xsQty > 0) ? xsQty : 0);
                            detailEntity.setProd_small((sQty > 0) ? sQty : 0);
                            detailEntity.setProd_medium((mQty > 0) ? mQty : 0);
                            detailEntity.setProd_large((lQty > 0) ? lQty : 0);
                            detailEntity.setProd_xlarge((xlQty > 0) ? xlQty : 0);
                            detailEntity.setProd_xxlarge((xxlQty > 0) ? xxlQty : 0);
                            detailEntity.setProd_xxxlarge((xxxlQty > 0) ? xxxlQty : 0);
                            detailEntity.setProd_mix((mixQty > 0) ? mixQty : 0);
                            detailEntity.setTotal_production((totalQty > 0) ? totalQty : 0);
                            break;
                        }
                    }
                    session.update(productEntity);
                }
            }
            session.delete(myObject);
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
    public List<BackUpOrderEntity> listOrdersBySearch(String orderNo, Date value1, Date value2) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        sb.append("FROM BackUpOrderEntity WHERE ");
        if (orderNo.trim().length() != 0) {
            flag = true;
            sb.append("backUpOrderId = :backUpOrderId ");
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
        sb.append("order by orderDate");
        Session session = factory.openSession();
        Transaction tx = null;
        List<BackUpOrderEntity> orders = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(sb.toString());
            if (orderNo.trim().length() != 0) {
                query.setParameter("backUpOrderId", Utils.normalBackUpOrderId(orderNo));
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

    public boolean updateBackUpOrder(BackUpOrderEntity backUpOrderEntity, boolean shippedFlag) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (shippedFlag) {
                Set<BackUpOrderDetailEntity> orderDetails = backUpOrderEntity.getOrderDetails();
                Set<Long> itemSet = new HashSet<Long>();
                for (BackUpOrderDetailEntity orderDetail : orderDetails) {
                    long itemId = orderDetail.getItemId();
                    ProductEntity productEntity = session.get(ProductEntity.class, itemId);
                    if (!itemSet.contains(itemId)) {
                        productEntity.setProductionQuantity(productEntity.getProductionQuantity()
                                + orderDetail.getItemTotalQuantity());
                        productEntity.setUpdatedOn(System.currentTimeMillis());
                        itemSet.add(itemId);
                    }
                    for (ProductDetailEntity detailEntity : productEntity.getDetails()) {
                        if (detailEntity.getItemDetailId() == orderDetail.getItemDetailId()) {
                            detailEntity.setProd_xsmall(detailEntity.getProd_xsmall() + orderDetail.getXsmall());
                            detailEntity.setProd_small(detailEntity.getProd_small() + orderDetail.getSmall());
                            detailEntity.setProd_medium(detailEntity.getProd_medium() + orderDetail.getMedium());
                            detailEntity.setProd_large(detailEntity.getProd_large() + orderDetail.getLarge());
                            detailEntity.setProd_xlarge(detailEntity.getProd_xlarge() + orderDetail.getXlarge());
                            detailEntity.setProd_xxlarge(detailEntity.getProd_xxlarge() + orderDetail.getXxlarge());
                            detailEntity.setProd_xxxlarge(detailEntity.getProd_xxxlarge() + orderDetail.getXxxlarge());
                            detailEntity.setProd_mix(detailEntity.getProd_mix() + orderDetail.getMix());
                            detailEntity.setTotal_production(detailEntity.getTotal_production()
                                    + orderDetail.getDetailTotalQuantity());
                            break;
                        }
                    }
                    session.update(productEntity);
                }
            }
            session.update(backUpOrderEntity);
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
}

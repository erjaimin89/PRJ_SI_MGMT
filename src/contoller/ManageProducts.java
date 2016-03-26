package contoller;

import hibernate.HibernateUtil;
import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageProducts {

    private SessionFactory factory;

    public ManageProducts() {
        factory = HibernateUtil.getSessionFactory();
    }

    public boolean addProduct(ProductEntity entity) {

        if (entity != null) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.saveOrUpdate(entity);
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
        }
        return true;
    }

    public boolean updateProduct(ProductEntity entity) {

        if (entity != null) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.saveOrUpdate(entity);
                updateItemNames(session, entity);
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
        }
        return true;
    }

    private void updateItemNames(Session session, ProductEntity entity) {
        Query query1 = session
                .createQuery("UPDATE SalesOrderDetailEntity a SET a.itemName =:itemName WHERE a.itemId =:itemId");
        Query query2 = session
                .createQuery("UPDATE PurchaseOrderDetailEntity a SET a.itemName =:itemName where a.itemId =:itemId");
        Query query3 = session
                .createQuery("UPDATE BackUpOrderDetailEntity a SET a.itemName =:itemName where a.itemId =:itemId");
        Query query4 = session
                .createQuery("UPDATE ThresholdDetailEntity a SET a.itemName =:itemName where a.itemId =:itemId");

        query1.setParameter("itemName", entity.getItemName());
        query1.setParameter("itemId", entity.getItemId());
        query2.setParameter("itemName", entity.getItemName());
        query2.setParameter("itemId", entity.getItemId());
        query3.setParameter("itemName", entity.getItemName());
        query3.setParameter("itemId", entity.getItemId());
        query4.setParameter("itemName", entity.getItemName());
        query4.setParameter("itemId", entity.getItemId());
        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();

        Query query5 = session
                .createQuery("UPDATE SalesOrderDetailEntity a SET a.detailName =:detailName WHERE a.itemDetailId =:itemDetailId");
        Query query6 = session
                .createQuery("UPDATE PurchaseOrderDetailEntity a SET a.detailName =:detailName WHERE a.itemDetailId =:itemDetailId");
        Query query7 = session
                .createQuery("UPDATE BackUpOrderDetailEntity a SET a.detailName =:detailName WHERE a.itemDetailId =:itemDetailId");
        Query query8 = session
                .createQuery("UPDATE ThresholdDetailEntity a SET a.detailName =:detailName WHERE a.itemDetailId =:itemDetailId");

        for (ProductDetailEntity detail : entity.getDetails()) {
            query5.setParameter("detailName", detail.getName());
            query5.setParameter("itemDetailId", detail.getItemDetailId());
            query6.setParameter("detailName", detail.getName());
            query6.setParameter("itemDetailId", detail.getItemDetailId());
            query7.setParameter("detailName", detail.getName());
            query7.setParameter("itemDetailId", detail.getItemDetailId());
            query8.setParameter("detailName", detail.getName());
            query8.setParameter("itemDetailId", detail.getItemDetailId());
            query5.executeUpdate();
            query6.executeUpdate();
            query7.executeUpdate();
            query8.executeUpdate();
        }
    }

    public boolean isProductExists(String itemName, long partyId) {
        List<ProductEntity> products = listProductsByParty(partyId);
        for (ProductEntity product : products) {
            if (product.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List<ProductEntity> listProductsByNameAndParty(String itemName, long partyId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<ProductEntity> products = null;
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createQuery("FROM ProductEntity where itemName like :itemName and partyId =:partyId order by itemStyle");
            query.setParameter("itemName", itemName + "%");
            query.setParameter("partyId", partyId);
            products = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public List<ProductEntity> listAllProducts() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<ProductEntity> products = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ProductEntity order by partyName, itemName, itemStyle");
            products = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public List<ProductEntity> listProductsByParty(long partyId) {
        Session session = factory.openSession();
        Transaction tx = null;
        List<ProductEntity> products = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ProductEntity where partyId =:partyId order by itemName");
            query.setParameter("partyId", partyId);
            products = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    public ProductEntity fetchProductEntity(long key) {
        Session session = factory.openSession();
        Transaction tx = null;
        ProductEntity item = null;
        try {
            tx = session.beginTransaction();
            item = session.get(ProductEntity.class, key);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    public ProductDetailEntity fetchProductDetailEntity(long key) {
        Session session = factory.openSession();
        Transaction tx = null;
        ProductDetailEntity item = null;
        try {
            tx = session.beginTransaction();
            item = session.get(ProductDetailEntity.class, key);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @SuppressWarnings("unchecked")
    public List<ProductEntity> listProductsBySearch(String itemName, String style, long partyId) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        sb.append("FROM ProductEntity WHERE ");
        if (itemName.trim().length() != 0) {
            flag = true;
            sb.append("itemName like :itemName ");
        }
        if (style.trim().length() != 0) {
            if (flag) {
                sb.append(" and ");
            }
            sb.append("itemStyle like :itemStyle ");
            flag = true;
        }
        if (partyId > 0) {
            if (flag) {
                sb.append(" and ");
            }
            sb.append("partyId = :partyId ");
            flag = true;
        }
        if (!flag) {
            sb.append(" 1=1 ");
        }
        sb.append("order by partyName, itemName, itemStyle");
        Session session = factory.openSession();
        Transaction tx = null;
        List<ProductEntity> products = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(sb.toString());
            if (itemName.trim().length() != 0) {
                query.setParameter("itemName", itemName + "%");
            }
            if (style.trim().length() != 0) {
                query.setParameter("itemStyle", style + "%");
            }
            if (partyId > 0) {
                query.setParameter("partyId", partyId);
            }
            products = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<String> listStyles() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<String> list = new ArrayList<String>();
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("SELECT distinct style FROM PRODUCT order by style");
            List data = query.list();
            Iterator iterator = data.iterator();
            while (iterator.hasNext()) {
                list.add((String) iterator.next());
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

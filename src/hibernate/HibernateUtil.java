package hibernate;

import gui.LoadingDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
    	LoadingDialog loadingDialog = new LoadingDialog("Loading...Please Wait...", true);
        try {
            // Create the SessionFactory from hibernate.cfg.xml
//            Configuration  configuration = new Configuration().configure( "C:\\Users\\Nikolay_Tkachev\\workspace\\hiberTest\\src\\logic\\hibernate.cfg.xml");
        	SessionFactory buildSessionFactory = new Configuration().configure().buildSessionFactory();
            loadingDialog.hideDialog();
            return buildSessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
//            new LoadingDialog("Start Database first..", true);
            JOptionPane.showMessageDialog(new JFrame(), "Database not reachable!",
					"Error", JOptionPane.ERROR_MESSAGE);
            loadingDialog.hideDialog();
            System.exit(0);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}

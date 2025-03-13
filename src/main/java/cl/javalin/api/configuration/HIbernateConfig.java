package cl.javalin.api.configuration;

import java.util.Objects;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

import cl.javalin.api.model.CursoModel;

public class HIbernateConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HIbernateConfig.class);
    public static Configuration config(){
        var configuration = new Configuration();
        var settings = new Properties();

        settings.put(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/javalinpg");
        //settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://postgres:5432/javalinpg");
        settings.put(AvailableSettings.JAKARTA_JDBC_USER, "postgres");
        settings.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, "pass123");
        

        settings.put(AvailableSettings.HIGHLIGHT_SQL,true);

        //settings.put(AvailableSettings.HBM2DDL_AUTO, Action.ACTION_CREATE);

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(CursoModel.class);

        System.out.println("settings="+settings);
        return configuration;
    }

    

    private static SessionFactory sf;

    public static SessionFactory getSession(){
        if(Objects.isNull(sf)){
            try {
                var configuration = HIbernateConfig.config();
                var serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

                sf = configuration.buildSessionFactory(serviceRegistry);
                //sf = new Configuration().configure().buildSessionFactory();
            } catch (Throwable e) {
                logger.error("falla al crear session", e);;
            }
        }
            return sf;
    }

}

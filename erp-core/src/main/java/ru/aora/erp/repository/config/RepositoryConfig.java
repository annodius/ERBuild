package ru.aora.erp.repository.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "ru.aora.erp.repository",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)
@ComponentScan("ru.aora.erp.repository.jpa")
@EnableTransactionManagement
public class RepositoryConfig {

    private static final String[] BASE_PACKAGES_TO_ENTITY_SCAN = new String[]{
            "ru.aora.erp.model.entity.db",
            "ru.aora.erp.model.entity.db.authority",
            "ru.aora.erp.repository.jpa.test"
    };
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driverClassName";
    private static final String JDBC_USER = "jdbc.user";
    private static final String JDBC_PASS = "jdbc.pass";
    private static final String USER_JDBC_URL = "users.jdbc.url";

    private final Environment env;

    @Autowired
    public RepositoryConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager() throws URISyntaxException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(this.jpaPropertyMap());
        em.setPackagesToScan(BASE_PACKAGES_TO_ENTITY_SCAN);
        em.setDataSource(this.postgresDataSource());
        return em;
    }

//    @Bean
//    @Primary
//    public DataSource userDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUsername(env.getProperty(JDBC_USER));
//        dataSource.setPassword(env.getProperty(JDBC_PASS));
//        dataSource.setUrl(env.getProperty(USER_JDBC_URL));
//        dataSource.setDriverClassName(
//                Objects.requireNonNull(env.getProperty(JDBC_DRIVER_CLASS_NAME))
//        );
//        return dataSource;
//    }

    @Bean(name = "postgres-db")
    public DataSource postgresDataSource() throws URISyntaxException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require");
        return dataSource;
    }

    @Bean(name = "postgres-db")
    public DataSource postgresDataSource() throws URISyntaxException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require");
        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager userTransactionManager() throws URISyntaxException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.userEntityManager().getObject());
        return transactionManager;
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate() throws URISyntaxException {
        return new JdbcTemplate(postgresDataSource());
    }

    private Map<String, Object> jpaPropertyMap() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(
                HIBERNATE_HBM2DDL_AUTO,
                env.getProperty(HIBERNATE_HBM2DDL_AUTO)
        );
        properties.put(
                HIBERNATE_DIALECT,
                env.getProperty(HIBERNATE_DIALECT)
        );
        return properties;
    }
}

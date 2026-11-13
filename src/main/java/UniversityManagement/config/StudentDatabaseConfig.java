package UniversityManagement.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "UniversityManagement.student.repository",
    entityManagerFactoryRef = "studentEntityManagerFactory",
    transactionManagerRef = "studentTransactionManager"
)
public class StudentDatabaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.datasource.student")
    public DataSource studentDataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("studentDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("UniversityManagement.student.entity")
                .persistenceUnit("student")
                .properties(java.util.Map.of(
                    "hibernate.hbm2ddl.auto", "update"
                ))
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager studentTransactionManager(
            @Qualifier("studentEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
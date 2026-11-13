package UniversityManagement.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "UniversityManagement.grade.repository",
    entityManagerFactoryRef = "gradeEntityManagerFactory",
    transactionManagerRef = "gradeTransactionManager"
)
public class GradeDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.grade")
    public DataSource gradeDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean gradeEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("gradeDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("UniversityManagement.grade.entity")
                .persistenceUnit("grade")
                .properties(java.util.Map.of(
                    "hibernate.hbm2ddl.auto", "update"
                ))
                .build();
    }

    @Bean
    public PlatformTransactionManager gradeTransactionManager(
            @Qualifier("gradeEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
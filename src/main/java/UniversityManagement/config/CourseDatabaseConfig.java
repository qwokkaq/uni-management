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
    basePackages = "UniversityManagement.course.repository",
    entityManagerFactoryRef = "courseEntityManagerFactory",
    transactionManagerRef = "courseTransactionManager"
)
public class CourseDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.course")
    public DataSource courseDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean courseEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("courseDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("UniversityManagement.course.entity")
                .persistenceUnit("course")
                .properties(java.util.Map.of(
                    "hibernate.hbm2ddl.auto", "update"
                ))
                .build();
    }

    @Bean
    public PlatformTransactionManager courseTransactionManager(
            @Qualifier("courseEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
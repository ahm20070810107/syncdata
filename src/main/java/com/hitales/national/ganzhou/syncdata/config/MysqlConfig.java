package com.hitales.national.ganzhou.syncdata.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
	entityManagerFactoryRef = "jpaEntityManagerFactory",
	transactionManagerRef = "jpaTransactionManager",
	basePackages = MysqlConfig.basePackages
)
@ComponentScan(MysqlConfig.basePackages)
@Data
public class MysqlConfig {
    public static final String basePackages = "com.hitales.national.ganzhou.syncdata";
	@Value("${hitales.national.ganzhou.mysql.show-sql}")
	private String showSql;

	@Value("${hitales.national.ganzhou.mysql.ddl-auto}")
	private String ddlAuto;

	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties(prefix = "hitales.national.ganzhou.mysql")
	public HikariDataSource dataSource() {
		return new HikariDataSource();
	}

	@Bean(name = "mysqlTransactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "jpaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(@Qualifier("mysqlDataSource") DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);

		em.setPersistenceUnitName("NATIONAL-GANZHOU");
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setPackagesToScan(MysqlConfig.basePackages);
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", ddlAuto);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.physical_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		em.setJpaPropertyMap(properties);
		return em;
	}


	@Bean(name = "jpaTransactionManager")
	@Primary
	public PlatformTransactionManager jpaTransactionManager( @Qualifier("jpaEntityManagerFactory")  EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

//	@Bean
//	public ModelMapper modelMapper() {
//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
//		modelMapper.getConfiguration().setAmbiguityIgnored(false);
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//		return modelMapper;
//	}
	@Bean
	public ObjectMapper objectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		return objectMapper;
	}

	@Bean()
	RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(20 * 1000);
		httpRequestFactory.setReadTimeout(20 * 1000);
		return new RestTemplate(httpRequestFactory);
	}
}

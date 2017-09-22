package pl.exorigo.upos.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("pl.exorigo.upos")
@PropertySource(value = "classpath:database.properties")
public class SpringConfig {

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.driver.url}")
    private String jdbcDriverUrl;

    @Value("${database.host.ip}")
    private String hostIp;

    @Value("${database.host.port}")
    private String hostPort;

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${database.initial-size}")
    private int initialSize;

    @Value("${database.max-active}")
    private int maxActive;

    @Value("${database.use-ssl}")
    private String useSsl;

    @Value("${database.use-unicode}")
    private String useUnicode;

    @Value("${database.character-encoding}")
    private String characterEncoding;

    @Value("${database.use-jdbc-compliant-timezone-shift}")
    private boolean useJdbcCompliantTimezoneShift;

    @Value("${database.use-legacy-datetime-code}")
    private boolean useLegacyDatetimeCode;

    @Value("${database.server-timezone}")
    private String serverTimezone;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.jdbcDriver);
        dataSource.setUrl(getDataSourceUrl());
        dataSource.setConnectionProperties(createDatabaseConnectionProperties());
        dataSource.setUsername(this.databaseUsername);
        dataSource.setPassword(this.databasePassword);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMaxActive(this.maxActive);
        return dataSource;
    }
    private String getDataSourceUrl() {
        return this.jdbcDriverUrl + "://" + this.hostIp + ":" + this.hostPort + "/" + this.databaseName;
    }
    private String createDatabaseConnectionProperties() {
        return "useSSL=" + this.useSsl + ";"
                + "useUnicode=" + this.useUnicode + ";"
                + "characterEncoding=" + this.characterEncoding + ";"
                + "useJDBCCompliantTimezoneShift=" + this.useJdbcCompliantTimezoneShift + ";"
                + "useLegacyDatetimeCode=" + this.useLegacyDatetimeCode + ";"
                + "serverTimezone=" + this.serverTimezone;
    }

}
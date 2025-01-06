package com.phenricks.finity.configuration.database

import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.metrics.micrometer.MicrometerMetricsTrackerFactory
import com.phenricks.finity.configuration.database.JdbcProperties.JdbcDataSourceProperties
import io.micrometer.core.instrument.MeterRegistry
import org.postgresql.Driver
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class JdbcConfiguration(
    private val jdbcProperties: JdbcProperties,
    private val meterRegistry: MeterRegistry) {

    @Bean
    @Primary
    @Qualifier("rwDataSource")
    fun rwDataSource(): DataSource {
        return createDataSource(jdbcProperties.rw)
    }

    @Bean
    @Qualifier("roDataSource")
    fun roDataSource(): DataSource {
        return createDataSource(jdbcProperties.ro)
    }

    @Bean
    @Primary
    @Qualifier("rwDataSource")
    fun rwJdbcTemplate(@Qualifier("rwDataSource") dataSource: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    @Qualifier("roJdbcTemplate")
    fun roJdbcTemplate(@Qualifier("roDataSource") dataSource: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }

    private fun createDataSource(dataSourceProperties: JdbcDataSourceProperties): HikariDataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = Driver::class.java.name
        dataSource.jdbcUrl = dataSourceProperties.url
        dataSource.username = jdbcProperties.username
        dataSource.password = jdbcProperties.password
        dataSource.maximumPoolSize = dataSourceProperties.maxPoolSize
        dataSource.maxLifetime = dataSourceProperties.maxLifetimeInMinutes
        dataSource.leakDetectionThreshold = dataSourceProperties.leakDetectionThresholdInMilliseconds
        dataSource.connectionTimeout = dataSourceProperties.connectionTimeoutInMilliseconds
        dataSource.connectionInitSql = jdbcProperties.initSql
        dataSource.metricsTrackerFactory = MicrometerMetricsTrackerFactory(meterRegistry)
        return dataSource
    }
}

package com.phenricks.finity.configuration.database

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jdbc")
class JdbcProperties {
    var username: String = ""
    var password: String = ""
    var initSql: String = ""


    var rw : JdbcDataSourceProperties = JdbcDataSourceProperties()
    var ro: JdbcDataSourceProperties = JdbcDataSourceProperties()

    class JdbcDataSourceProperties {

        var url: String = ""
        var maxPoolSize: Int = 0
        var maxLifetimeInMinutes: Long = 0
        var leakDetectionThresholdInMilliseconds: Long = 0
        var connectionTimeoutInMilliseconds: Long = 0
    }
}
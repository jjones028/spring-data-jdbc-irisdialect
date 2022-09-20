package io.spektacle.spring.data.jdbc

import org.springframework.data.jdbc.repository.config.DialectResolver
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.jdbc.core.ConnectionCallback
import org.springframework.jdbc.core.JdbcOperations
import java.sql.Connection
import java.util.*

class IRISDialectResolver : DialectResolver.JdbcDialectProvider {
    override fun getDialect(operations: JdbcOperations): Optional<Dialect> {
        return Optional.ofNullable(
            operations.execute((ConnectionCallback<Dialect>(IRISDialectResolver::getDialect)) ))
    }

    companion object {
        private fun getDialect(connection: Connection) : IRISDialect? {
            val metadata = connection.metaData
            val name = metadata.databaseProductName.lowercase(Locale.ROOT)
            if (name.contains("intersystems iris")) {
                return IRISDialect.INSTANCE
            }
            return null
        }
    }
}
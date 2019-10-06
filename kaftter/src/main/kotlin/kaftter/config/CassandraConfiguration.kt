package kaftter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@EnableCassandraRepositories
class CassandraConfiguration: AbstractCassandraConfiguration {
    private val contactPoints: String
    private val port: Int
    private val keyspace: String
    private val basePackages: String

    constructor(@Value("\${cassandra.contactPoints}") contactPoints: String,
                @Value("\${cassandra.port}") port: Int,
                @Value("\${cassandra.keyspace}") keyspace: String,
                @Value("\${cassandra.basePackages") basePackages: String) {
        this.port = port
        this.contactPoints = contactPoints
        this.keyspace = keyspace
        this.basePackages = basePackages
    }

    override fun getPort(): Int {
        return port
    }

    override fun getKeyspaceName(): String {
        return keyspace
    }

    override fun getContactPoints(): String {
        return contactPoints
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getEntityBasePackages(): Array<String> {
        return arrayOf(basePackages)
    }
}
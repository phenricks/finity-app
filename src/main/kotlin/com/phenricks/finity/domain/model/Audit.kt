package com.phenricks.finity.domain.model

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.UUID

data class Audit(
    val uuid: UUID,
    val action: String,
    val email: String,
    val dataBefore: String,
    val dataAfter: String,
    val relatedEntity: String,
    val relatedEntityUuid: UUID,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
) {
    companion object {
        val AuditRowMapper = RowMapper<Audit> { rs: ResultSet, _: Int ->
            Audit(
                uuid = UUID.fromString(rs.getString(AuditTable.UUID)),
                action = rs.getString(AuditTable.ACTION),
                email = rs.getString(AuditTable.EMAIL),
                dataBefore = rs.getString(AuditTable.DATA_BEFORE),
                dataAfter = rs.getString(AuditTable.DATA_AFTER),
                relatedEntity = rs.getString(AuditTable.RELATED_ENTITY),
                relatedEntityUuid = UUID.fromString(rs.getString(AuditTable.RELATED_ENTITY_UUID)),
                createdAt = rs.getTimestamp(AuditTable.CREATED_AT),
                updatedAt = rs.getTimestamp(AuditTable.UPDATED_AT)
            )
        }
    }
}

class AuditTable() {
    companion object {
        const val TABLE_NAME = "audit"
        const val UUID = "uuid"
        const val ACTION = "action"
        const val EMAIL = "email"
        const val DATA_BEFORE = "data_before"
        const val DATA_AFTER = "data_after"
        const val RELATED_ENTITY = "related_entity"
        const val RELATED_ENTITY_UUID = "related_entity_uuid"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }

    init {
        throw UnsupportedOperationException("This class can't be instantiated")
    }
}

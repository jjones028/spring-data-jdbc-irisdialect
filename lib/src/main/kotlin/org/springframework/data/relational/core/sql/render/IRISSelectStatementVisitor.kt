package org.springframework.data.relational.core.sql.render

import io.spektacle.spring.data.jdbc.IRISSelectRenderContext
import org.springframework.data.relational.core.sql.Select
import org.springframework.data.relational.core.sql.Visitable

internal class IRISSelectStatementVisitor(context: RenderContext) : SelectStatementVisitor(context) {

    private val selectRenderContext: IRISSelectRenderContext? = null

    private val builder = StringBuilder()
    private val selectList = StringBuilder()
    private val from = StringBuilder()
    private val join = StringBuilder()
    private val where = StringBuilder()

    private val orderByClauseVisitor: OrderByClauseVisitor? = null
    override fun doLeave(segment: Visitable): Delegation {
        if (segment is Select) {
            builder.append("SELECT ")
            builder.append(selectRenderContext?.afterSelect()?.apply(segment))
            if (segment.isDistinct) {
                builder.append("DISTINCT ")
            }
            builder.append(selectList)
            builder.append(selectRenderContext?.afterSelectList()?.apply(segment) ?: "")
            if (from.isNotEmpty()) {
                builder.append(" FROM ").append(from)
            }
            builder.append(selectRenderContext?.afterFromTable()?.apply(segment) ?: "")
            if (join.isNotEmpty()) {
                builder.append(' ').append(join)
            }
            if (where.isNotEmpty()) {
                builder.append(" WHERE ").append(where)
            }
            val orderBy = orderByClauseVisitor?.renderedPart
            if (orderBy?.length != 0) {
                builder.append(" ORDER BY ").append(orderBy)
            }
            builder.append(selectRenderContext?.afterOrderBy(orderBy?.length != 0)?.apply(segment))
            return Delegation.leave()
        }

        return Delegation.retain()
    }
}

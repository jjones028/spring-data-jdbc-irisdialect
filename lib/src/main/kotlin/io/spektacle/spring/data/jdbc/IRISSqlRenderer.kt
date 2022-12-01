package io.spektacle.spring.data.jdbc

import org.springframework.data.relational.core.sql.Delete
import org.springframework.data.relational.core.sql.Insert
import org.springframework.data.relational.core.sql.Select
import org.springframework.data.relational.core.sql.Update
import org.springframework.data.relational.core.sql.render.IRISSelectStatementVisitor
import org.springframework.data.relational.core.sql.render.RenderContext
import org.springframework.data.relational.core.sql.render.Renderer
import org.springframework.data.relational.core.sql.render.SqlRenderer


class IRISSqlRenderer(private val context: RenderContext): Renderer {

    private val sqlRenderer: SqlRenderer = SqlRenderer.create(context)
    override fun render(select: Select): String {
        val visitor = IRISSelectStatementVisitor(context)
        select.visit(visitor)

        return visitor.renderedPart.toString()
    }

    override fun render(insert: Insert): String = sqlRenderer.render(insert)

    override fun render(update: Update): String = sqlRenderer.render(update)

    override fun render(delete: Delete): String = sqlRenderer.render(delete)
}
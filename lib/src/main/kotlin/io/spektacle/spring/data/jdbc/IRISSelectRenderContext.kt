package io.spektacle.spring.data.jdbc

import org.springframework.data.relational.core.sql.Select
import org.springframework.data.relational.core.sql.render.SelectRenderContext
import java.util.function.Function

class IRISSelectRenderContext(
    afterFromTable: Function<Select, CharSequence>,
    afterOrderBy: Function<Select, CharSequence>
) : SelectRenderContext {

    override fun afterOrderBy(hasOrderBy: Boolean): Function<Select, out CharSequence> {
        return Function<Select, CharSequence> {
            return@Function ""
        }
    }
    fun afterSelect(): Function<Select, out CharSequence> {
        return Function<Select, CharSequence> { select: Select ->
            val limit = select.limit
            val offset = select.offset
            if (usesPagination(select)) {
                if (limit.isPresent && offset.isPresent) {
                    return@Function String.format(
                        "%ROWOFFSET %d %ROWLIMIT %d", offset.asLong, limit.asLong
                    )
                }
                if (limit.isPresent) {
                    return@Function String.format("%ROWLIMIT %d", limit.asLong)
                }
                if (offset.isPresent) {
                    return@Function String.format("%ROWOFFSET %d", offset.asLong)
                }
            }
            return@Function ""
        }
    }
    private fun usesPagination(select: Select): Boolean {
        return select.offset.isPresent || select.limit.isPresent
    }
}
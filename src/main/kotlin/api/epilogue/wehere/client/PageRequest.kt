package api.epilogue.wehere.client

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class PageRequest(
    val page: Int?,
    val size: Int?
) {
    fun toPageable() = PageRequest.of(page ?: 0, size ?: 10)
    fun toPageable(order: Sort.Order) = toPageable().withSort(Sort.by(order))
}
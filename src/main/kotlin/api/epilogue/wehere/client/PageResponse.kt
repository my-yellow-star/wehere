package api.epilogue.wehere.client

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val total: Long,
    val items: List<T>,
    val nextPage: Int? = null
) {
    companion object {
        fun <I, O> of(result: Page<I>, mapContent: (I) -> O) =
            PageResponse(
                result.totalElements,
                result.content.map(mapContent),
                takeIf {
                    result.hasNext()
                }?.let {
                    result.nextPageable().pageNumber
                }
            )
    }
}
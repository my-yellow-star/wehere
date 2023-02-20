package api.epilogue.wehere.kernel

object RegexUtils {
    fun containsKorean(value: String) =
        value.matches(Regex(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"))
}
package api.epilogue.wehere.member.domain

object NicknameFactory {
    fun random() = "${adjectives.random()} ${nouns.random()}"

    private val adjectives = listOf(
        "행복한",
        "다정한",
        "용감한",
        "빛나는",
        "성실한",
        "열정적인",
        "도전적인",
        "쾌활한",
        "개방적인",
        "낙천적인",
        "즐거운",
        "솔직한",
        "지혜로운",
        "우아한",
        "나긋한",
        "진실한",
        "사색적인",
        "감성적인",
        "공정한",
        "꼼꼼한",
        "유머러스한",
        "가을타는"
    )
    private val nouns = listOf(
        "사과나무",
        "귤나무",
        "소나무",
        "은행나무",
        "단풍나무",
        "자작나무",
        "모험가",
        "나그네",
        "방랑자",
        "여행가",
        "돛단배",
        "호수",
        "햇님",
        "달님",
        "별님",
        "청새치",
        "돌고래",
        "코끼리",
        "표범",
        "고양이",
        "강아지",
        "와이셔츠",
        "원피스",
        "청바지",
        "음유시인"
    )
}
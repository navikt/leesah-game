package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class Grunnbeløp(active: Boolean = false, maxCount: Int = 1, interval: Duration) :
    QuestionCategory("grunnbelop", active = active, maxCount = maxCount, interval = interval) {

    val fasit = mutableMapOf<String, Int>()

    override fun check(answer: Answer) {
        (fasit[answer.questionId] == answer.answer.toInt()).publish(
            teamName = answer.teamName,
            questionId = answer.questionId,
            answerId = answer.id()
        )
    }

    override fun newQuestions(): List<Question> {
        val year = Random.nextInt(1967, 2023)
        val month = Random.nextInt(1, 13)
        val day = Random.nextInt(1, 28)
        val questionDate = LocalDate.of(year, month, day)
        val question = Question(category=category, question = "grunnbelop på dato: ${questionDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
        storeFasit(question, grunnbeløpFor(questionDate)!!)
        return listOf(question)
    }

    private fun storeFasit(question: Question, answer: Int) {
        fasit[question.id()] = answer
    }

    override fun sync(question: Question): Boolean {
        val date = questionDate(question) ?: return false
        val answer = grunnbeløpFor(date) ?: return false
        storeFasit(question, answer)
        return true
    }

    private fun questionDate(question: Question): LocalDate? =
        LocalDate.parse(question.question.split(":")[1].removePrefix(" "))

    fun grunnbeløpFor(date: LocalDate): Int? {
        return grunnbeløp.firstOrNull { date.isEqual(it.second) || date.isAfter(it.second) }?.first
    }

    private val grunnbeløp = listOf(
        Pair(111477, 1.mai(2022)),
        Pair(106399, 1.mai(2021)),
        Pair(101351, 1.mai(2020)),
        Pair(99858, 1.mai(2019)),
        Pair(96883, 1.mai(2018)),
        Pair(93634, 1.mai(2017)),
        Pair(92576, 1.mai(2016)),
        Pair(90068, 1.mai(2015)),
        Pair(88370, 1.mai(2014)),
        Pair(85245, 1.mai(2013)),
        Pair(82122, 1.mai(2012)),
        Pair(79216, 1.mai(2011)),
        Pair(75641, 1.mai(2010)),
        Pair(72881, 1.mai(2009)),
        Pair(70256, 1.mai(2008)),
        Pair(66812, 1.mai(2007)),
        Pair(62892, 1.mai(2006)),
        Pair(60699, 1.mai(2005)),
        Pair(58778, 1.mai(2004)),
        Pair(56861, 1.mai(2003)),
        Pair(54170, 1.mai(2002)),
        Pair(51360, 1.mai(2001)),
        Pair(49090, 1.mai(2000)),
        Pair(46950, 1.mai(1999)),
        Pair(45370, 1.mai(1998)),
        Pair(42500, 1.mai(1997)),
        Pair(41000, 1.mai(1996)),
        Pair(39230, 1.mai(1995)),
        Pair(38080, 1.mai(1994)),
        Pair(37300, 1.mai(1993)),
        Pair(36500, 1.mai(1992)),
        Pair(35500, 1.mai(1991)),
        Pair(34100, 1.desember(1990)),
        Pair(34000, 1.mai(1990)),
        Pair(32700, 1.april(1989)),
        Pair(31000, 1.april(1988)),
        Pair(30400, 1.januar(1988)),
        Pair(29900, 1.mai(1987)),
        Pair(28000, 1.mai(1986)),
        Pair(26300, 1.januar(1986)),
        Pair(25900, 1.mai(1985)),
        Pair(24200, 1.mai(1984)),
        Pair(22600, 1.mai(1983)),
        Pair(21800, 1.januar(1983)),
        Pair(21200, 1.mai(1982)),
        Pair(19600, 1.oktober(1981)),
        Pair(19100, 1.mai(1981)),
        Pair(17400, 1.januar(1981)),
        Pair(16900, 1.mai(1980)),
        Pair(16100, 1.januar(1980)),
        Pair(15200, 1.januar(1979)),
        Pair(14700, 1.juli(1978)),
        Pair(14400, 1.desember(1977)),
        Pair(13400, 1.mai(1977)),
        Pair(13100, 1.januar(1977)),
        Pair(12100, 1.mai(1976)),
        Pair(11800, 1.januar(1976)),
        Pair(11000, 1.mai(1975)),
        Pair(10400, 1.januar(1975)),
        Pair(9700, 1.mai(1974)),
        Pair(9200, 1.januar(1974)),
        Pair(8500, 1.januar(1973)),
        Pair(7900, 1.januar(1972)),
        Pair(7500, 1.mai(1971)),
        Pair(7200, 1.januar(1971)),
        Pair(6800, 1.januar(1970)),
        Pair(6400, 1.januar(1969)),
        Pair(5900, 1.januar(1968)),
        Pair(5400, 1.januar(1967))
    )
}

fun Int.januar(year: Int): LocalDate = LocalDate.of(year, 1, this)
fun Int.februar(year: Int): LocalDate = LocalDate.of(year, 2, this)
fun Int.mars(year: Int): LocalDate = LocalDate.of(year, 3, this)
fun Int.april(year: Int): LocalDate = LocalDate.of(year, 4, this)
fun Int.mai(year: Int): LocalDate = LocalDate.of(year, 5, this)
fun Int.juni(year: Int): LocalDate = LocalDate.of(year, 6, this)
fun Int.juli(year: Int): LocalDate = LocalDate.of(year, 7, this)
fun Int.august(year: Int): LocalDate = LocalDate.of(year, 8, this)
fun Int.september(year: Int): LocalDate = LocalDate.of(year, 9, this)
fun Int.oktober(year: Int): LocalDate = LocalDate.of(year, 10, this)
fun Int.november(year: Int): LocalDate = LocalDate.of(year, 11, this)
fun Int.desember(year: Int): LocalDate = LocalDate.of(year, 12, this)
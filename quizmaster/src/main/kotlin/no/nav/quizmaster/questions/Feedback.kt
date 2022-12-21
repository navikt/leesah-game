import no.nav.quizmaster.questions.QuestionCategory
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class Feedback(maxCount: Int = 1, active: Boolean = false) : QuestionCategory("feedback", maxCount, active) {
    override val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)
    override fun check(answer: Answer) {
        if (answer.answer.isEmpty()) {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
        } else {
            answer.answer
                .isNotEmpty()
                .publish(answer.teamName, answer.questionId, answer.messageId)
        }
    }

    override fun newQuestions(): List<Question> {
        return if (active) {
            val question = Question(
                category = category,
                question = "What do you think about the game so far? Please give us feedback."
            )
            listOf(question)
        } else emptyList()
    }

    override fun sync(question: Question) = true
}
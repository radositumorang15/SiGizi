package com.example.sigizi.view.konsultasi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sigizi.R
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.NavigableOrderedTask
import com.quickbirdstudios.surveykit.NavigationRule
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ActivityKonsultasi : AppCompatActivity() {


    private lateinit var survey: SurveyView
    private lateinit var container: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsultasi)

        survey = findViewById(R.id.survey_view)
        container = findViewById(R.id.surveyContainer)
        setupSurvey(survey)
    }

    private fun setupSurvey(surveyView: SurveyView) {
        val steps = listOf(
            InstructionStep(
                title = getString(R.string.intro_title),
                text = getString(R.string.intro_text),
                buttonText = getString(R.string.intro_start)
            ),
            QuestionStep(
                title = getString(R.string.app_name),
                text = getString(R.string.pertanyaan2),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(getString(R.string.yes)),
                        TextChoice(getString(R.string.no))
                    )
                )
            ),
            QuestionStep(
                title = getString(R.string.app_name),
                text = getString(R.string.pertanyaan3),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(getString(R.string.yes)),
                        TextChoice(getString(R.string.no))
                    )
                )
            ),
            QuestionStep(
                title = getString(R.string.app_name),
                text = getString(R.string.pertanyaan4),
                answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(getString(R.string.makanan1)),
                        TextChoice(getString(R.string.makanan2)),
                        TextChoice(getString(R.string.makanan3)),
                        TextChoice(getString(R.string.makanan4))
                    )
                )
            ),
            QuestionStep(
                title = getString(R.string.app_name),
                text = getString(R.string.pertanyaan5),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(getString(R.string.yes)),
                        TextChoice(getString(R.string.no))
                    )
                )
            ),
            QuestionStep(
                title = getString(R.string.app_name),
                text = getString(R.string.pertanyaan5),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(getString(R.string.sering)),
                        TextChoice(getString(R.string.sering1)),
                        TextChoice(getString(R.string.sering2)),
                        TextChoice(getString(R.string.sering3))
                    )
                )
            ),
            CompletionStep(
                title = getString(R.string.finish_question_title),
                text = getString(R.string.finish_question_text),
                buttonText = getString(R.string.finish_question_submit)
            )
        )

        val task = NavigableOrderedTask(steps = steps)

        task.setNavigationRule(
            steps[5].id,
            NavigationRule.DirectStepNavigationRule(
                destinationStepStepIdentifier = steps[6].id
            )
        )

        task.setNavigationRule(
            steps[6].id,
            NavigationRule.ConditionalDirectionStepNavigationRule(
                resultToStepIdentifierMapper = { input ->
                    when (input) {
                        "Ja" -> steps[6].id
                        "Nein" -> steps[0].id
                        else -> null
                    }
                }
            )
        )




        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                // Lakukan sesuatu dengan hasil survei jika diperlukan
                // Misalnya, iterasi melalui hasil survei

                // Mulai activity baru setelah survei selesai
                val intent = Intent(this, ActivityKonsultasiData::class.java)
                startActivity(intent)

                // Hapus tampilan yang ada jika perlu
                container.removeAllViews()
            }
        }



        val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.secondprimary),
            themeColor = ContextCompat.getColor(this, R.color.navi),
            textColor = ContextCompat.getColor(this, R.color.navi),
        )


        surveyView.start(task, configuration)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            survey.backPressed()
            true
        } else false
    }
}
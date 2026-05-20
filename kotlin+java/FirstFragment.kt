package ua.com.ohmy.littlemulti

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import ua.com.ohmy.littlemulti.databinding.FragmentFirstBinding
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var leitnerSystem: LeitnerSystem
    private var currentQuestionIndex = 0
    private val totalQuestions = 30
    private var score = 0
    private var streak = 0
    private var selectedValue: Int? = null
    
    private lateinit var questions: List<MultiplicationTask>
    private lateinit var currentTask: MultiplicationTask

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leitnerSystem = LeitnerSystem(requireContext())
        
        setupNumpad()
        setupButtons()
        startNewGame()
        startMascotAnimations()
        startSkyAnimations()
        startPulseAnimation()
    }

    private fun startPulseAnimation() {
        val pulse = AnimationUtils.loadAnimation(context, R.anim.pulse)
        binding.selectedAnswer.startAnimation(pulse)
    }

    private fun startMascotAnimations() {
        val floatAnim = AnimationUtils.loadAnimation(context, R.anim.float_animation)
        binding.drakoContainer.startAnimation(floatAnim)
        
        Handler(Looper.getMainLooper()).postDelayed({
            val floatAnimOwl = AnimationUtils.loadAnimation(context, R.anim.float_animation)
            binding.owlSvg.startAnimation(floatAnimOwl)
        }, 1200)
    }

    private fun startSkyAnimations() {
        ObjectAnimator.ofFloat(binding.cloud1, "translationX", 0f, 30f, 0f).apply {
            duration = 9000
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
        ObjectAnimator.ofFloat(binding.cloud3, "translationX", 0f, -40f, 0f).apply {
            duration = 11000
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }

    private fun startNewGame() {
        currentQuestionIndex = 0
        score = 0
        streak = 0
        questions = leitnerSystem.getNextQuestions(totalQuestions)
        binding.victoryScreen.visibility = View.GONE
        updateStatsUI()
        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        if (currentQuestionIndex >= totalQuestions) {
            showVictory()
            return
        }

        currentTask = questions[currentQuestionIndex]
        selectedValue = null
        
        binding.num1.text = currentTask.a.toString()
        binding.num2.text = currentTask.b.toString()
        binding.selectedAnswer.text = "?"
        
        binding.questionProgressLabel.text = "🎯 Question ${currentQuestionIndex + 1} of $totalQuestions"
        binding.progressLabel.text = "$currentQuestionIndex / $totalQuestions done ✨"
        binding.questionProgressBar.progress = (currentQuestionIndex.toFloat() / totalQuestions * 100).toInt()

        generateOptions()
        
        binding.drakoBubble.text = "You got this! 💪"
        binding.owlBubble.text = "Think carefully! 🦉"
    }

    private fun generateOptions() {
        val correctAnswer = currentTask.result
        val options = mutableSetOf(correctAnswer)
        
        while (options.size < 8) {
            val a = Random.nextInt(2, 10)
            val b = Random.nextInt(2, 10)
            val wrongAns = a * b
            if (wrongAns != correctAnswer) {
                options.add(wrongAns)
            }
        }

        val shuffledOptions = options.toList().shuffled()
        val numButtons = binding.numpad.children.filter { 
            it is AppCompatButton && it.id != R.id.btnClear && it.id != R.id.btnSubmit 
        }.toList()
        
        shuffledOptions.forEachIndexed { index, value ->
            if (index < numButtons.size) {
                val btn = numButtons[index] as AppCompatButton
                btn.text = value.toString()
                btn.setOnClickListener { 
                    animateButtonClick(btn)
                    selectAnswer(value) 
                }
            }
        }
    }

    private fun animateButtonClick(view: View) {
        view.animate()
            .scaleX(0.92f)
            .scaleY(0.92f)
            .translationY(8f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .translationY(0f)
                    .setDuration(100)
                    .start()
            }.start()
    }

    private fun selectAnswer(value: Int) {
        selectedValue = value
        binding.selectedAnswer.text = value.toString()
    }

    private fun setupNumpad() {
        binding.btnClear.setOnClickListener {
            animateButtonClick(it)
            selectedValue = null
            binding.selectedAnswer.text = "?"
            binding.owlBubble.text = "Let's pick again! 🦉"
        }

        binding.btnSubmit.setOnClickListener {
            animateButtonClick(it)
            checkAnswer()
        }
    }

    private fun setupButtons() {
        binding.btnHint.setOnClickListener {
            animateButtonClick(it)
            showHint()
        }
        
        binding.btnPlayAgain.setOnClickListener {
            animateButtonClick(it)
            startNewGame()
        }
    }

    private fun checkAnswer() {
        if (selectedValue == null) {
            binding.owlBubble.text = "Pick an answer first! 👇"
            return
        }

        val isCorrect = selectedValue == currentTask.result
        leitnerSystem.handleAnswer(currentTask, isCorrect)

        if (isCorrect) {
            score += 10 + (streak * 2)
            streak++
            binding.drakoBubble.text = "Correct! Super! 🌟"
            binding.owlBubble.text = "Brilliant! 🦉"
            
            currentQuestionIndex++
            updateStatsUI()
            Handler(Looper.getMainLooper()).postDelayed({
                loadNextQuestion()
            }, 1000)
        } else {
            streak = 0
            updateStatsUI()
            binding.drakoBubble.text = "Oops! Try again! 🐉"
            binding.owlBubble.text = "Almost there! 🦉"
            selectedValue = null
            binding.selectedAnswer.text = "?"
        }
    }

    private fun showHint() {
        val hints = listOf(
            "Hint: it's close to ${currentTask.result + if (Random.nextBoolean()) 2 else -2}!",
            "Answer ends with ${currentTask.result % 10}!",
            "Think of ${currentTask.a} groups of ${currentTask.b} apples!"
        )
        binding.owlBubble.text = hints.random()
        score = (score - 5).coerceAtLeast(0)
        updateStatsUI()
    }

    private fun updateStatsUI() {
        binding.scoreValue.text = score.toString()
        binding.streakValue.text = streak.toString()
    }

    private fun showVictory() {
        binding.victoryScreen.visibility = View.VISIBLE
        binding.victoryStats.text = "Score: $score 🌟"
        binding.victoryTitle.startAnimation(AnimationUtils.loadAnimation(context, R.anim.victory_pop))

        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xFF87CEEB.toInt(), 0xFFFFB3C6.toInt(), 0xFFFFE066.toInt(), 0xFFC3A6E8.toInt()), // Sky, Pink, Yellow, Purple
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        )
        binding.konfettiView.start(party)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package ua.com.ohmy.littlemulti

import android.content.Context
import android.content.SharedPreferences
import kotlin.random.Random

data class MultiplicationTask(val a: Int, val b: Int) {
    val id: String get() = "${a}x${b}"
    val result: Int get() = a * b
}

class LeitnerSystem(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("leitner_prefs", Context.MODE_PRIVATE)
    private val allTasks: List<MultiplicationTask>

    init {
        val tasks = mutableListOf<MultiplicationTask>()
        for (i in 2..9) {
            for (j in 2..9) {
                tasks.add(MultiplicationTask(i, j))
            }
        }
        allTasks = tasks
    }

    private fun getTaskBox(taskId: String): Int {
        return prefs.getInt(taskId, 1)
    }

    private fun setTaskBox(taskId: String, box: Int) {
        prefs.edit().putInt(taskId, box.coerceIn(1, 5)).apply()
    }

    fun getNextQuestions(count: Int): List<MultiplicationTask> {
        val tasksWithBoxes = allTasks.groupBy { getTaskBox(it.id) }
        val result = mutableListOf<MultiplicationTask>()
        
        // Weights for boxes: Box 1 is 5x more likely than Box 5
        // Box 1: 10, Box 2: 8, Box 3: 5, Box 4: 3, Box 5: 1
        val weights = mapOf(1 to 10, 2 to 8, 3 to 5, 4 to 3, 5 to 1)
        
        val weightedPool = mutableListOf<MultiplicationTask>()
        for (box in 1..5) {
            val tasksInBox = tasksWithBoxes[box] ?: emptyList()
            val weight = weights[box] ?: 1
            repeat(weight) {
                weightedPool.addAll(tasksInBox)
            }
        }

        if (weightedPool.isEmpty()) return allTasks.shuffled().take(count)

        repeat(count) {
            val task = weightedPool.random()
            result.add(task)
            // To avoid duplicate in same session (optional)
            weightedPool.removeAll { it == task }
            if (weightedPool.isEmpty()) weightedPool.addAll(allTasks)
        }

        return result
    }

    fun handleAnswer(task: MultiplicationTask, correct: Boolean) {
        val currentBox = getTaskBox(task.id)
        if (correct) {
            setTaskBox(task.id, currentBox + 1)
        } else {
            setTaskBox(task.id, 1)
        }
    }
}

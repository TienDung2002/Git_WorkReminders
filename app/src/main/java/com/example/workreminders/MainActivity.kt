package com.example.workreminders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workreminders.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = WorkAdapter(getDataFromProvider(this))

        // 1 số ràng buộc
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .build()

        val dailyRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork("Daily Reminder", ExistingPeriodicWorkPolicy.KEEP, dailyRequest)
    }

    private fun calculateInitialDelay(): Long {
        val currentTime = Calendar.getInstance()
        val timeSelected = Calendar.getInstance()
        timeSelected.set(Calendar.HOUR_OF_DAY, 6)
        timeSelected.set(Calendar.MINUTE, 0)
        timeSelected.set(Calendar.SECOND, 0)

        // Nếu hiện tại quá 6h sáng thì set cho ngày tiếp theo
        if (currentTime.after(timeSelected)) timeSelected.add(Calendar.DAY_OF_MONTH, 1)

        return timeSelected.timeInMillis - currentTime.timeInMillis
    }
}
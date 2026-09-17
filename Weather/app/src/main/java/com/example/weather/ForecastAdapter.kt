package com.example.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemForecastBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastAdapter(private val items: List<ForecastDayItem>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        
        // Reformat the date yyyy-MM-dd to a readable layout (e.g. Mon, Sep 15)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        val formattedDate = try {
            val date = inputFormat.parse(item.date)
            date?.let { outputFormat.format(it) } ?: item.date
        } catch (e: Exception) {
            item.date
        }

        holder.binding.dateText.text = formattedDate
        holder.binding.maxTempText.text = "Max: ${item.maxTemp.toInt()}°C"
        holder.binding.minTempText.text = "Min: ${item.minTemp.toInt()}°C"
    }

    override fun getItemCount(): Int = items.size
}

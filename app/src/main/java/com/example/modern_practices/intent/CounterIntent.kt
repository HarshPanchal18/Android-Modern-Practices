package com.example.modern_practices.intent

// User's actions or commands that can be sent to the ViewModel
sealed class CounterIntent {
    object Increment : CounterIntent()
}

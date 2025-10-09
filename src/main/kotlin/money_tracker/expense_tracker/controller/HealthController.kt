package money_tracker.expense_tracker.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/health")
    fun health(): Map<String, String> {
        return mapOf(
            "status" to "UP",
            "message" to "Application is running"
        )
    }

    @GetMapping("/")
    fun root(): Map<String, String> {
        return mapOf(
            "message" to "Expense Tracker API",
            "version" to "1.0.0",
            "docs" to "/swagger-ui.html"
        )
    }
}

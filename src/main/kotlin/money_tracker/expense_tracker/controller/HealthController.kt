package money_tracker.expense_tracker.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @GetMapping("/health")
    fun health(): Map<String, String> {
        return mapOf(
            "status" to "UP",
            "message" to "Application is running"
        )
    }

    @GetMapping("/db-test")
    fun dbTest(): Map<String, Any> {
        return try {
            val result: Int? = jdbcTemplate.queryForObject("SELECT 1 as test", Int::class.java)
            mapOf(
                "status" to "SUCCESS",
                "database" to "CONNECTED",
                "test_query" to (result ?: 0)
            )
        } catch (e: Exception) {
            mapOf(
                "status" to "ERROR",
                "database" to "DISCONNECTED",
                "error" to (e.message ?: "Unknown error")
            )
        }
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
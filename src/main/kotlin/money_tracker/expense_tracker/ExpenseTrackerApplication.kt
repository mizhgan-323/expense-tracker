package money_tracker.expense_tracker

import money_tracker.expense_tracker.service.CategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ExpenseTrackerApplication {

	@Bean
	fun initializeData(categoryService: CategoryService): CommandLineRunner {
		return CommandLineRunner {
			println("Инициализация начальных данных...")
			try {
				categoryService.initializeDefaultCategories()
				println("Начальные данные успешно инициализированы!")
			} catch (e: Exception) {
				println("Ошибка при инициализации данных: ${e.message}")
				println("Приложение продолжит работу без инициализации данных")
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<ExpenseTrackerApplication>(*args)
}

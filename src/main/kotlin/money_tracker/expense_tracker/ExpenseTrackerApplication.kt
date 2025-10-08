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
			categoryService.initializeDefaultCategories()
			println("Начальные данные успешно инициализированы!")
		}
	}
}

fun main(args: Array<String>) {
	runApplication<ExpenseTrackerApplication>(*args)
}

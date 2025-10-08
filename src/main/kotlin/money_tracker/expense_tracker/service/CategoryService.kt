package money_tracker.expense_tracker.service

import money_tracker.expense_tracker.dto.CategoryDto
import money_tracker.expense_tracker.dto.CreateCategoryRequest
import money_tracker.expense_tracker.dto.UpdateCategoryRequest
import money_tracker.expense_tracker.entity.Category
import money_tracker.expense_tracker.entity.CategoryType
import money_tracker.expense_tracker.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategories(): List<CategoryDto> {
        return categoryRepository.findAll().map { CategoryDto.fromEntity(it) }
    }

    fun getCategoriesByType(type: CategoryType): List<CategoryDto> {
        return categoryRepository.findByTypeAndIsActiveTrue(type)
            .map { CategoryDto.fromEntity(it) }
    }

    fun getCategoryById(id: Long): CategoryDto? {
        return categoryRepository.findById(id).map { CategoryDto.fromEntity(it) }.orElse(null)
    }

    fun getCategoryEntityById(id: Long): Category? {
        return categoryRepository.findById(id).orElse(null)
    }

    @Transactional
    fun createCategory(request: CreateCategoryRequest): CategoryDto {
        // Проверяем, что категория с таким именем и типом не существует
        if (categoryRepository.existsByNameAndType(request.name, request.type)) {
            throw IllegalArgumentException("Категория '${request.name}' для типа '${request.type}' уже существует")
        }

        val category = Category(
            name = request.name,
            type = request.type
        )
        val savedCategory = categoryRepository.save(category)
        return CategoryDto.fromEntity(savedCategory)
    }

    @Transactional
    fun updateCategory(id: Long, request: UpdateCategoryRequest): CategoryDto? {
        val category = categoryRepository.findById(id).orElse(null) ?: return null

        val updatedCategory = category.copy(
            name = request.name ?: category.name,
            isActive = request.isActive ?: category.isActive
        )

        val savedCategory = categoryRepository.save(updatedCategory)
        return CategoryDto.fromEntity(savedCategory)
    }

    @Transactional
    fun deleteCategory(id: Long): Boolean {
        return if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    @Transactional
    fun softDeleteCategory(id: Long): Boolean {
        val category = categoryRepository.findById(id).orElse(null) ?: return false
        val updatedCategory = category.copy(isActive = false)
        categoryRepository.save(updatedCategory)
        return true
    }

    // Инициализация начальных категорий
    fun initializeDefaultCategories() {
        if (categoryRepository.count() == 0L) {
            val defaultCategories = listOf(
                // Категории расходов
                Category(name = "Еда", type = CategoryType.EXPENSE),
                Category(name = "Транспорт", type = CategoryType.EXPENSE),
                Category(name = "Жилье", type = CategoryType.EXPENSE),
                Category(name = "Развлечения", type = CategoryType.EXPENSE),
                Category(name = "Здоровье", type = CategoryType.EXPENSE),
                Category(name = "Одежда", type = CategoryType.EXPENSE),

                // Категории доходов
                Category(name = "Зарплата", type = CategoryType.INCOME),
                Category(name = "Инвестиции", type = CategoryType.INCOME),
                Category(name = "Фриланс", type = CategoryType.INCOME),
                Category(name = "Подарки", type = CategoryType.INCOME)
            )
            categoryRepository.saveAll(defaultCategories)
        }
    }
}
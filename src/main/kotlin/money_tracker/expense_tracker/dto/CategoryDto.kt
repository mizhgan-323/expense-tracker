package money_tracker.expense_tracker.dto

import io.swagger.v3.oas.annotations.media.Schema
import money_tracker.expense_tracker.entity.Category
import money_tracker.expense_tracker.entity.CategoryType

@Schema(description = "DTO для категории доходов/расходов")
data class CategoryDto(
    @Schema(description = "Уникальный идентификатор категории", example = "1")
    val id: Long? = null,
    
    @Schema(description = "Название категории", example = "Продукты", required = true)
    val name: String,
    
    @Schema(description = "Тип категории", example = "EXPENSE", required = true)
    val type: CategoryType,
    
    @Schema(description = "Активна ли категория", example = "true")
    val isActive: Boolean = true
) {
    companion object {
        fun fromEntity(category: Category): CategoryDto {
            return CategoryDto(
                id = category.id,
                name = category.name,
                type = category.type,
                isActive = category.isActive
            )
        }
    }

    fun toEntity(): Category {
        return Category(
            id = id,
            name = name,
            type = type,
            isActive = isActive
        )
    }
}

@Schema(description = "Запрос на создание новой категории")
data class CreateCategoryRequest(
    @Schema(description = "Название категории", example = "Продукты", required = true)
    val name: String,
    
    @Schema(description = "Тип категории", example = "EXPENSE", required = true)
    val type: CategoryType
)

@Schema(description = "Запрос на обновление категории")
data class UpdateCategoryRequest(
    @Schema(description = "Новое название категории", example = "Продукты и напитки")
    val name: String? = null,
    
    @Schema(description = "Статус активности категории", example = "true")
    val isActive: Boolean? = null
)
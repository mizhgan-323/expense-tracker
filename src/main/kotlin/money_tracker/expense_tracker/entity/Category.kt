package money_tracker.expense_tracker.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table(name = "categories")
@Schema(description = "Категория доходов или расходов")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор категории")
    val id: Long? = null,

    @Column(nullable = false)
    @Schema(description = "Название категории", example = "Продукты")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Тип категории", example = "EXPENSE")
    val type: CategoryType,

    @Column(name = "is_active", nullable = false)
    @Schema(description = "Активна ли категория", example = "true")
    val isActive: Boolean = true
) {
    // Конструктор для удобства
    constructor(name: String, type: CategoryType) : this(null, name, type, true)
}

@Schema(description = "Тип категории")
enum class CategoryType {
    @Schema(description = "Категория расходов")
    EXPENSE,
    @Schema(description = "Категория доходов") 
    INCOME
}
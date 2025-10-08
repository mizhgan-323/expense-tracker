package money_tracker.expense_tracker.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
@Schema(description = "Транзакция дохода или расхода")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор транзакции")
    val id: Long? = null,

    @Column(nullable = false)
    @Schema(description = "Название операции", example = "Вечернее кофе с десертом")
    val name: String,

    @Column(nullable = false)
    @Schema(description = "Сумма транзакции", example = "1500.50")
    val amount: Double,

    @Column(nullable = false)
    @Schema(description = "Описание транзакции", example = "Выпила вечером кофе с подружками в кофейне на углу дома")
    val description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Тип транзакции", example = "EXPENSE")
    val type: TransactionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Категория транзакции")
    val category: Category,

    @Column(nullable = false)
    @Schema(description = "Дата и время транзакции", example = "2024-01-15T10:30:00")
    val date: LocalDateTime = LocalDateTime.now()
)

@Schema(description = "Тип транзакции")
enum class TransactionType {
    @Schema(description = "Доход")
    INCOME,
    @Schema(description = "Расход")
    EXPENSE
}
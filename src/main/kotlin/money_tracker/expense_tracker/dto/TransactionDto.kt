package money_tracker.expense_tracker.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import money_tracker.expense_tracker.entity.Transaction
import money_tracker.expense_tracker.entity.TransactionType
import java.time.LocalDateTime

@Schema(description = "DTO для транзакции")
data class TransactionDto(
    @Schema(description = "Уникальный идентификатор транзакции", example = "1")
    val id: Long? = null,
    
    @Schema(description = "Сумма транзакции", example = "1500.50", required = true)
    val amount: Double,
    
    @Schema(description = "Описание транзакции", example = "Покупка продуктов в супермаркете", required = true)
    val description: String,
    
    @Schema(description = "Тип транзакции", example = "EXPENSE", required = true)
    val type: TransactionType,
    
    @Schema(description = "ID категории транзакции", example = "1", required = true)
    val categoryId: Long,
    
    @Schema(description = "Дата и время транзакции", example = "2024-01-15T10:30:00")
    val date: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun fromEntity(transaction: Transaction): TransactionDto {
            return TransactionDto(
                id = transaction.id,
                amount = transaction.amount,
                description = transaction.description,
                type = transaction.type,
                categoryId = transaction.category.id ?: 0L,
                date = transaction.date
            )
        }
    }
}

@Schema(description = "Запрос на создание новой транзакции")
data class CreateTransactionRequest(
    @Schema(description = "Сумма транзакции", example = "1500.50", required = true)
    @field:NotNull(message = "Сумма транзакции не может быть пустой")
    @field:DecimalMin(value = "0.01", message = "Сумма транзакции должна быть больше 0")
    val amount: Double,
    
    @Schema(description = "Описание транзакции", example = "Покупка продуктов в супермаркете", required = true)
    @field:NotBlank(message = "Описание транзакции не может быть пустым")
    val description: String,
    
    @Schema(description = "Тип транзакции", example = "EXPENSE", required = true)
    @field:NotNull(message = "Тип транзакции не может быть пустым")
    val type: TransactionType,
    
    @Schema(description = "ID категории транзакции", example = "1", required = true)
    @field:NotNull(message = "ID категории не может быть пустым")
    @field:Positive(message = "ID категории должен быть положительным числом")
    val categoryId: Long,
    
    @Schema(description = "Дата и время транзакции", example = "2024-01-15T10:30:00")
    val date: LocalDateTime? = null
)

@Schema(description = "Запрос на обновление транзакции")
data class UpdateTransactionRequest(
    @Schema(description = "Сумма транзакции", example = "1500.50")
    val amount: Double? = null,
    
    @Schema(description = "Описание транзакции", example = "Покупка продуктов в супермаркете")
    val description: String? = null,
    
    @Schema(description = "Тип транзакции", example = "EXPENSE")
    val type: TransactionType? = null,
    
    @Schema(description = "ID категории транзакции", example = "1")
    val categoryId: Long? = null,
    
    @Schema(description = "Дата и время транзакции", example = "2024-01-15T10:30:00")
    val date: LocalDateTime? = null
)

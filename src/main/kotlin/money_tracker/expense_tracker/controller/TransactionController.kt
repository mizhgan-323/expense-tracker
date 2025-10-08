package money_tracker.expense_tracker.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import money_tracker.expense_tracker.dto.CreateTransactionRequest
import money_tracker.expense_tracker.dto.TransactionDto
import money_tracker.expense_tracker.dto.UpdateTransactionRequest
import money_tracker.expense_tracker.entity.TransactionType
import money_tracker.expense_tracker.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "API для управления транзакциями (доходами и расходами)")
class TransactionController(private val transactionService: TransactionService) {

    @Operation(summary = "Получить все транзакции", description = "Возвращает список всех транзакций")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список транзакций успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TransactionDto::class))]
            )
        ]
    )
    @GetMapping
    fun getAllTransactions(): List<TransactionDto> = transactionService.getAllTransactions()

    @Operation(summary = "Получить транзакцию по ID", description = "Возвращает информацию о транзакции по указанному идентификатору")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Транзакция найдена",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TransactionDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Транзакция не найдена"
            )
        ]
    )
    @GetMapping("/{id}")
    fun getTransactionById(
        @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<TransactionDto> {
        val transaction = transactionService.getTransactionById(id)
        return if (transaction != null) {
            ResponseEntity.ok(transaction)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Создать новую транзакцию", description = "Создает новую транзакцию дохода или расхода")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Транзакция успешно создана",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TransactionDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса"
            )
        ]
    )
    @PostMapping
    fun createTransaction(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные транзакции")
        @Valid @RequestBody request: CreateTransactionRequest
    ): ResponseEntity<TransactionDto> {
        val createdTransaction = transactionService.createTransaction(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction)
    }

    @Operation(summary = "Обновить транзакцию", description = "Обновляет информацию о существующей транзакции")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Транзакция успешно обновлена",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TransactionDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Транзакция не найдена"
            )
        ]
    )
    @PutMapping("/{id}")
    fun updateTransaction(
        @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
        @PathVariable id: Long,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Обновленные данные транзакции")
        @Valid @RequestBody request: UpdateTransactionRequest
    ): ResponseEntity<TransactionDto> {
        val transaction = transactionService.updateTransaction(id, request)
        return if (transaction != null) {
            ResponseEntity.ok(transaction)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Удалить транзакцию", description = "Удаляет транзакцию из системы")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Транзакция успешно удалена"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Транзакция не найдена"
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteTransaction(
        @Parameter(description = "Идентификатор транзакции", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        return if (transactionService.deleteTransaction(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Получить транзакции по типу", description = "Возвращает список транзакций определенного типа (INCOME или EXPENSE)")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список транзакций по типу успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TransactionDto::class))]
            )
        ]
    )
    @GetMapping("/type/{type}")
    fun getTransactionsByType(
        @Parameter(description = "Тип транзакции", required = true, example = "INCOME")
        @PathVariable type: TransactionType
    ): List<TransactionDto> {
        return transactionService.getTransactionsByType(type)
    }
}
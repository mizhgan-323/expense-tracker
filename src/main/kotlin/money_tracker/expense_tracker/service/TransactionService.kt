package money_tracker.expense_tracker.service

import money_tracker.expense_tracker.dto.CreateTransactionRequest
import money_tracker.expense_tracker.dto.TransactionDto
import money_tracker.expense_tracker.dto.UpdateTransactionRequest
import money_tracker.expense_tracker.entity.CategoryType
import money_tracker.expense_tracker.entity.Transaction
import money_tracker.expense_tracker.entity.TransactionType
import money_tracker.expense_tracker.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val categoryService: CategoryService
) {

    fun getAllTransactions(): List<TransactionDto> =
        transactionRepository.findAll().map { TransactionDto.fromEntity(it) }

    fun getTransactionById(id: Long): TransactionDto? =
        transactionRepository.findById(id).map { TransactionDto.fromEntity(it) }.orElse(null)

    fun getTransactionEntityById(id: Long): Transaction? =
        transactionRepository.findById(id).orElse(null)

    @Transactional
    fun createTransaction(request: CreateTransactionRequest): TransactionDto {
        val category = categoryService.getCategoryEntityById(request.categoryId)
            ?: throw IllegalArgumentException("Категория с ID ${request.categoryId} не найдена")

        if (!category.isActive) {
            throw IllegalArgumentException("Категория '${category.name}' не активна")
        }

        if ((request.type == TransactionType.EXPENSE && category.type != CategoryType.EXPENSE) ||
            (request.type == TransactionType.INCOME && category.type != CategoryType.INCOME)) {
            throw IllegalArgumentException(
                "Категория '${category.name}' не соответствует типу транзакции '${request.type}'. " +
                        "Ожидается тип категории: ${if (request.type == TransactionType.EXPENSE) CategoryType.EXPENSE else CategoryType.INCOME}"
            )
        }

        val transaction = Transaction(
            name = request.name,
            amount = request.amount,
            description = request.description,
            type = request.type,
            category = category,
            date = request.date ?: java.time.LocalDateTime.now()
        )

        val savedTransaction = transactionRepository.save(transaction)
        return TransactionDto.fromEntity(savedTransaction)
    }

    @Transactional
    fun createTransactionEntity(transaction: Transaction): Transaction {
        if (!transaction.category.isActive) {
            throw IllegalArgumentException("Категория '${transaction.category.name}' не активна")
        }

        if ((transaction.type == TransactionType.EXPENSE && transaction.category.type != CategoryType.EXPENSE) ||
            (transaction.type == TransactionType.INCOME && transaction.category.type != CategoryType.INCOME)) {
            throw IllegalArgumentException(
                "Категория '${transaction.category.name}' не соответствует типу транзакции '${transaction.type}'. " +
                        "Ожидается тип категории: ${if (transaction.type == TransactionType.EXPENSE) CategoryType.EXPENSE else CategoryType.INCOME}"
            )
        }

        return transactionRepository.save(transaction)
    }

    @Transactional
    fun updateTransaction(id: Long, request: UpdateTransactionRequest): TransactionDto? {
        val existingTransaction = transactionRepository.findById(id).orElse(null) ?: return null

        val categoryId = request.categoryId ?: existingTransaction.category.id
        val category = categoryService.getCategoryEntityById(categoryId!!)
            ?: throw IllegalArgumentException("Категория с ID $categoryId не найдена")

        if (!category.isActive) {
            throw IllegalArgumentException("Категория '${category.name}' не активна")
        }

        val transactionType = request.type ?: existingTransaction.type
        if ((transactionType == TransactionType.EXPENSE && category.type != CategoryType.EXPENSE) ||
            (transactionType == TransactionType.INCOME && category.type != CategoryType.INCOME)) {
            throw IllegalArgumentException(
                "Категория '${category.name}' не соответствует типу транзакции '$transactionType'"
            )
        }

        val updatedTransaction = existingTransaction.copy(
            name = request.name ?: existingTransaction.name,
            amount = request.amount ?: existingTransaction.amount,
            description = request.description ?: existingTransaction.description,
            type = transactionType,
            category = category,
            date = request.date ?: existingTransaction.date
        )

        val savedTransaction = transactionRepository.save(updatedTransaction)
        return TransactionDto.fromEntity(savedTransaction)
    }

    @Transactional
    fun updateTransactionEntity(id: Long, updatedTransaction: Transaction): Transaction? {
        return if (transactionRepository.existsById(id)) {
            if (!updatedTransaction.category.isActive) {
                throw IllegalArgumentException("Категория '${updatedTransaction.category.name}' не активна")
            }

            if ((updatedTransaction.type == TransactionType.EXPENSE && updatedTransaction.category.type != CategoryType.EXPENSE) ||
                (updatedTransaction.type == TransactionType.INCOME && updatedTransaction.category.type != CategoryType.INCOME)) {
                throw IllegalArgumentException(
                    "Категория '${updatedTransaction.category.name}' не соответствует типу транзакции '${updatedTransaction.type}'"
                )
            }

            transactionRepository.save(updatedTransaction.copy(id = id))
        } else {
            null
        }
    }

    @Transactional
    fun deleteTransaction(id: Long): Boolean {
        return if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun getTransactionsByType(type: TransactionType): List<TransactionDto> =
        transactionRepository.findByType(type).map { TransactionDto.fromEntity(it) }

    fun getTransactionsByCategory(categoryId: Long): List<TransactionDto> {
        val category = categoryService.getCategoryEntityById(categoryId)
        return if (category != null) {
            transactionRepository.findByCategory(category).map { TransactionDto.fromEntity(it) }
        } else {
            emptyList()
        }
    }

    fun getTransactionsByCategoryAndType(categoryId: Long, type: TransactionType): List<TransactionDto> {
        val category = categoryService.getCategoryEntityById(categoryId)
        return if (category != null) {
            transactionRepository.findByCategoryAndType(category, type).map { TransactionDto.fromEntity(it) }
        } else {
            emptyList()
        }
    }
}
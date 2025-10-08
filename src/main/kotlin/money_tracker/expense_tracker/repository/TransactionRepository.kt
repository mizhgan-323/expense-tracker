package money_tracker.expense_tracker.repository

import money_tracker.expense_tracker.entity.Transaction
import money_tracker.expense_tracker.entity.TransactionType
import money_tracker.expense_tracker.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByType(type: TransactionType): List<Transaction>
    fun findByCategory(category: Category): List<Transaction>
    fun findByCategoryAndType(category: Category, type: TransactionType): List<Transaction>
}
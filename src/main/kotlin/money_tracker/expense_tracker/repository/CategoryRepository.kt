package money_tracker.expense_tracker.repository

import money_tracker.expense_tracker.entity.Category
import money_tracker.expense_tracker.entity.CategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByType(type: CategoryType): List<Category>
    fun findByTypeAndIsActiveTrue(type: CategoryType): List<Category>
    fun findByNameAndType(name: String, type: CategoryType): Category?
    fun existsByNameAndType(name: String, type: CategoryType): Boolean
}
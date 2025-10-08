package money_tracker.expense_tracker.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import money_tracker.expense_tracker.dto.CategoryDto
import money_tracker.expense_tracker.dto.CreateCategoryRequest
import money_tracker.expense_tracker.dto.UpdateCategoryRequest
import money_tracker.expense_tracker.entity.CategoryType
import money_tracker.expense_tracker.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "API для управления категориями доходов и расходов")
class CategoryController(private val categoryService: CategoryService) {

    @Operation(summary = "Получить все категории", description = "Возвращает список всех доступных категорий")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список категорий успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            )
        ]
    )
    @GetMapping
    fun getAllCategories(): List<CategoryDto> = categoryService.getAllCategories()

    @Operation(summary = "Получить категории по типу", description = "Возвращает список категорий определенного типа (EXPENSE или INCOME)")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список категорий по типу успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            )
        ]
    )
    @GetMapping("/type/{type}")
    fun getCategoriesByType(
        @Parameter(description = "Тип категории", required = true, example = "EXPENSE")
        @PathVariable type: CategoryType
    ): List<CategoryDto> =
        categoryService.getCategoriesByType(type)

    @Operation(summary = "Получить категории расходов", description = "Возвращает список всех категорий для расходов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список категорий расходов успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            )
        ]
    )
    @GetMapping("/expense")
    fun getExpenseCategories(): List<CategoryDto> =
        categoryService.getCategoriesByType(CategoryType.EXPENSE)

    @Operation(summary = "Получить категории доходов", description = "Возвращает список всех категорий для доходов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Список категорий доходов успешно получен",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            )
        ]
    )
    @GetMapping("/income")
    fun getIncomeCategories(): List<CategoryDto> =
        categoryService.getCategoriesByType(CategoryType.INCOME)

    @Operation(summary = "Получить категорию по ID", description = "Возвращает информацию о категории по указанному идентификатору")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Категория найдена",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Категория не найдена"
            )
        ]
    )
    @GetMapping("/{id}")
    fun getCategoryById(
        @Parameter(description = "Идентификатор категории", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<CategoryDto> {
        val category = categoryService.getCategoryById(id)
        return if (category != null) {
            ResponseEntity.ok(category)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Создать новую категорию", description = "Создает новую категорию доходов или расходов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Категория успешно создана",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Некорректные данные запроса"
            )
        ]
    )
    @PostMapping
    fun createCategory(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для создания категории")
        @RequestBody request: CreateCategoryRequest
    ): ResponseEntity<Any> {
        return try {
            val category = categoryService.createCategory(request)
            ResponseEntity.status(HttpStatus.CREATED).body(category)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @Operation(summary = "Обновить категорию", description = "Обновляет информацию о существующей категории")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Категория успешно обновлена",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Категория не найдена"
            )
        ]
    )
    @PutMapping("/{id}")
    fun updateCategory(
        @Parameter(description = "Идентификатор категории", required = true, example = "1")
        @PathVariable id: Long,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для обновления категории")
        @RequestBody request: UpdateCategoryRequest
    ): ResponseEntity<CategoryDto> {
        val category = categoryService.updateCategory(id, request)
        return if (category != null) {
            ResponseEntity.ok(category)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Удалить категорию", description = "Полностью удаляет категорию из системы")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Категория успешно удалена"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Категория не найдена"
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteCategory(
        @Parameter(description = "Идентификатор категории", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        return if (categoryService.deleteCategory(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Деактивировать категорию", description = "Мягкое удаление - деактивирует категорию без физического удаления")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Категория успешно деактивирована"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Категория не найдена"
            )
        ]
    )
    @PatchMapping("/{id}/deactivate")
    fun deactivateCategory(
        @Parameter(description = "Идентификатор категории", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        return if (categoryService.softDeleteCategory(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Активировать категорию", description = "Активирует ранее деактивированную категорию")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Категория успешно активирована",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CategoryDto::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Категория не найдена"
            )
        ]
    )
    @PatchMapping("/{id}/activate")
    fun activateCategory(
        @Parameter(description = "Идентификатор категории", required = true, example = "1")
        @PathVariable id: Long
    ): ResponseEntity<CategoryDto> {
        val request = UpdateCategoryRequest(isActive = true)
        val category = categoryService.updateCategory(id, request)
        return if (category != null) {
            ResponseEntity.ok(category)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
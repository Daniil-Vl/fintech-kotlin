package ru.tbank.springapp.controller

import org.springframework.web.bind.annotation.*
import ru.tbank.springapp.aspect.Timed
import ru.tbank.springapp.dto.CategoryDTO
import ru.tbank.springapp.service.CategoryService

@RestController
@RequestMapping("/api/v1/places/categories")
@Timed
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getCategories(): List<CategoryDTO> =
        categoryService
            .findAll()
            .map { it.toDTO() }

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: String): CategoryDTO? =
        categoryService
            .findById(id)
            ?.toDTO()

    @PostMapping
    fun createCategory(@RequestBody categoryDTO: CategoryDTO) =
        categoryService.create(categoryDTO.slug, categoryDTO.name)

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: String, @RequestBody categoryDTO: CategoryDTO) =
        categoryService.update(id, categoryDTO.name)

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: String) =
        categoryService.delete(id)
}
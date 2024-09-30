package ru.tbank.springapp.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.tbank.springapp.dao.Repository
import ru.tbank.springapp.model.Category
import ru.tbank.springapp.service.CategoryService

@Service
class CategoryServiceImpl(
    private val categoryRepository: Repository<String, Category>
) : CategoryService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAll(): List<Category> {
        logger.info("Trying to get all categories")
        return categoryRepository.getAll()
    }

    override fun findById(slug: String): Category? {
        logger.info("Trying to find category by id $slug")
        return categoryRepository.getById(slug)
    }

    override fun create(slug: String, name: String): Category? {
        val category = Category(slug, name)
        logger.info("Trying to create category $category")
        return categoryRepository.save(slug, category)
    }

    override fun update(slug: String, name: String): Category? {
        logger.info("Trying to update category with id $slug")
        val category = Category(slug, name)
        return categoryRepository.update(slug, category)
    }

    override fun delete(slug: String): Category? {
        logger.info("Trying to remove category with id $slug")
        return categoryRepository.delete(slug)
    }
}
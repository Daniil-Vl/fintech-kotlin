package ru.tbank.springapp.controller

import org.springframework.web.bind.annotation.*
import ru.tbank.springapp.aspect.Timed
import ru.tbank.springapp.dto.CityDTO
import ru.tbank.springapp.exception.ResourceNotFoundException
import ru.tbank.springapp.service.CityService

@RestController
@RequestMapping("/api/v1/locations")
@Timed
class CityController(
    private val cityService: CityService
) {

    @GetMapping
    fun getCities(): List<CityDTO> =
        cityService
            .findAll()
            .map { it.toDTO() }

    @GetMapping("/{id}")
    fun getCity(@PathVariable id: String): CityDTO =
        cityService
            .findById(id)
            .toDTO()

    @PostMapping
    fun createCity(@RequestBody cityDTO: CityDTO) =
        cityService.create(cityDTO.slug, cityDTO.name)


    @PutMapping("/{id}")
    fun updateCity(@PathVariable id: String, @RequestBody cityDTO: CityDTO) =
        cityService.update(id, cityDTO.name)


    @DeleteMapping("/{id}")
    fun deleteCity(@PathVariable id: String) =
        cityService.delete(id)
}
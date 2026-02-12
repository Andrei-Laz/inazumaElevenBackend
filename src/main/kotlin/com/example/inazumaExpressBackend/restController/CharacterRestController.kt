package com.example.inazumaExpressBackend.restController

import com.example.inazumaExpressBackend.model.Character
import com.example.inazumaExpressBackend.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// ⚠️ KEY: @RestController (not @Controller) automatically returns JSON
@RestController
@RequestMapping("/api/inazuma-characters")
class CharacterRestController {

    @Autowired
    private lateinit var characterService: CharacterService

    @GetMapping
    fun getAllCharacters(): ResponseEntity<List<Character>> {
        return ResponseEntity.ok(characterService.getAllCharacters())
    }

    @GetMapping("/{id}")
    fun getCharacterById(@PathVariable id: Int): ResponseEntity<Character> {
        return characterService.getCharacterById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createCharacter(@RequestBody character: Character): ResponseEntity<Character> {
        val saved = characterService.saveCharacter(character)
        return ResponseEntity.status(201).body(saved)
    }

    @PutMapping("/{id}")
    fun updateCharacter(
        @PathVariable id: Int,
        @RequestBody character: Character
    ): ResponseEntity<Character> {
        val updated = characterService.updateCharacter(id, character)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCharacter(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = characterService.deleteCharacter(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
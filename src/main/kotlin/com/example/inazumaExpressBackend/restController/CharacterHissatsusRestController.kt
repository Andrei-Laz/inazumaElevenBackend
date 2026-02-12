package com.example.inazumaExpressBackend.restController

import com.example.inazumaExpressBackend.model.CharacterHissatsus
import com.example.inazumaExpressBackend.model.Hissatsu
import com.example.inazumaExpressBackend.service.CharacterHissatsusService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/character-hissatsus")
class CharacterHissatsusRestController(
    private val service: CharacterHissatsusService
) {

    // Assign hissatsu to character
    @PostMapping
    fun assignHissatsu(
        @RequestParam characterId: Int,
        @RequestParam hissatsuId: Int
    ): ResponseEntity<CharacterHissatsus> {
        return try {
            val assignment = service.assignHissatsuToCharacter(characterId, hissatsuId)
            ResponseEntity.ok(assignment)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(null)
        }
    }

    // Remove hissatsu from character
    @DeleteMapping
    fun removeHissatsu(
        @RequestParam characterId: Int,
        @RequestParam hissatsuId: Int
    ): ResponseEntity<Void> {
        return if (service.removeHissatsuFromCharacter(characterId, hissatsuId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Get all hissatsus for a character
    @GetMapping("/character/{characterId}")
    fun getHissatsusForCharacter(@PathVariable characterId: Int): ResponseEntity<List<Hissatsu>> {
        return ResponseEntity.ok(service.getHissatsusForCharacter(characterId))
    }
}
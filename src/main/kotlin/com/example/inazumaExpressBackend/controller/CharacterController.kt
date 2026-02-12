package com.example.inazumaExpressBackend.controller

import com.example.inazumaExpressBackend.model.Character
import com.example.inazumaExpressBackend.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Controller
@RequestMapping("/inazuma-characters")
class CharacterController {

    @Autowired
    private lateinit var characterService: CharacterService

    @GetMapping
    fun listCharacters(model: Model): String {
        model.addAttribute("characters", characterService.getAllCharacters())
        return "characters"
    }

    @GetMapping("/new")
    fun showCreateForm(model: Model): String {
        model.addAttribute("character", Character())
        return "characterForm"
    }

    @PostMapping
    fun createCharacter(
        @ModelAttribute character: Character,
        @RequestParam("image") file: MultipartFile?  // ← NEW: Handle file upload
    ): String {
        // Handle image upload
        val filename = saveCharacterImage(file, character.characterId)
        character.imageUrl = "/images/characters/$filename"

        characterService.saveCharacter(character)
        return "redirect:/inazuma-characters/${character.characterId}"
    }

    @PostMapping("/{id}")
    fun updateCharacter(
        @PathVariable id: Int,
        @ModelAttribute character: Character,
        @RequestParam("image") file: MultipartFile?  // ← NEW
    ): String {
        val existing = characterService.getCharacterById(id) ?: return "redirect:/inazuma-characters"

        // Preserve existing image if no new file uploaded
        val filename = if (file != null && !file.isEmpty) {
            saveCharacterImage(file, id)
        } else {
            existing.imageUrl.substringAfterLast('/')
        }
        character.imageUrl = "/images/characters/$filename"

        characterService.updateCharacter(id, character)
        return "redirect:/inazuma-characters/${id}"
    }

    // Helper method (add inside controller)
    private fun saveCharacterImage(file: MultipartFile?, characterId: Int?): String {
        if (file == null || file.isEmpty) return "default.png"

        // Generate unique filename: character_{id}_{timestamp}.ext
        val ext = file.originalFilename?.substringAfterLast('.') ?: "png"
        val filename = "character_${characterId ?: System.currentTimeMillis()}_${System.currentTimeMillis()}.$ext"

        // Save to static resources directory
        val path = Paths.get("src/main/resources/static/images/characters", filename)
        Files.createDirectories(path.parent)
        file.transferTo(path.toFile())

        return filename
    }

    @GetMapping("/{id}/edit")
    fun showUpdateForm(@PathVariable id: Int, model: Model): String {
        val character = characterService.getCharacterById(id)
        if (character != null) {
            model.addAttribute("character", character)
            return "characterForm"
        }
        return "redirect:/inazuma-characters"
    }


    @GetMapping("/{id}/delete")
    fun deleteCharacter(@PathVariable id: Int): String {
        characterService.deleteCharacter(id)
        return "redirect:/inazuma-characters"
    }

    @GetMapping("/{id}")
    fun getCharacterDetails(@PathVariable id: Int, model: Model): String {
        val character = characterService.getCharacterById(id)
        if (character != null) {
            model.addAttribute("character", character)
            return "characterDetails"
        }
        return "redirect:/inazuma-characters"
    }
}
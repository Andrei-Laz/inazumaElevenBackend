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
    ): String {
        characterService.saveCharacter(character)
        return "redirect:/inazuma-characters/${character.characterId}"
    }

    @PostMapping("/{id}")
    fun updateCharacter(
        @PathVariable id: Int,
        @ModelAttribute character: Character,
    ): String {
        val existing = characterService.getCharacterById(id) ?: return "redirect:/inazuma-characters"

        characterService.updateCharacter(id, character)
        return "redirect:/inazuma-characters/${id}"
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
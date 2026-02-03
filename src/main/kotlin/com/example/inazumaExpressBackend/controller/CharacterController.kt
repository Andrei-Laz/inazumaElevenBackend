package com.example.inazumaExpressBackend.controller

import com.example.inazumaExpressBackend.model.Character
import com.example.inazumaExpressBackend.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/characters")
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
    fun createCharacter(@ModelAttribute character: Character, model: Model): String {
        try {
            characterService.saveCharacter(character)
            return "redirect:/characters"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "characterError"
        }
    }

    @GetMapping("/{id}/edit")
    fun showUpdateForm(@PathVariable id: Int, model: Model): String {
        val character = characterService.getCharacterById(id)
        if (character != null) {
            model.addAttribute("character", character)
            return "characterForm"
        }
        return "redirect:/characters"
    }

    @PostMapping("/{id}")
    fun updateCharacter(@PathVariable id: Int, @ModelAttribute character: Character, model: Model): String {
        try {
            val updated = characterService.updateCharacter(id, character)
            if (updated == null) {
                model.addAttribute("error", "No se pudo actualizar el personaje.")
                return "characterError"
            }
            return "redirect:/characters"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "characterError"
        }
    }

    @GetMapping("/{id}/delete")
    fun deleteCharacter(@PathVariable id: Int): String {
        characterService.deleteCharacter(id)
        return "redirect:/characters"
    }

    @GetMapping("/{id}")
    fun getCharacterDetails(@PathVariable id: Int, model: Model): String {
        val character = characterService.getCharacterById(id)
        if (character != null) {
            model.addAttribute("character", character)
            return "characterDetails"
        }
        return "redirect:/characters"
    }
}
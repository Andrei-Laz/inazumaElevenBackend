package com.example.inazumaExpressBackend.controller

import com.example.inazumaExpressBackend.model.Hissatsu
import com.example.inazumaExpressBackend.model.enums.Element
import com.example.inazumaExpressBackend.service.HissatsuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/hissatsus")
class HissatsuController {

    @Autowired
    private lateinit var hissatsuService: HissatsuService

    @GetMapping
    fun listHissatsus(model: Model): String {
        model.addAttribute("hissatsus", hissatsuService.getAllHissatsus())
        return "hissatsus"
    }

    @GetMapping("/new")
    fun showCreateForm(model: Model): String {
        val newHissatsu = Hissatsu(
            element = Element.FIRE
        )
        model.addAttribute("hissatsu", newHissatsu)
        return "hissatsuForm"
    }

    @PostMapping
    fun createHissatsu(@ModelAttribute hissatsu: Hissatsu, model: Model): String {
        try {
            hissatsuService.saveHissatsu(hissatsu)
            return "redirect:/hissatsus"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "hissatsuError"
        }
    }

    @GetMapping("/{id}/edit")
    fun showUpdateForm(@PathVariable id: Int, model: Model): String {
        val hissatsu = hissatsuService.getHissatsuById(id)
        if (hissatsu != null) {
            model.addAttribute("hissatsu", hissatsu)
            return "hissatsuForm"
        }
        return "redirect:/hissatsus"
    }

    @PostMapping("/{id}")
    fun updateHissatsu(@PathVariable id: Int, @ModelAttribute hissatsu: Hissatsu, model: Model): String {
        try {
            val updated = hissatsuService.updateHissatsu(id, hissatsu)
            if (updated == null) {
                model.addAttribute("error", "No se pudo actualizar el hissatsu.")
                return "hissatsuError"
            }
            return "redirect:/hissatsus"
        } catch (e: Exception) {
            model.addAttribute("error", e.message)
            return "hissatsuError"
        }
    }

    @GetMapping("/{id}/delete")
    fun deleteHissatsu(@PathVariable id: Int): String {
        hissatsuService.deleteHissatsu(id)
        return "redirect:/hissatsus"
    }

    @GetMapping("/{id}")
    fun getHissatsuDetails(@PathVariable id: Int, model: Model): String {
        val hissatsu = hissatsuService.getHissatsuById(id)
        if (hissatsu != null) {
            model.addAttribute("hissatsu", hissatsu)
            return "hissatsuDetails"
        }
        return "redirect:/hissatsus"
    }
}
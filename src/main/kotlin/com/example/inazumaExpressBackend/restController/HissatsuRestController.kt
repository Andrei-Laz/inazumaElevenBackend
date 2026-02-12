package com.example.inazumaExpressBackend.restController

import com.example.inazumaExpressBackend.model.Hissatsu
import com.example.inazumaExpressBackend.service.HissatsuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hissatsus")
class HissatsuRestController {

    @Autowired
    private lateinit var hissatsuService: HissatsuService

    @GetMapping
    fun getAllHissatsus(): ResponseEntity<List<Hissatsu>> {
        return ResponseEntity.ok(hissatsuService.getAllHissatsus())
    }

    @GetMapping("/{id}")
    fun getHissatsuById(@PathVariable id: Int): ResponseEntity<Hissatsu> {
        return hissatsuService.getHissatsuById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createHissatsu(@RequestBody hissatsu: Hissatsu): ResponseEntity<Hissatsu> {
        val saved = hissatsuService.saveHissatsu(hissatsu)
        return ResponseEntity.status(201).body(saved)
    }

    @PutMapping("/{id}")
    fun updateHissatsu(
        @PathVariable id: Int,
        @RequestBody hissatsu: Hissatsu
    ): ResponseEntity<Hissatsu> {
        val updated = hissatsuService.updateHissatsu(id, hissatsu)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteHissatsu(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = hissatsuService.deleteHissatsu(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
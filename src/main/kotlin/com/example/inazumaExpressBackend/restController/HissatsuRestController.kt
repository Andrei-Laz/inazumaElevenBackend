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

    // GET /api/inazuma-hissatsus → Returns JSON array of all hissatsus
    @GetMapping
    fun getAllHissatsus(): ResponseEntity<List<Hissatsu>> {
        return ResponseEntity.ok(hissatsuService.getAllHissatsus())
    }

    // GET /api/inazuma-hissatsus/{id} → Returns single hissatsu as JSON
    @GetMapping("/{id}")
    fun getHissatsuById(@PathVariable id: Int): ResponseEntity<Hissatsu> {
        return hissatsuService.getHissatsuById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    // POST /api/inazuma-hissatsus → Creates hissatsu from JSON body
    @PostMapping
    fun createHissatsu(@RequestBody hissatsu: Hissatsu): ResponseEntity<Hissatsu> {
        val saved = hissatsuService.saveHissatsu(hissatsu)
        return ResponseEntity.status(201).body(saved) // 201 Created
    }

    // PUT /api/inazuma-hissatsus/{id} → Updates hissatsu from JSON body
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

    // DELETE /api/inazuma-hissatsus/{id} → Deletes hissatsu
    @DeleteMapping("/{id}")
    fun deleteHissatsu(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = hissatsuService.deleteHissatsu(id)
        return if (deleted) {
            ResponseEntity.noContent().build() // 204 No Content
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
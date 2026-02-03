package com.example.inazumaExpressBackend.service

import com.example.inazumaExpressBackend.model.Hissatsu
import com.example.inazumaExpressBackend.repository.HissatsuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HissatsuService {

    @Autowired
    private lateinit var hissatsuRepository: HissatsuRepository

    fun getAllHissatsus(): List<Hissatsu> = hissatsuRepository.findAll()

    fun getHissatsuById(id: Int): Hissatsu? = hissatsuRepository.findById(id).orElse(null)

    fun saveHissatsu(hissatsu: Hissatsu): Hissatsu = hissatsuRepository.save(hissatsu)

    fun updateHissatsu(id: Int, updatedHissatsu: Hissatsu): Hissatsu? {
        if (hissatsuRepository.existsById(id)) {
            updatedHissatsu.hissatsuId = id
            return hissatsuRepository.save(updatedHissatsu)
        }
        return null
    }

    fun deleteHissatsu(id: Int): Boolean {
        if (hissatsuRepository.existsById(id)) {
            hissatsuRepository.deleteById(id)
            return true
        }
        return false
    }

    fun searchByName(name: String): List<Hissatsu> = hissatsuRepository.findByName(name)
}
package com.example.inazumaExpressBackend.repository

import com.example.inazumaExpressBackend.model.Hissatsu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HissatsuRepository : JpaRepository<Hissatsu, Int> {
    fun findByName(name: String): List<Hissatsu>
}
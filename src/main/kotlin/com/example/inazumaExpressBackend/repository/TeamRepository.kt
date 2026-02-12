package com.example.inazumaExpressBackend.repository

import com.example.inazumaExpressBackend.model.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, Int> {
    fun findByTeamNameContaining(name: String): List<Team>
    fun existsByTeamName(name: String): Boolean
}
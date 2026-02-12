package com.example.inazumaExpressBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    var teamId: Int? = null,

    @Column(nullable = false)
    var teamName: String = "",

    // Relationships
    @JsonIgnore
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableSet<TeamMember> = mutableSetOf()
)
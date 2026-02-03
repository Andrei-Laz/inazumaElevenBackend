package com.example.inazumaExpressBackend.model

import jakarta.persistence.*

@Entity
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var teamId: Int? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "team_character",
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")]
    )
    var characters: MutableSet<Character> = mutableSetOf(),

    
)
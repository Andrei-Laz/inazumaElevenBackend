package com.example.inazumaExpressBackend.model

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column
    var formation: String = "4-4-2", // Default formation

    // Relationships
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableSet<TeamMember> = mutableSetOf()
)
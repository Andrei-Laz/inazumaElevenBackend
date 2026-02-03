package com.example.inazumaExpressBackend.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var userId: Int? = null,

    @Column(nullable = false)
    var userName: String,

    @Column(nullable = false)
    var password: String,
)
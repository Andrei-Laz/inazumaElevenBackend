package com.example.inazumaExpressBackend.model
//
//import com.fasterxml.jackson.annotation.JsonIgnore
//import jakarta.persistence.*
//
//@Entity
//@Table(name = "users")
//data class User(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    var userId: Int? = null,
//
//    @Column(nullable = false, unique = true)
//    var userName: String = "",
//
//    @Column(nullable = false)
//    var password: String = "",
//
//    @Column(unique = true)
//    var email: String? = null,
//
//    // Relationships
//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
//    val teams: MutableSet<Team> = mutableSetOf(),
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
//    val hissatsuAssignments: MutableSet<CharacterHissatsus> = mutableSetOf()
//)
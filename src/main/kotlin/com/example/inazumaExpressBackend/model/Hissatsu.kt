package com.example.inazumaExpressBackend.model

import com.example.inazumaExpressBackend.model.enums.Element
import jakarta.persistence.*

@Entity
@Table(name = "hissatsus")
data class Hissatsu(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var hissatsuId: Int? = null,

    @Column(nullable = false)
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var element: Element,

    @Column
    var description: String = ""
)
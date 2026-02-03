package com.example.inazumaExpressBackend.model

import com.example.inazumaExpressBackend.model.enums.Element
import jakarta.persistence.*

@Embeddable
class CharacterHissatsuId(
    var characterId: Int = 0,
    var hissatsuId: Int = 0
)

@Entity
@Table(name = "character_hissatsus")
data class characterHissatsus(

    @EmbeddedId
    var id: CharacterHissatsuId = CharacterHissatsuId(),

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "character_id")
    var character: Character? = null,

    @ManyToOne
    @MapsId("hissatsuId")
    @JoinColumn(name = "hissatsu_id")
    var hissatsu: Hissatsu? = null,

    @Column
    var element: Element
)
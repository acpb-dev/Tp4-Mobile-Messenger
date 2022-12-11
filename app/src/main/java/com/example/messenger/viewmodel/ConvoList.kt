package com.example.messenger.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.User

class ConvoList {
    private var _convoList: MutableMap<String, ConvoListData> = mutableStateMapOf()
    private val _convoListConst: MutableMap<String, ConvoListData> = mutableStateMapOf()

    init {
        val t1: ConvoListData = ConvoListData(User("1", "Will", "Bergy", "https://cdn.discordapp.com/avatars/305770707239829504/a_dab45062e55791a8d483a9f7d7373c66.gif?size=256", true))
        val t2: ConvoListData = ConvoListData(User("2", "Seth", "Browning", "https://cdn.discordapp.com/avatars/144716588594102273/769259b1eb517cacaba42f7798d3e560.webp?size=256", false))
        val t3: ConvoListData = ConvoListData(User("3", "Alex", "Mayfield", "https://cdn.discordapp.com/avatars/673951279260893224/cfacc64e8e6096b6c6286a5d453ef611.webp?size=256", false))
        val t4: ConvoListData = ConvoListData(User("4", "Card", "Lith", "https://cdn.discordapp.com/avatars/442696238287290378/d6b87c4bbe6ee7f0546cab201f2b7923.webp?size=256", true))
        val t5: ConvoListData = ConvoListData(User("5", "Ian", "Jetz", "https://cdn.discordapp.com/avatars/1044465738406170694/539326f4d2b7c586563d7576af45041c.webp?size=256", false))
        val tp: List<ConvoListData> = listOf<ConvoListData>(t1, t2, t3, t4, t5)

        tp.forEach{
            _convoList[it.user.uid] = it
        }
    }

    fun getUser(uid: String): ConvoListData {
        _convoList.forEach{
            if (it.value.user.uid == uid){
                return it.value
            }
        }
        return _convoList["1"]!!
    }


    val convoList: Map<String, ConvoListData>
        get() = _convoList

}
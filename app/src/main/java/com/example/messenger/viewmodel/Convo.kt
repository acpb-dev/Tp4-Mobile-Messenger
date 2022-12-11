package com.example.messenger.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.example.messenger.viewmodel.data.IndividualConvo
import com.example.messenger.viewmodel.data.Messages

class Convo {

    val t1 = mutableListOf<Messages>(
        Messages(true, "hey"),
        Messages(false, "heyyo"),
        Messages(true, "whats up?"),
        Messages(false, "not much hbu?"),
        Messages(true, "meh , just doing projets"),
        Messages(false, "ahh shittt, same"),
        Messages(true, "yea we got toot much todo"),
        Messages(false, "ikr..."),
        Messages(true, "Anyways whtachuh doing friday?"),
        Messages(false, "nothing"),
        Messages(false, "not much hbu?"),
        Messages(true, "meh , just doing projets"),
        Messages(false, "ahh shittt, same"),
        Messages(true, "yea we got toot much todo"),
        Messages(false, "ikr..."),
        Messages(true, "Anyways whtachuh doing friday?"),
        Messages(false, "nothing"),
        Messages(false, "not much hbu?"),
        Messages(true, "meh , just doing projets"),
        Messages(false, "ahh shittt, same"),
        Messages(true, "yea we got toot much todo"),
        Messages(false, "ikr..."),
        Messages(true, "Anyways whtachuh doing friday?"),
        Messages(false, "nothing"),
        Messages(false, "not much hbu?"),
        Messages(true, "meh , just doing projets"),
        Messages(false, "ahh shittt, same"),
        Messages(true, "yea we got toot much todo"),
        Messages(false, "ikr..."),
        Messages(true, "Anyways whtachuh doing friday?"),
        Messages(false, "nothing"),
        Messages(true, "wanna do something?"))
    val tt: IndividualConvo = IndividualConvo(t1);

    fun getConvo(): IndividualConvo {
        return tt;
    }


//    val convoMessagesList: MutableState<IndividualConvo>
//        get() = _convoMessages
//
//    fun pushMessage(message: String){
//        _convoMessages.value.messages.add(Messages(true, message))
//    }

    fun getConversation(uid: String){
        //_convoMessages = APICALL();
    }




}
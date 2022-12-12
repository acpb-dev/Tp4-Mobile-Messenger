package com.example.messenger.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import com.example.messenger.viewmodel.data.ConvoListData
import com.example.messenger.viewmodel.data.Messages

class MessengerViewModel {
    private var currentUserSelected: String = "";
    private val login = Login();
    private val convoListClass = ConvoList();
    private val convoClass = Convo();



    fun getCurrentUser(): ConvoListData {
        return convoListClass.getUser(currentUserSelected);
    }

    fun selectUser(uid: String){
        currentUserSelected = uid;
    }


    private var _convosMessages: MutableList<Messages> = mutableListOf()
    val convosMessages: MutableList<Messages>
        get() = _convosMessages


    fun getMessages(): MutableList<Messages> {
        val tt = convoClass.getConvo()
        tt.messages.forEach{
            _convosMessages.add(it)
        }
        return convosMessages;
    }

    fun pushMessage(message: String){
        _convosMessages.add(Messages(true, message))
        print("f")
    }







    // TODO: Convo List
    val convoList: Map<String, ConvoListData>
        get() = convoListClass.convoList

    fun getUser(uid: String): ConvoListData {
        return convoListClass.getUser(uid);
    }

    //TODO: Send message
    fun sendMessage(toUid: String, message: String){

    }






    //TODO: Credentials
    fun loginVerification(email: String, password: String): Boolean{
        return login.CheckCredentials(email, password)
    }

    //TODO: Search Widget
    private val searchWidget = SearchWidget();
    val searchWidgetState: State<SearchWidget.SearchWidgetState>
        get() = searchWidget.searchWidgetState

    val searchTextState: State<String>
        get() = searchWidget.searchTextState

    fun updateSearchWidgetState(newValue: SearchWidget.SearchWidgetState){
        searchWidget.updateSearchWidgetState(newValue);
    }

    fun updateSearchTextState(newValue: String){
        searchWidget.updateSearchTextState(newValue);
    }




}
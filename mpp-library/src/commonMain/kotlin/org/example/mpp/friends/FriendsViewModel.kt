package org.example.mpp.friends

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class FriendsViewModel : ViewModel() {
    private val _friends: MutableLiveData<List<Friend>> =
        MutableLiveData(
            initialValue = List(10) {
                Friend(
                    id = it,
                    name = "friend $it",
                    avatarUrl = "https://exchange.icinga.com/jschanz/Batman%20Theme%20%28Light%29/logo"
                )
            }
        )
    val friends: LiveData<List<Friend>> = _friends
}
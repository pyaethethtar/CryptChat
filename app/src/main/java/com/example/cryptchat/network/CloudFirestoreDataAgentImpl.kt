package com.example.cryptchat.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.utils.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
object CloudFirestoreDataAgentImpl : FirebaseAPI {

    private val firestoreDB = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override fun login(
        user: UserVO,
        profileImage: Bitmap?,
        onSuccess: (id: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = firestoreDB.collection(GET_USERS).document()
        user.userId = docRef.id

        if (profileImage!=null){
            uploadImage(profileImage, DIRECTORY_PROFILE_IMAGES, onComplete = {
                user.profileImage = it
                docRef.set(user.toUserMap()).addOnSuccessListener {
                    onSuccess(user.userId)
                }.addOnFailureListener {
                    onFailure(it.message ?: EM_NO_INTERNET)
                }
            })
        }
        else{
            docRef.set(user.toUserMap()).addOnSuccessListener {
                onSuccess(user.userId)
            }.addOnFailureListener {
                onFailure(it.message ?: EM_NO_INTERNET)
            }
        }




    }

    override fun getChatList(
        userId: String,
        onSuccess: (List<ChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = firestoreDB.collection(GET_CHATS).whereArrayContains(USER_IDS, userId)
        docRef.get().addOnSuccessListener {
            val chatList = arrayListOf<ChatVO>()
            if (it.isEmpty){
                onSuccess(chatList)
            }else{
                for (document in it){
                    val chat = document.data.toChatVO()
                    var isCompleteMessages : Boolean = false
                    val messageCollectionRef = firestoreDB.collection(GET_CHATS).document(document.id).collection(GET_MESSAGES)
                    fetchMessageList(messageCollectionRef, onSucess={messageList->
                        chat.messages = messageList
                        isCompleteMessages = true
                        chatList.add(chat)

                        if (document==it.last() && isCompleteMessages){
                            onSuccess(chatList)
                        }
                    },onFailure={
                        onFailure(it)
                    })
                }
            }
        }.addOnFailureListener {
            onFailure(it.message ?: EM_NO_INTERNET)
        }
    }

    override fun getChatById(
        chatId: String,
        onSuccess: (ChatVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = firestoreDB.collection(GET_CHATS).document(chatId)
        docRef.get().addOnSuccessListener {
            val chatVO = it.data?.toChatVO() ?: ChatVO()
            fetchMessageList(docRef.collection(GET_MESSAGES), onSucess = {
                chatVO.messages = it
                onSuccess(chatVO)
            }, onFailure={
                onFailure(it)
            })
        }.addOnFailureListener {
            onFailure(it.message?: EM_NO_INTERNET)
        }
    }

    override fun getChatByUserId(
        userIds: List<String>,
        onSuccess: (ChatVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = firestoreDB.collection(GET_CHATS).whereEqualTo(USER_IDS, userIds)

        docRef.get().addOnSuccessListener {
            if (it.isEmpty){
                onSuccess(ChatVO())
            }else{
                onSuccess(it.first().data.toChatVO())
            }
        }.addOnFailureListener {
            onFailure(it.message?: EM_NO_INTERNET)
        }
    }

    override fun getUser(userId: String, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit) {
        val docRef = firestoreDB.collection(GET_USERS).document(userId)
        docRef.get().addOnSuccessListener {
            onSuccess(it.data?.toUserVO() ?: UserVO())
        }.addOnFailureListener {
            onFailure(it.message ?: EM_NO_INTERNET)
        }
    }

    override fun getUserList(
        contactList: List<String>,
        onSuccess: (List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userList = arrayListOf<UserVO>()

        for (contact in contactList){
            val docRef = firestoreDB.collection(GET_USERS).whereEqualTo(PHONE_NO, contact.replace(" ",""))
            docRef.get().addOnSuccessListener {
                if (!it.isEmpty){
                    for (document in it){
                        userList.add(document.data.toUserVO())
                    }
                }
                if (contact == contactList.last()){
                    onSuccess(userList)
                }
            }.addOnFailureListener {
                onFailure(it.message?: EM_NO_INTERNET)
            }
        }
//        var isComplete = false
//        while (!isComplete){
//            val mContactList = contactList.subList(0, 10)
//            val docRef = firestoreDB.collection(GET_USERS).whereIn(PHONE_NO, mContactList)
//            docRef.get().addOnSuccessListener {
//                if (!it.isEmpty){
//                    for (document in it){
//                        userList.add(document.data.toUserVO())
//                    }
//                }
//            }.addOnFailureListener {
//                onFailure(it.message?: EM_NO_INTERNET)
//            }
//            contactList.subtract(mContactList)
//            if (contactList.isEmpty()){
//                isComplete = true
//                onSuccess(userList)
//            }
//        }
    }

    override fun sendTextMessage(
        message: MessageVO,
        chatId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val collRef = firestoreDB.collection(GET_CHATS).document(chatId).collection(GET_MESSAGES)
        collRef.document().set(message.toMessageMap())
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: EM_NO_INTERNET) }

    }

    override fun sendImageMessage(
        image: Bitmap,
        message: MessageVO,
        chatId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        uploadImage(image, DIRECTORY_MESSAGE_IMAGES, onComplete = {
            message.photoMessage = it
            sendTextMessage(message, chatId, onSuccess, onFailure)
        })

    }

    override fun createNewChat(chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit) {
        val docRef = firestoreDB.collection(GET_CHATS).document()
        chatVO.chatId = docRef.id
        docRef.set(chatVO.toChatMap()).addOnSuccessListener {
            //docRef.collection(GET_MESSAGES).document().set(chatVO.messages.first().toMessageMap())
            onSuccess(chatVO.chatId)
        }.addOnFailureListener {
            onFailure(it.message?: EM_NO_INTERNET)
        }

    }

    override fun updateUserInfo(
        user: UserVO,
        profileImage: Bitmap?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val docRef = firestoreDB.collection(GET_USERS).document(user.userId)

        if (profileImage!=null){
            uploadImage(profileImage, DIRECTORY_PROFILE_IMAGES, onComplete = {
                user.profileImage = it
                docRef.set(user.toUserMap()).addOnSuccessListener {
                    onSuccess()
                }.addOnFailureListener {
                    onFailure(it.message ?: EM_NO_INTERNET)
                }
            })
        }
        else{
            docRef.set(user.toUserMap()).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it.message ?: EM_NO_INTERNET)
            }
        }
    }

//    override fun createNewChatWithImage(
//        image: Bitmap,
//        chatVO: ChatVO,
//        onSuccess: (chatId : String) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        uploadImage(image, DIRECTORY_MESSAGE_IMAGES, onComplete = {
//            chatVO.messages.first().photoMessage = it
//            createNewChat(chatVO, onSuccess, onFailure)
//        })
//    }

    private fun fetchMessageList(collection : CollectionReference, onSucess: (ArrayList<MessageVO>) -> Unit, onFailure: (String) -> Unit){
        collection.addSnapshotListener { value, error ->
            error?.let {
                onFailure(it.message ?: EM_NO_INTERNET)
            }.run{
                val messageList = arrayListOf<MessageVO>()
                for (messageDoc in value?.documents?: arrayListOf()){
                    messageList.add(messageDoc.data?.toMessageVO()?:MessageVO())
                }
                onSucess(messageList)
            }
        }
    }

    override fun uploadImage(image: Bitmap, directory:String, onComplete:(String)->Unit){
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val imageRef = storageReference.child("${directory}/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)

        uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val imageUrl = task.result.toString()
            onComplete(imageUrl)
        }
    }
}
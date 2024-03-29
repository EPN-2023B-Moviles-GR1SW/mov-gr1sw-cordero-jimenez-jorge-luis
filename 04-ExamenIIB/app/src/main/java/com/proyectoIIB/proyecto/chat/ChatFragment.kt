package com.barryzea.nilopartner.chat

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzea.nilopartner.R
import com.barryzea.nilopartner.adapters.ChatAdapter
import com.barryzea.nilopartner.commons.Constants
import com.barryzea.nilopartner.databinding.FragmentChatBinding
import com.barryzea.nilopartner.interfaces.OnChatListener
import com.barryzea.nilopartner.interfaces.OrderAux
import com.barryzea.nilopartner.pojo.Message
import com.barryzea.nilopartner.pojo.Order
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment(), OnChatListener {
    private var bind: FragmentChatBinding? = null
    private lateinit var adapter: ChatAdapter
    private var order: Order? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentChatBinding.inflate(inflater, container, false)
        bind?.let {
            return it.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrder()
        setupRecyclerView()
        setupButtons()
    }

    private fun getOrder() {
        order = (activity as? OrderAux)?.getOrderSelected()
        order?.let {
            setupActionBar()
            setupRealtimeDatabase()
        }
    }

    private fun setupRealtimeDatabase() {
        order?.let {
            val database = Firebase.database
            val chatRef = database.getReference(Constants.PATH_CHAT).child(it.id)
            val childListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    getMessage(snapshot)?.let { message ->
                        adapter.add(message)
                        bind?.rvChat?.smoothScrollToPosition(adapter.itemCount - 1)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    getMessage(snapshot)?.let { message ->
                        adapter.update(message)
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    getMessage(snapshot)?.let { message ->
                        adapter.delete(message)
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {
                    Snackbar.make(bind!!.root, "Error al cargar el chat", Snackbar.LENGTH_SHORT).show()
                }
            }
            chatRef.addChildEventListener(childListener)
        }
    }

    private fun getMessage(snapshot: DataSnapshot): Message? {
        return snapshot.getValue(Message::class.java)?.apply {
            snapshot.key?.let {
                id = it
            }
            FirebaseAuth.getInstance().currentUser?.let { user ->
                myUid = user.uid
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(mutableListOf(), this)
        bind?.let {
            it.rvChat.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    stackFromEnd = true
                }
                adapter = this@ChatFragment.adapter
            }
        }
    }

    private fun setupButtons() {
        bind?.let { bind ->
            bind.imgSend.setOnClickListener {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        bind?.let { bind ->
            order?.let {
                val database = Firebase.database
                val chatRef = database.getReference(Constants.PATH_CHAT).child(it.id)
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    val message = Message(
                        message = bind.edtMessage.text.toString().trim(),
                        sender = it.uid
                    )
                    bind.imgSend.isEnabled = false
                    chatRef.push().setValue(message)
                        .addOnSuccessListener {
                            bind.edtMessage.setText("")
                        }
                        .addOnCompleteListener {
                            bind.imgSend.isEnabled = true
                        }
                }
            }
        }
    }

    override fun deleteMessage(message: Message) {
        order?.let {
            val database = Firebase.database
            val messageRef = database.getReference(Constants.PATH_CHAT).child(it.id).child(message.id)
            messageRef.removeValue { error, ref ->
                bind?.let {
                    if (error != null) {
                        Snackbar.make(it.root, "Error al borrar mensaje", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(it.root, "Mensaje borrado", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupActionBar() {
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.title = getString(R.string.chat_title)
            setHasOptionsMenu(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            it.supportActionBar?.title = getString(R.string.order_title)
            setHasOptionsMenu(false)
        }
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}

package com.example.gmailclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emailList: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val senderInitial: TextView = view.findViewById(R.id.tvSenderInitial)
        val sender: TextView = view.findViewById(R.id.tvSender)
        val subject: TextView = view.findViewById(R.id.tvSubject)
        val message: TextView = view.findViewById(R.id.tvMessage)
        val time: TextView = view.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emailList[position]
        holder.senderInitial.text = email.senderInitial.toString()
        holder.sender.text = email.sender
        holder.subject.text = email.subject
        holder.message.text = email.message
        holder.time.text = email.time
    }

    override fun getItemCount(): Int {
        return emailList.size
    }
}

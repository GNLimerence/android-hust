package com.example.gmailclone

data class Email(
    val sender: String,
    val subject: String,
    val message: String,
    val time: String,
    val senderInitial: Char
)

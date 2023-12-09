package com.lovevery.notes.android.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository
    @Inject
    constructor() {
        private lateinit var userId: String
        private lateinit var subject: String

        fun saveUserId(userId: String) {
            this.userId = userId
        }

        fun getUserId(): String = userId

        fun saveSubject(subject: String) {
            this.subject = subject
        }

        fun getSubject(): String = subject
    }

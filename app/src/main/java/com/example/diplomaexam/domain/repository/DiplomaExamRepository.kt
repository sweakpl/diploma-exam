package com.example.diplomaexam.domain.repository

interface DiplomaExamRepository {

    suspend fun getHello(): String
}
package com.tl.mineminds.service.api

import com.tl.mineminds.MineMinds
import com.tl.mineminds.entity.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject

object Api {

    const val BASE_URL = "https://9629-49-249-171-190.ngrok-free.app/api/"

    suspend fun fetchSubjectList(userToken: String): List<Subject> {
        val subjects = mutableListOf<Subject>()
        withContext(Dispatchers.IO) {
            try {
                throw Exception("Dont wait")
                val request = Request.Builder()
                    .url("${BASE_URL}courses")
                    .header("Authorization", "Bearer $userToken")
                    .build()
                val response = MineMinds.httpClient.newCall(request).execute()
                val responseData = response.body?.string()!!
                val responseJson = JSONObject(responseData)
                //convertJsonToSubjects()
                println(responseJson)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        subjects.add(Subject(1, "Maths", ""))
        subjects.add(Subject(2, "Physics", ""))
        subjects.add(Subject(3, "Chemistry", ""))
        subjects.add(Subject(4, "Biology", ""))
        return subjects
    }

}
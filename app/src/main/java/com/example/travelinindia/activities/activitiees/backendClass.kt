package com.example.travelinindia.activities.activitiees

import android.util.Log
import android.widget.Toast
import com.example.travelinindia.activities.models.StationNameAndCode
import com.example.travelinindia.activities.models.trainInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject

class backendClass {
    suspend fun getTrainBetweenStation(
        stationFromCode: String,
        stationToCode: String,
        dateOfJourney: String
    ): List<trainInfoModel> {
        val arrayList = ArrayList<trainInfoModel>()
        try {
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations?fromStationCode=$stationFromCode&toStationCode=$stationToCode&dateOfJourney=$dateOfJourney")
                    .get()
                    .addHeader("X-RapidAPI-Key", "b1044adf0bmsh60bb07c9c735a9ep1ba787jsn45077450af4c")
                    .addHeader("X-RapidAPI-Host", "irctc1.p.rapidapi.com")
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(responseBody!!)
                    val status = jsonResponse.getBoolean("status")

                    if (status) {
                        val data = jsonResponse.getJSONArray("data")

                        for (i in 0 until data.length()) {
                            val firstObject = data.getJSONObject(i)

                            val trainNo = firstObject.optString("train_number")
                            val trainName = firstObject.optString("train_name")
                            val departureTime = firstObject.optString("from_sta")
                            val arrivalTime = firstObject.optString("to_sta")
                            val totalDuration = firstObject.optString("duration")
                            val runDay = firstObject.optString("run_days")
                            val sourceDes = firstObject.optString("train_src")
                            val trainDes = firstObject.optString("train_dstn")
                            val trainType = firstObject.optString("train_type")
                            val classType = firstObject.optString("class_type")

                            val dataList = trainInfoModel(
                                trainNo,
                                trainName,
                                departureTime,
                                arrivalTime,
                                totalDuration,
                                runDay,
                                sourceDes,
                                trainDes,
                                trainType,
                                classType
                            )

                            arrayList.add(dataList)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the network request
            e.printStackTrace()
        }

        return arrayList
    }
    suspend fun stationData(searchData: String): List<StationNameAndCode> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = RequestBody.create(mediaType, """
        {
            "search": "$searchData"
        }
    """.trimIndent())

        val request = Request.Builder()
            .url("https://rstations.p.rapidapi.com/")
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-RapidAPI-Key", "b1044adf0bmsh60bb07c9c735a9ep1ba787jsn45077450af4c")
            .addHeader("X-RapidAPI-Host", "rstations.p.rapidapi.com")
            .build()

        try {
            val response: Response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()

                responseBody?.let {
                    // Process the response data here
                    val data = JSONArray(it)
                    val stationInfoList = mutableListOf<StationNameAndCode>()

                    for (i in 0 until data.length()) {
                        val stationArray = data.getJSONArray(i)
                        if (stationArray.length() >= 2) {
                            val stationCode = stationArray.getString(0)
                            val stationName = stationArray.getString(1)

                            stationInfoList.add(StationNameAndCode(stationCode, stationName))
                        }
                    }

                    return@withContext stationInfoList
                } ?: run {
                    println("Response body is null")
                }
            } else {
                println("Error: ${response.code} - ${response.message}")
            }
        } catch (e: IOException) {
            println("Network error: ${e.message}")
        }

        return@withContext emptyList() // Return an empty list in case of an error
    }

}
package org.d3if0006.mobpro1.asessmen3fazrul.system.database

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.Outfits
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.OutfitsAPI
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.OutfitsStatus
import java.io.ByteArrayOutputStream

class mainViewModel : ViewModel() {

    private val _outfitsData = MutableLiveData<List<Outfits>>()
    val outfitsData: LiveData<List<Outfits>> get() = _outfitsData

    /*private val _status = MutableLiveData<OutfitsStatus>()
    val status: LiveData<OutfitsStatus> get() = _status*/
    var _status = MutableStateFlow(OutfitsStatus.LOADING)
        private set

    init {
        getAllOutfits()
    }

    fun getAllOutfits() {
        viewModelScope.launch {
            _status.value = OutfitsStatus.LOADING
            try {
                val response = OutfitsAPI.retrofitService.getAllOutfits()
                _outfitsData.value = response.results
                _status.value = OutfitsStatus.SUCCESS
                Log.d("OutfitsViewModel", "Success fetching outfits data: ${response.results}")
            } catch (e: Exception) {
                _status.value = OutfitsStatus.FAILED
                Log.e("OutfitsViewModel", "Error fetching outfits data: ${e.message}")
            }
        }
    }

    fun addOutfits(email: String, userName: String, styleName: String, photo: Bitmap?) {
        viewModelScope.launch {
            _status.value = OutfitsStatus.LOADING
            try {
                val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val userNamePart = userName.toRequestBody("text/plain".toMediaTypeOrNull())
                val styleNamePart = styleName.toRequestBody("text/plain".toMediaTypeOrNull())

                val photoPart: MultipartBody.Part? = photo?.let {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    val requestBody = byteArrayOutputStream.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("photoUrl", "photo.jpg", requestBody)
                }

                val response = OutfitsAPI.retrofitService.addOutfits(emailPart, userNamePart, styleNamePart, photoPart)
                _outfitsData.postValue(response.results)
                _status.value = OutfitsStatus.SUCCESS
                getAllOutfits()
            } catch (e: Exception) {
                _status.value = OutfitsStatus.FAILED
                Log.e("OutfitsViewModel", "Error adding outfits: ${e.message}")
            }
        }
    }


    fun deleteOutfits(id: String) {
        viewModelScope.launch {
            _status.value = OutfitsStatus.LOADING
            try {
                val response = OutfitsAPI.retrofitService.deleteOutfits(id)
                _outfitsData.value = response.results
                _status.value = OutfitsStatus.SUCCESS
                getAllOutfits()
            } catch (e: Exception) {
                _status.value = OutfitsStatus.FAILED
                Log.e("OutfitsViewModel", "Error deleting outfits: ${e.message}")
            }
        }
    }

}

package com.tl.mineminds

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tl.mineminds.entity.LearningMaterial
import com.tl.mineminds.entity.LessonItem
import com.tl.mineminds.entity.Subject
import com.tl.mineminds.service.api.Api
import com.tl.mineminds.ui.component.SubjectItemInteraction
import com.tl.mineminds.ui.screen.LessonScreenInteraction
import com.tl.mineminds.ui.screen.ScreenNames
import kotlinx.coroutines.launch

class MainViewModel:ViewModel(), SubjectItemInteraction, LessonScreenInteraction {

    private var sharedPreferences: SharedPreferences? = null

    //these routes could be a lot better
    var currentRoute: MutableLiveData<String> = MutableLiveData(ScreenNames.LOGIN.routeName)
        private set
    var username: MutableLiveData<String> = MutableLiveData("")
        private set
    var userToken: MutableLiveData<String> = MutableLiveData("")
        private set

    var subjects: MutableLiveData<List<Subject>> = MutableLiveData(emptyList())
        private set

    var learningMaterials: MutableLiveData<List<LearningMaterial>> = MutableLiveData(emptyList())
        private set

    var mainScreenRoute: MutableLiveData<String> = MutableLiveData(ScreenNames.LOGIN.routeName)
        private set

    var selectedSubjectId: MutableLiveData<Int> = MutableLiveData(0)
        private set

    var selectedLessonId: MutableLiveData<Int> = MutableLiveData(0)
        private set

    fun initSharedPref(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    fun onUsernameEntered(username: String) {
        this.username.postValue(username)
        currentRoute.postValue(ScreenNames.MAIN.routeName)
    }

    fun loadSubjects() {
        mainScreenRoute.postValue(ScreenNames.SUBJECTS.routeName) //todo should not be here
        viewModelScope.launch {
            val newSubjects = Api.fetchSubjectList(userToken.value!!)
            subjects.postValue(newSubjects)
        }
    }

    fun loadLearningMaterials(subjectId: Int, lessonId: Int) {
        viewModelScope.launch {
            val newLearningMaterial = Api.fetchLearningMaterial(userToken.value!!)
            learningMaterials.postValue(newLearningMaterial)
        }
    }

    override fun onSubjectItemSelected(subject: Subject) {
        selectedSubjectId.postValue(subject.id)
        mainScreenRoute.postValue(ScreenNames.LESSONS.routeName)
    }

    override fun onLessonSelected(lessonItem: LessonItem) {
        selectedLessonId.postValue(lessonItem.id)
        mainScreenRoute.postValue(ScreenNames.LEARNING_MATERIAL.routeName)
    }
}
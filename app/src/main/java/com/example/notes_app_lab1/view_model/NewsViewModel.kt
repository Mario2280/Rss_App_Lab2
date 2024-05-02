package com.example.notes_app_lab1.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_app_lab1.Network
import com.example.notes_app_lab1.app
import com.example.notes_app_lab1.data.database.NewsDatabase
import com.example.notes_app_lab1.data.entity.New
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.ktrssreader.kotlin.model.item.RssStandardItem
import tw.ktrssreader.kotlin.parser.RssStandardParser


class NewsViewModel() : ViewModel() {
    private val database = NewsDatabase.getInstance(app.context!!)
    private val newsDao = database.newsDao()
    private val _state: MutableState<List<New>> = mutableStateOf(emptyList())
    private val _allNews: MutableState<List<New>> = mutableStateOf(emptyList())
    init {
        val parser = RssStandardParser()
        fun mapNews(
            src: RssStandardItem,
        ) = New(title = src.title!!, description = src.description, pubDate = src.pubDate!!)
        viewModelScope.launch(Dispatchers.IO) {
            val httpClient = HttpClient()
            val xml = httpClient.get("https://people.onliner.by/feed").body<String>()
            val parsedXmlList = parser.parse(xml).items?.toList()
            if (parsedXmlList != null) {
                _state.value = parsedXmlList.map { New(title = it.title!!, description =  it.description, pubDate = it.pubDate!!)}
            }
            httpClient.close()
        }

//        if (Network.checkConnectivity(app.context!!)){
//            viewModelScope.launch(Dispatchers.IO) {
//
//                }
//            }
//        else
//        {
//            getAll()
//        }
    }


    val state: State<List<New>> = _state

    fun getAllReadLaterNews(){

        viewModelScope.launch(Dispatchers.IO) {
            _allNews.value = _state.value
            _state.value = newsDao.getAllNotesSortedByDate()
        }
    }

    fun getAllNews(){
        _state.value = _allNews.value
    }

    fun getNewsSortedByDate() = viewModelScope.launch(Dispatchers.IO) {
        _state.value = newsDao.getAllNotesSortedByDate()
    }

    fun insert(new: New) = viewModelScope.launch(Dispatchers.IO) {
        newsDao.inset(new)
    }

    fun deleteNote(aNew: New) = viewModelScope.launch(Dispatchers.IO) {
        newsDao.delete(aNew)
    }
}

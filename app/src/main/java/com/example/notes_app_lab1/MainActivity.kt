package com.example.notes_app_lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.notes_app_lab1.data.dao.NewDao
import com.example.notes_app_lab1.data.database.NewsDatabase
import com.example.notes_app_lab1.data.entity.New
import com.example.notes_app_lab1.ui.theme.Purple80
import com.example.notes_app_lab1.view_model.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var newsDao: NewDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = NewsViewModel()
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        setContent {
            ListOfNews(viewModel)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ListOfNews(viewModel: NewsViewModel) {

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("News App", fontSize = 22.sp) },
            modifier = Modifier.background(Purple80),
            actions = {
                IconButton(
                    onClick = {
                              viewModel.getAllReadLaterNews()
                    },

                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmarks,
                        contentDescription = "Read later",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.getAllNews()
                    },

                    ) {
                    Icon(
                        imageVector = Icons.Filled.Wifi,
                        contentDescription = "Read later",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        )
    }
    ) {
        val state = viewModel.state
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
        ) {
            items(state.value) {
                ExpandableCard(it!!, viewModel)
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun check(){
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("News App", fontSize = 22.sp) },
            modifier = Modifier.background(Purple80),
            actions = {
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Read later",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(
                    onClick = {
                    },

                    ) {
                    Icon(
                        imageVector = Icons.Filled.Wifi,
                        contentDescription = "Read later",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        )
    }
    ) {

        Text(text="qwe")

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(rssObj: New, viewModel: NewsViewModel) {

    var expanded by remember { mutableStateOf(false) }
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        onClick = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text  = rssObj?.title!!, modifier = Modifier.width(200.dp))
                if (expanded) {
                    Text(
                        text = Html.fromHtml(rssObj.description, 1).toString(),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = rssObj.pubDate!!.toString(),
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
            IconButton(
                onClick = {
                    viewModel.insert(New(title = rssObj?.title!!, description = rssObj.description, pubDate = rssObj.pubDate!!.toString()))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Read later",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

    }
}
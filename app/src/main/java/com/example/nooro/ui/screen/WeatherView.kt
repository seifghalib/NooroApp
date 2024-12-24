package com.example.nooro.ui.screen

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nooro.R
import com.example.nooro.data.ApiResponse
import com.example.nooro.data.Current
import com.example.nooro.ui.viewmodels.HomeViewModel
import com.example.nooro.utils.ApiState
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt


const val SHARED_PREF_NAME = "WeatherSharedPref"
const val CITY_KEY = "queried_City_Key"

@Composable
fun WeatherView(
    modifier: Modifier = Modifier,
    results: StateFlow<ApiState<ApiResponse>>
) {
    val apiResponse by results.collectAsStateWithLifecycle()
    val state = apiResponse

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (state) {
            is ApiState.Failure -> {

                val errMsg = when {
                    state.msg.contains("1006") -> "Invalid City"
                    state.msg.contains("hostname") -> "No Network"
                    else -> state.msg
                }
                Text(
                    text = errMsg,
                    modifier = modifier,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }

            is ApiState.Loading -> {
                CircularProgressIndicator()
            }

            is ApiState.Success -> {
                state.data.let { response ->
                    response.current?.let {
                        it.condition?.let { condition ->
                            WeatherImage(url = condition.icon ?: "")
                            Text(
                                text = condition.text ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Text(
                            text = response.location?.name ?: "",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "${it.tempC?.roundToInt()} \u2103",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold
                        )
                        WeatherCard(it)
                    }
                }
            }

            ApiState.EmptyState -> {
                EmptyView()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchTopBar(
    viewModel: HomeViewModel,
    sharedPreferences: SharedPreferences
) {

    var query by remember { mutableStateOf("") }
    var isOpen by remember { mutableStateOf(false) }

    val onSearchCallBack: (String) -> Unit = {
        isOpen = false
        viewModel.onTextChange(query)
        sharedPreferences.edit {
            putString(CITY_KEY, query)
        }
        query = ""
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = onSearchCallBack,
        active = isOpen,
        onActiveChange = {
            isOpen = it
        },
        placeholder = {
            Text("Search Location")
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    if (isOpen)
                        onSearchCallBack(query)
                },
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        shape = RoundedCornerShape(14.dp)
    ) {}
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherImage(
    url: String
) {
    val painter = rememberImagePainter(
        data = "https:$url",
        builder = {
            placeholder(R.drawable.ic_loader)
            error(R.drawable.ic_error_24)
            crossfade(enable = true)
        }
    )
    Image(
        modifier = Modifier
            .height(123.dp)
            .height(123.dp),
        painter = painter,
        contentDescription = "Weather Image",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun WeatherCard(
    current: Current
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        CardHeader()

        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = "${current.humidity} %",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "${current.uv}",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "${current.feelslikeC?.roundToInt()} ℃",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CardHeader() {
    Row(
        modifier = Modifier.padding(8.dp),
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = "Humidity",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(1f),
            text = "UV",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(1f),
            text = "Feels Like",
            textAlign = TextAlign.Center
        )
    }
}


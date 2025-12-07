package com.example.BakeryApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



data class Recipe(
    val id: Int,
    var name: String,
    var servings: Int,
    var ingredients: List<String>,
    var steps: List<String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BakeryApp() }
    }
}

@Composable
fun DksekrudTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun BakeryApp() {
    var screen by remember { mutableStateOf("home") }
    var selected: Recipe? by remember { mutableStateOf(null) }
    var recipes by remember { mutableStateOf(sampleRecipes().toMutableList()) }

    DksekrudTheme {
        when (screen) {
            "home" -> HomeScreen(recipes) {
                selected = it
                screen = "detail"
            }
            "detail" -> selected?.let {
                DetailScreen(it) { screen = "home" }
            }
        }
    }
}

@Composable
fun HomeScreen(recipes: List<Recipe>, onClick: (Recipe) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("레시피 목록", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(recipes) { r ->
                Text(r.name, modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(r) }
                    .padding(12.dp))
                Divider()
            }
        }
    }
}

@Composable
fun DetailScreen(recipe: Recipe, onBack: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text(recipe.name, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        Text("재료:")
        recipe.ingredients.forEach { Text("- $it") }

        Spacer(Modifier.height(12.dp))
        Text("단계:")
        recipe.steps.forEachIndexed { i, s -> Text("${i + 1}. $s") }

        Spacer(Modifier.height(20.dp))
        Button(onClick = onBack) { Text("뒤로가기") }
    }
}

fun sampleRecipes() = listOf(
    Recipe(
        id = 1,
        name = "식빵",
        servings = 2,
        ingredients = listOf("강력분 300g", "이스트 4g", "소금 5g", "설탕 15g", "물 180ml"),
        steps = listOf("반죽", "1차 발효", "성형", "2차 발효", "굽기")
    ),
    Recipe(
        id = 2,
        name = "쿠키",
        servings = 4,
        ingredients = listOf("버터 120g", "설탕 80g", "박력분 200g", "달걀 1개"),
        steps = listOf("크림화", "가루 혼합", "성형", "굽기")
    ),
    Recipe(
        id = 3,
        name = "케이크",
        servings = 8,
        ingredients = listOf("박력분 100g", "설탕 100g", "계란 3-4개", "버터 30g", "우유 30ml", "베이킹파우더 소량"),
        steps = listOf("오븐 예열", "틀 준비", "재료 계량")
    ),
    Recipe(
        id = 4,
        name = "밀크 초콜릿",
        servings = 4,
        ingredients = listOf("코코아버터 50g", "코코아파우더 25g", "설탕 40-50g", "분유 20g", "바닐라 읷트랙 약간", "소금 한 꼬집"),
        steps = listOf("코코아버터 녹이기(중탕)", "덩어리 없도록 체쳐서 넣기", "녹인 코코아버터에 가루류 혼합", "골고루 섞이도록 부드럽게 젓기", "바닐라,소금 넣기", "틀에 붓기", "냉장 1시간")
    )
    )

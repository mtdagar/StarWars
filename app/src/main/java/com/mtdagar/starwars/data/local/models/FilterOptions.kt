package com.mtdagar.starwars.data.local.models

data class FilterOptions(
    val sortingOption : SortingOptions
)

enum class SortingOptions {
    NAME, AGE
}

enum class Gender {
    NAME, AGE
}

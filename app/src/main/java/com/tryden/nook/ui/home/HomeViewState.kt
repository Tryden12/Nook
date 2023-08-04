package com.tryden.nook.ui.home

data class HomeViewState(
    val dataList: List<DataItem<*>> = emptyList(),
    val isLoading: Boolean = false,
) {
    data class DataItem<T>(
        val data: T,
    )
}
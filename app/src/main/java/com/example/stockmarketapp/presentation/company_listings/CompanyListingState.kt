package com.example.stockmarketapp.presentation.company_listings

import com.example.stockmarketapp.domain.model.CompanyListing

data class CompanyListingState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing:Boolean = false,
    val searchQuery :String = "",
)

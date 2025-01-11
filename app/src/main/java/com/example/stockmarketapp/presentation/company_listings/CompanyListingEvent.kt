package com.example.stockmarketapp.presentation.company_listings

sealed class CompanyListingEvent {
    data object Refresh: CompanyListingEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingEvent()

}
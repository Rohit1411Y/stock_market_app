package com.example.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
):ViewModel() {
  var  state by mutableStateOf(CompanyListingState())
  private var searchJob: Job? =null
    fun onEvent(event: CompanyListingEvent){
        when(event){
            is CompanyListingEvent.OnSearchQueryChange -> {
             state = state.copy(searchQuery = event.query)
             searchJob?.cancel()
             searchJob = viewModelScope.launch {
                 delay(500L)
                 getCompanyListings()
             }
            }
           is CompanyListingEvent.Refresh -> {
             getCompanyListings(fetchFromRemote = true)
           }
        }
    }

    fun getCompanyListings(
        query:String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false,
    ){
     viewModelScope.launch {
         repository.getCompanyListings(fetchFromRemote = fetchFromRemote, query = query)
             .collect{
                 result->
                 when(result){
                     is Resource.Error -> {
                     state = state.copy(isError = result.isError, errorMessage = result.message)

                     }
                     is Resource.Loading -> {
                   state = state.copy(isLoading = result.isLoading)
                     }
                     is Resource.Success -> {
                     result.data?.let {
                         listings->
                         state = state.copy(
                             companies = listings
                         )
                     }
                     }
                 }
             }
     }
    }

}
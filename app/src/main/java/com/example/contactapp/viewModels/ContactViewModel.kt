package com.example.contactapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    // Create a reference to the ContactDao
    // This is the only way to access the database
    private val _dao = ContactDatabase.getDatabase(application).contactDao()

    // Expose all contacts as a StateFlow
    // state.in() creates a StateFlow from a Flow
    // scope is the coroutine scope for the StateFlow
    // started is the lifecycle that the StateFlow will be in
    // initialValue is the default value for the StateFlow
    val contacts: StateFlow<List<Contact>> = _dao.getAllContacts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // Save a contact to the database
    // viewModelScope is the coroutine scope for this function
    fun saveContact(contact: Contact) {
        viewModelScope.launch { _dao.insertContact(contact) }

    }
    // Challenge 1: Update a contact in the database
    // Challenge 2: Delete a contact from the database

    fun updateContact(contact: Contact) {
        viewModelScope.launch { _dao.updateContact(contact) }
    }
    fun deleteContact(contact: Contact) {
        viewModelScope.launch { _dao.deleteContact(contact) }
    }
}



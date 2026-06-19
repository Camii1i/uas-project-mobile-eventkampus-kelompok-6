package com.app.uts.universe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.uts.universe.model.Event

/**
 * Shared ViewModel used by AdminDashboardFragment and AdminCreateFragment.
 * Holds the Event that is being edited. When null, the Create fragment works in
 * add‑new mode.
 */
class AdminSharedViewModel : ViewModel() {
    private val _eventToEdit = MutableLiveData<Event?>(null)
    val eventToEdit: LiveData<Event?> = _eventToEdit

    fun setEventToEdit(event: Event?) {
        _eventToEdit.value = event
    }
}

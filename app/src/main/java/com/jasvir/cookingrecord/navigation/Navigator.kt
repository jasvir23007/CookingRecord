package com.jasvir.cookingrecord.navigation

import com.jasvir.cookingrecord.model.Character

interface Navigator {
    fun navigateToDetails(character: Character)
}
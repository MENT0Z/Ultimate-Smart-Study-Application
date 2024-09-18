package com.mruraza.smartstudy.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun spacer(height:Int?=null,
           width:Int?=null){
    Spacer(modifier = Modifier.height(height?.dp?:0.dp).width(width?.dp?:0.dp))
}
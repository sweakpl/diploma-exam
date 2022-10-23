package com.sweak.diplomaexam.presentation.screens.questions_answering.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun GradeCard(
    gradeCardOrientation: GradeCardOrientation,
    text: String,
    grade: Grade?,
    onGradeSelected: (Grade) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        if (gradeCardOrientation == GradeCardOrientation.HORIZONTAL) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(all = MaterialTheme.space.medium)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1
                )

                Column(modifier = Modifier.padding(start = MaterialTheme.space.medium)) {
                    var expanded by remember { mutableStateOf(false) }
                    var textFieldSize by remember { mutableStateOf(Size.Zero) }

                    OutlinedTextField(
                        value = grade?.stringRepresentation ?: "",
                        onValueChange = {},
                        readOnly = true,
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSurface
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            trailingIconColor = MaterialTheme.colors.onSurface,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { expanded = !expanded }
                            ) {
                                Icon(
                                    imageVector =
                                    if (expanded) Icons.Default.ArrowDropUp
                                    else Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop down arrow"
                                )
                            }
                        },
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    ) {
                        Grade.values().forEach { grade ->
                            DropdownMenuItem(onClick = {
                                onGradeSelected(grade)
                                expanded = false
                            }) {
                                Text(
                                    text = grade.stringRepresentation,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        } else if (gradeCardOrientation == GradeCardOrientation.VERTICAL) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MaterialTheme.space.medium)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.space.medium)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var textFieldSize by remember { mutableStateOf(Size.Zero) }

                    OutlinedTextField(
                        value = grade?.stringRepresentation ?: "",
                        onValueChange = {},
                        readOnly = true,
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSurface
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            trailingIconColor = MaterialTheme.colors.onSurface,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = MaterialTheme.colors.onSurface,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { expanded = !expanded }
                            ) {
                                Icon(
                                    imageVector =
                                    if (expanded) Icons.Default.ArrowDropUp
                                    else Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop down arrow"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    ) {
                        Grade.values().forEach { grade ->
                            DropdownMenuItem(onClick = {
                                onGradeSelected(grade)
                                expanded = false
                            }) {
                                Text(
                                    text = grade.stringRepresentation,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class GradeCardOrientation {
    HORIZONTAL, VERTICAL
}

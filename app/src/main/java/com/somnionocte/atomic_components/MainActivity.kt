package com.somnionocte.atomic_components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somnio_nocte.portal.NexusPortal
import com.somnionocte.atomic_components.core.LocalContentColor
import com.somnionocte.atomic_components.core.ScaleIndication
import com.somnionocte.atomic_components.core.rememberScrollBehavior
import com.somnionocte.atomic_components.extensions.mix
import com.somnionocte.atomic_components.templates.TemplateButton
import com.somnionocte.atomic_components.templates.TemplateCheckbox
import com.somnionocte.atomic_components.templates.TemplateCollapseBottomBar
import com.somnionocte.atomic_components.templates.TemplateFullModalView
import com.somnionocte.atomic_components.templates.TemplateLargeAppBar
import com.somnionocte.atomic_components.templates.TemplateRadioButton
import com.somnionocte.atomic_components.templates.TemplateSwitch
import com.somnionocte.atomic_components.ui.theme.FoundationComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FoundationComponentsTheme {
                CompositionLocalProvider(LocalIndication provides ScaleIndication) {
                    NexusPortal {
                        val scrollState = rememberLazyListState()

                        AtomicScaffold(
                            topBar = { MyAppBar("Foundation Components") },
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            contentPadding = PaddingValues(16.dp),
                            scrollBehavior = rememberScrollBehavior(scrollState)
                        ) { innerPadding ->
                            LazyColumn(
                                Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                state = scrollState,
                                contentPadding = innerPadding
                            ) {
                                item {
                                    var text by remember { mutableStateOf("") }

                                    TextField(
                                        text,
                                        { text = it },
                                        label = { Text("LABEL") },
                                        placeholder = { Text("Search") },
                                        singleLine = true,
                                        prefix = { Icon(Icons.Rounded.Search, null) },
                                    )
                                }

                                items(25) {
                                    var state by remember { mutableStateOf(false) }
                                    var showModalView by remember { mutableStateOf(false) }

                                    if(showModalView) TemplateFullModalView(
                                        { showModalView = false },
                                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                                    ) {
                                        Column {
                                            Text("Some text")
                                            Button(it) {
                                                showModalView = false
                                            }
                                            Text("Some text")
                                            Button(it) {
                                                showModalView = false
                                            }
                                            Text("Some text")
                                            Button(it) {
                                                showModalView = false
                                            }
                                        }
                                    }

                                    Switch(state, { state = it })
                                    CheckBox(state, { state = it })
                                    Row {
                                        RadioButton(state, { state = true })
                                        RadioButton(!state, { state = false })
                                    }
                                    Button(it) {
                                        showModalView = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RadioButton(
        checked: Boolean,
        onCheck: () -> Unit,
        enabled: Boolean = true
    ) {
        TemplateRadioButton(
            checked,
            onCheck,
            enabled = enabled,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            backgroundCheckedColor = MaterialTheme.colorScheme.primaryContainer,
            checkColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }

    @Composable
    fun Switch(
        checked: Boolean,
        onCheck: (Boolean) -> Unit,
        enabled: Boolean = true
    ) {
        TemplateSwitch(
            checked,
            onCheck,
            enabled = enabled,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            backgroundCheckedColor = MaterialTheme.colorScheme.primaryContainer,
            thumbColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            thumbCheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    @Composable
    fun CheckBox(
        checked: Boolean,
        onCheck: (Boolean) -> Unit,
        enabled: Boolean = true
    ) {
        TemplateCheckbox(
            checked,
            onCheck,
            enabled = enabled,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            backgroundCheckedColor = MaterialTheme.colorScheme.primaryContainer,
            checkColor = MaterialTheme.colorScheme.onPrimaryContainer,
            borderStroke = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondaryContainer)
        )
    }

    @Composable
    fun MyAppBar(
        title: String,
        onBackClick: () -> Unit = {  }
    ) {
        TemplateLargeAppBar(
            navigation = {
                IconButton(onBackClick) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, null) }
            },
            header = {
                Text(title, Modifier.padding(start = 8.dp), fontWeight = FontWeight.SemiBold)
            },
            largeHeader = {
                Text(
                    title, Modifier.padding(start = 8.dp, bottom = 8.dp),
                    fontSize = 28.sp, fontWeight = FontWeight.SemiBold
                )
            },
            inactiveBackgroundColor = MaterialTheme.colorScheme.background,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 8.dp)
        )
    }

    @Composable
    fun MyBottomBar(content: @Composable RowScope.() -> Unit) {
        TemplateCollapseBottomBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            content = content
        )
    }

    @Composable
    fun Button(index: Int, enabled: Boolean = true, onClick: () -> Unit = {  }) {
        TemplateButton(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(45),
            contentPadding = PaddingValues(24.dp),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            pressedBackgroundColor = MaterialTheme.colorScheme.run { primaryContainer.mix(.25f, onPrimaryContainer) },
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Text("Foundation Button $index")
        }
    }

    val textStyleTextField = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)

    @Composable
    fun TextField(
        value: String,
        onChange: (value: String) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = textStyleTextField,
        label: @Composable() (() -> Unit)? = null,
        placeholder: @Composable() (() -> Unit)? = null,
        prefix: @Composable() (() -> Unit)? = null,
        suffix: @Composable() (() -> Unit)? = null,
        supportingText: @Composable() (() -> Unit)? = null,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = false,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        minLines: Int = 1,
        backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape: RoundedCornerShape = RoundedCornerShape(28.dp),
        padding: PaddingValues = PaddingValues(22.dp),
        interactionSource: MutableInteractionSource? = null
    ) {
        AtomicTextField(
            value = value,
            onChange = onChange,
            modifier = modifier
                .background(backgroundColor, shape)
                .padding(padding),
            labelPosition = AtomicTextFieldLabelPosition.Inside,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            supportingTextArrangement = Arrangement.SpaceBetween,
            supportingTextModifier = Modifier,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            innerTextFieldGap = 16.dp,
            cursorColor = LocalContentColor.current,
        )
    }
}
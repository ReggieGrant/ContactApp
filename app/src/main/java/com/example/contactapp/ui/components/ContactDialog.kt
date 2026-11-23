package com.example.contactapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactapp.data.Contact

@Composable
fun ContactDialog(
    contact: Contact? = null,
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit,

    ) {
    var name by remember { mutableStateOf(contact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(contact?.phoneNumber ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }

    val title = if (contact == null) "Add Contact" else "Edit Contact"
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                // Challenge Name field required
                // Challenge Phone number field required
                // Challenge Email field required
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newContact = Contact(
                        id = contact?.id ?: 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    )
                    onConfirm(newContact)

                }) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ContactDialogPreview() {
    ContactDialog(
        contact = Contact(
        name = "John Doe",
        phoneNumber = "123-456-7890",
        email = "william.henry.harrison@example-pet-store.com"
    ),
        onConfirm = {}, onDismiss = {}
    )
}





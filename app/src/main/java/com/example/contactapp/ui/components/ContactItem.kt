package com.example.contactapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactapp.data.Contact

@Composable
fun ContactItem(
    contact: Contact,
    onClick: (Contact) -> Unit,
    onDelete: (Contact) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Contact") },
            text = { Text("Delete ${contact.name}? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(contact)
                    showDeleteDialog = false
                }) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) { Text("Cancel") }

            }
        )
    }
    // Card wraps the contact in a clickable surface
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(contact) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Person avatar
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Contact avatar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Contact name and phone number
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    contact.name,
                    style = MaterialTheme.typography.titleMedium

                )
                Text(
                    contact.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            // Delete button
            IconButton(
                onClick = { showDeleteDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete contact",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    ContactItem(
        contact = Contact(
            name = "John Doe",
            phoneNumber = "123-456-7890",
            email = "john.mclean@examplepetstore.com"
        ),

        onClick = {},
        onDelete = {}
    )
}

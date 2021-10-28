package com.gotravel.domain.entities

import com.gotravel.application.dto.Author
import java.time.LocalDateTime

data class Comment(var createdAt: String = formatter.format(LocalDateTime.now()),
                   var updatedAt: String = formatter.format(LocalDateTime.now()),
                   var body: String = "",
                   var author: Author,
                   var id: String)
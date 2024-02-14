package com.bookmarket.bookmarket.controller

import com.bookmarket.bookmarket.controller.request.PostBookRequest
import com.bookmarket.bookmarket.controller.request.PutBookRequest
import com.bookmarket.bookmarket.controller.response.BookResponse
import com.bookmarket.bookmarket.extension.toBookModel
import com.bookmarket.bookmarket.extension.toResponse
import com.bookmarket.bookmarket.service.BookService
import com.bookmarket.bookmarket.service.CustomerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid book: PostBookRequest){
        val customer = customerService.getById(book.customerId)
        bookService.create(book.toBookModel(customer))

    }

    @GetMapping
    fun getAll(@PageableDefault(page = 0, size = 10) pageable: Pageable) : Page<BookResponse>{
        return bookService.getAll(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse  {
        return bookService.findById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        return bookService.update(book.toBookModel(bookSaved))
    }

    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 10) pageble: Pageable): Page<BookResponse>{
        return bookService.findActives(pageble).map { it.toResponse()}
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

}
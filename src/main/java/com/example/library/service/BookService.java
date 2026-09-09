package com.example.library.service;
import com.example.library.dto.*;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class BookService {
    private final BookRepository repo;
    public BookService(BookRepository repo){this.repo=repo;}
    public List<BookResponse> findAll(){return repo.findAll().stream().map(this::toResponse).toList();}
    public BookResponse findById(Long id){return toResponse(repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Book not found: "+id)));}
    public BookResponse create(BookRequest r){return toResponse(repo.save(new Book(r.title(),r.author(),r.genre(),r.amount(),r.available())));}
    public BookResponse update(Long id,BookRequest r){Book b=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Book not found: "+id));b.setTitle(r.title());b.setAuthor(r.author());b.setGenre(r.genre());b.setAmount(r.amount());b.setAvailable(r.available());return toResponse(repo.save(b));}
    public void delete(Long id){if(!repo.existsById(id))throw new ResourceNotFoundException("Book not found: "+id);repo.deleteById(id);}
    public Book getEntity(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Book not found: "+id));}
    public Book getEntityForBorrow(Long id){return repo.findByIdForUpdate(id).orElseThrow(()->new ResourceNotFoundException("Book not found: "+id));}
    private BookResponse toResponse(Book b){return new BookResponse(b.getId(),b.getTitle(),b.getAuthor(),b.getGenre(),b.getAmount(),b.isAvailable());}
}
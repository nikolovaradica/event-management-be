package mk.ukim.finki.event_platform_backend.event.service;

import mk.ukim.finki.event_platform_backend.event.model.dto.CategoryRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponse> findAll(String search, int page, int size);
    CategoryResponse findById(Long id);
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
}
package mk.ukim.finki.event_platform_backend.event.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.event_platform_backend.common.exception.NotFoundException;
import mk.ukim.finki.event_platform_backend.event.model.Category;
import mk.ukim.finki.event_platform_backend.event.model.dto.CategoryRequest;
import mk.ukim.finki.event_platform_backend.event.model.dto.CategoryResponse;
import mk.ukim.finki.event_platform_backend.event.repository.CategoryRepository;
import mk.ukim.finki.event_platform_backend.event.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryResponse> findAll(String search, int page, int size) {
        page = Math.max(page, 0);
        if (size <= 0) {
            size = 10;
        }

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categories;

        if (search == null || search.isBlank()) {
            categories = categoryRepository.findAll(pageable);
        } else {
            categories = categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        return categories.map(this::mapToResponse);
    }

    @Override
    public CategoryResponse findById(Long id) {
        return mapToResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found")));
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
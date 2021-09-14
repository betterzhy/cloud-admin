package com.zhy.controller;

import com.zhy.commons.ApiException;
import com.zhy.commons.ApiResponse;
import com.zhy.dto.CategoryDto;
import com.zhy.dto.CategoryTree;
import com.zhy.entity.Category;
import com.zhy.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类(Category)表控制层
 *
 * @author makejava
 * @since 2021-09-13 20:49:51
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryTreeList")
    public ApiResponse<?> getCategoryTreeList() {
        List<CategoryTree> categoryTreeList = this.categoryService.queryByFirstLevel();
        return ApiResponse.ok("add category successful!", categoryTreeList);
    }

    @PostMapping("/addCategory")
    public ApiResponse<?> addCategory(@RequestBody @Validated CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        this.categoryService.insert(category);
        return ApiResponse.ok("add category successful!", category);
    }

    @PostMapping("/deleteCategory")
    public ApiResponse<?> deleteCategory(Integer id) {
        if(this.categoryService.deleteById(id)){
            throw new ApiException("delete category failed!");
        }
        return ApiResponse.ok("delete category successful!");
    }

    @PostMapping("/updateCategory")
    public ApiResponse<?> updateCategory(@RequestBody @Validated Category category) {
        this.categoryService.update(category);
        return ApiResponse.ok("update category successful!", category);
    }

}